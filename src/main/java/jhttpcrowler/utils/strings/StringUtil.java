package jhttpcrowler.utils.strings;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for using with {@link String}

 * @author Sergey Prilukin
 */
public final class StringUtil {
    private static final String[] DEFAULT_CHARSETS =
            new String[]{"UTF-8", "windows-1251", "IBM866", "ISO-8859-1"};

    /**
     * Used to compare strings ignoring their charsets.
     * Only for fixing charset issues in windows consoles!
     *
     * @param sourceString    first string for comaring
     * @param stringToCompare second string for comaring
     * @param charsets array of charset names which will be used for comparing strings
     * @return <code>true</code> if string are equal of defferences only by their charsets else return <code>false</code>
     */
    public static boolean equalsIgnoreCharset(String sourceString, String stringToCompare, String[] charsets) {

        if (sourceString == null || stringToCompare == null) {
            return false;
        }

        for (String charset : charsets) {
            try {
                String srcStr = new String(sourceString.getBytes(charset), "UTF-8");
                String str = new String(stringToCompare.getBytes(charset), "UTF-8");
                if (sourceString.equals(str) || srcStr.equals(stringToCompare)) {
                    return true;
                }
            } catch (UnsupportedEncodingException e) {
                //should not happen
                throw new RuntimeException(e);
            }
        }

        return false;
    }

    /**
     * See {@link #equalsIgnoreCharset(String, String, String[])}.
     * {"UTF-8", "windows-1251", "IBM866"} used as charset array 
     * @param sourceString    first string for comaring
     * @param stringToCompare second string for comaring
     * @return <code>true</code> if string are equal of defferences only by their charsets else return <code>false</code>
     */
    public static boolean equalsIgnoreCharset(String sourceString, String stringToCompare) {
        return equalsIgnoreCharset(sourceString, stringToCompare, DEFAULT_CHARSETS);
    }

    public static String search(String text, String regexp, int group, int start) {
        Matcher matcher = Pattern.compile(regexp).matcher(text);

        if (matcher.find(start)) {
            return matcher.group(group);
        } else {
            return null;
        }
    }

    public static String search(String text, String regexp) {
        return search(text, regexp, 0, 0);
    }

    public static List<String> searchAll(String text, String regexp, int group, int start) {
        Matcher matcher = Pattern.compile(regexp).matcher(text);
        List<String> resultList = new ArrayList<String>();

        while (matcher.find()) {
            resultList.add(matcher.group(group));
        }

        if (resultList.size() > 0) {
            return resultList;
        } else {
            return null;
        }
    }

    public static List<String> searchAll(String text, String regexp) {
        return searchAll(text, regexp, 0, 0);
    }
}
