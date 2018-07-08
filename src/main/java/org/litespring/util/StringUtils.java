package org.litespring.util;

/**
 * Spring中StringUtils
 */
public class StringUtils {
    public static Boolean hasLenth(String str){
        return hasLenth((CharSequence)str);
    }

    public static Boolean hasLenth(CharSequence str){
        return str!=null&&str.length()>0;
    }

    public static Boolean hasText(String str){
        return hasText((CharSequence)str);
    }

    public static Boolean hasText(CharSequence str){
        if(!hasLenth(str)){
            return false;
        }
        int strLen = str.length();
        for(int i = 0;i<strLen;i++){
            if(!Character.isWhitespace(str.charAt(i))){
                return true;
            }
        }
        return false;
    }

    public static String trimAllWhitespace(String str) {
        if (!hasLenth(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            }
            else {
                index++;
            }
        }
        return sb.toString();
    }
}
