package com.yupi.yuojbackenduserservice;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class YuojBackendUserServiceApplicationTests {

        public static Integer findFirstWithAbsoluteDifference(List<Integer> numbers, int k) {
            // 遍历列表，找到第一个与其他数字的差值绝对值都大于k的数字
            for (int i = 0; i < numbers.size(); i++) {
                int current = numbers.get(i);
                boolean isValid = true;

                for (int j = 0; j < numbers.size(); j++) {
                    if (i != j && Math.abs(current - numbers.get(j)) <= k) {
                        isValid = false;
                        break;
                    }
                }

                if (isValid) {
                    return current; // 返回第一个符合条件的数字
                }
            }

            // 如果没有找到符合条件的数字，则返回null
            return null;
        }

        public static void main(String[] args) {
            // List<Integer> numbers = List.of(1, 5, 3, 9, 2);
            List<Integer> numbers = Arrays.asList(1, 5, 3, 9, 2);
            int k = 3;
            Integer result = findFirstWithAbsoluteDifference(numbers, k);

            if (result != null) {
                System.out.println("第一个与其他数字差值绝对值大于" + k + "的数字是: " + result);
            } else {
                System.out.println("列表中没有符合条件的数字。");
            }
        }
    }

