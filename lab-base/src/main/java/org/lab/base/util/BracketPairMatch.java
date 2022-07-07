package org.lab.base.util;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author lzqing
 * @date 2022-07-06
 * @vsrsion 1.0
 **/
public class BracketPairMatch {

    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        System.out.println(stack);
        final Object peek = stack.peek();
        System.out.println(peek);

        System.out.println(stack);

        System.out.println(isValid("{666{}}"));
        System.out.println(isValid2("}{777{}"));
    }

    private static boolean isValid(String s) {
        if (StrUtil.isBlank(s)) {
            return true;
        }
        Map<Character, Character> map = new HashMap();
        map.put('}', '{');
        map.put(']', '[');
        map.put('】', '【');
        map.put(')', '(');
        map.put('）', '（');
        map.put('”', '”');
        map.put('\'', '\'');
        map.put('“', '“');
        Stack stack = new Stack();
        final int len = s.length();
        for (int i = 0; i < len; i++) {
            if (map.containsValue(s.charAt(i))) {
                stack.push(s.charAt(i));
            } else if (!stack.isEmpty() && map.containsKey(s.charAt(i)) && map.get(s.charAt(i)).equals(stack.peek())) {
                stack.pop();
            } else if (map.containsKey(s.charAt(i))) {
                return false;
            }
        }
        if (stack.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isValid3(String s) {
        int slen = s.length(); // 括号的长度
        if (slen % 2 == 1) { // 括号不是成对出现直接返回 false
            return false;
        }
        // 把所有对比的括号存入 map，对比时用
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');
        // 定义栈，用于存取括号（辅助比较）
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < slen; i++) { // 循环所有字符
            char c = s.charAt(i);
            if (map.containsKey(c)) { // 为右边的括号，如 ')'、'}' 等
                if (stack.isEmpty() || stack.peek() != map.get(c)) { // 栈为空或括号不匹配
                    return false;
                }
                stack.pop(); // 是一对括号，执行出栈（消除左右括号）
            } else { // 左边括号，直接入栈
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    public static boolean isValid2(String s) {
        int len;
        do {
            len = s.length();
            // 消除成双成对的符号
            s = s.replace("()", "").replace("[]", "").
                    replace("{}", "");
        } while (len != s.length()); // 不能再进行替换了，replace 方法没有替换任何字符
        return s.length() == 0;
    }
}
