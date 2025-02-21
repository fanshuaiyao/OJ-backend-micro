package com.yupi.yuojbackenduserservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuojbackendcommon.common.ErrorCode;
import com.yupi.yuojbackendcommon.constant.CommonConstant;
import com.yupi.yuojbackendcommon.exception.BusinessException;
import com.yupi.yuojbackendcommon.utils.SqlUtils;
import com.yupi.yuojbackendcommon.utils.UserContext;
import com.yupi.yuojbackendmodel.model.dto.user.UserLoginRequest;
import com.yupi.yuojbackendmodel.model.dto.user.UserQueryRequest;
import com.yupi.yuojbackendmodel.model.dto.user.UserRegisterRequest;
import com.yupi.yuojbackendmodel.model.entity.User;
import com.yupi.yuojbackendmodel.model.enums.UserRoleEnum;
import com.yupi.yuojbackendmodel.model.vo.LoginUserVO;
import com.yupi.yuojbackendmodel.model.vo.UserVO;
import com.yupi.yuojbackenduserservice.mapper.UserMapper;
import com.yupi.yuojbackenduserservice.service.UserService;
import com.yupi.yuojbackenduserservice.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.x500.style.RFC4519Style;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.yupi.yuojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;
import static org.bouncycastle.asn1.x500.style.RFC4519Style.userPassword;
import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * 用户服务实现
 *
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "yupi";

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    /**
     * @description: 用户注册
     * @author: fanshuaiyao
     * @date: 2025/2/17 17:39
     * @param: userRegisterRequest
     * @return: long
     **/
    @Override
    public long userRegister(UserRegisterRequest userRegisterRequest) {

        // 1. 获取用户名称和密码
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();

        // 2. 密码加密
        String encodePassword = passwordEncoder.encode(userPassword);

        // 3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encodePassword);
        try {
            this.save(user);
            log.info("用户注册成功，用户名：{}", userAccount);
            return user.getId();
        } catch (DuplicateKeyException e) {
            log.error("用户注册失败，用户名重复，用户名：{}", userAccount, e);
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名重复，保存用户失败！");
        } catch (Exception e) {
            log.error("用户注册失败，未知错误，用户名：{}", userAccount, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，未知错误！");
        }
    }

    /**
     * @description: 用户登录
     * @author: fanshuaiyao
     * @date: 2025/2/18 18:04
     * @param: userLoginRequest
     * @param: request
     * @return: LoginUserVO
     **/
    @Override
    public LoginUserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {

        // 1. 获取账号密码
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        // 2. 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        User user = this.baseMapper.selectOne(queryWrapper);

        // 3. 用户不存在
        if (user == null) {
            log.info("user login failed, not find this account");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }

        // 4. 用户存在验证密码
        boolean matches = passwordEncoder.matches(userPassword, user.getUserPassword());
        if (!matches) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 5. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return this.getLoginUserVO(user);
    }


    /**
     * @description: 得到登录用户VO
     * @author: fanshuaiyao
     * @date: 2025/2/18 18:05
     * @param: user
     * @return: LoginUserVO
     **/
    @Override
    public LoginUserVO getLoginUserVO(User user) {

        // 1. 创建token
        HashMap<String, String> map = new HashMap<>();
        map.put("userId",String.valueOf(user.getId()));
        String token = null;
        try {
            token = JWTUtils.getToken(map);
        } catch (UnsupportedEncodingException e) {
            log.error("生成 Token 时出现编码错误", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败，请稍后再试");
        }

        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        loginUserVO.setToken(token);
        log.info("token信息：{}", token);
        return loginUserVO;
    }


    /**
     * @description: 从ThreadLocal中获取当前登录用户
     * @author: fanshuaiyao
     * @date: 2025/2/18 18:27
     * @param: request
     * @return: User
     **/
    @Override
    public User getLoginUser(HttpServletRequest request) {
        Long userId = UserContext.getUser();
        return this.getById(userId);
    }

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUserPermitNull(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            return null;
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        return this.getById(userId);
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return isAdmin(user);
    }

    @Override
    public boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }



    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVO(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String unionId = userQueryRequest.getUnionId();
        String mpOpenId = userQueryRequest.getMpOpenId();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(unionId), "unionId", unionId);
        queryWrapper.eq(StringUtils.isNotBlank(mpOpenId), "mpOpenId", mpOpenId);
        queryWrapper.eq(StringUtils.isNotBlank(userRole), "userRole", userRole);
        queryWrapper.like(StringUtils.isNotBlank(userProfile), "userProfile", userProfile);
        queryWrapper.like(StringUtils.isNotBlank(userName), "userName", userName);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }
}
