package com.github.siilas.imagereader.utils;

public final class StringUtils {

	private StringUtils() {
	}
	
	public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
	
	public static String between(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        final int start = str.indexOf(open);
        if (start != (-1)) {
            final int end = str.indexOf(close, start + open.length());
            if (end != (-1)) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }
	
}
