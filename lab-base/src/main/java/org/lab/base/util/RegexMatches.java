package org.lab.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class RegexMatches
{
   private static String REGEX = "^[A-Za-z0-9_\\( \\)\\-]+$";
   private static String INPUT = "考虑到房价快速的aabfooaabfo-_)(oabfoobkkk-_()";
   private static String REPLACE = "";
   public static void main(String[] args) {
      Pattern p = Pattern.compile(REGEX);
      // 获取 matcher 对象
      Matcher m = p.matcher(INPUT);
      StringBuffer sb = new StringBuffer();
      while(m.find()){
         m.appendReplacement(sb,REPLACE);
      }
      m.appendTail(sb);
      System.out.println(sb.toString());
   }
}