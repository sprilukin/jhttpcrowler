package jhttpcrowler.core.plugin;

import jhttpcrowler.utils.strings.StringUtil;

import java.util.List;

/**
 * @author Sergey
 * @since 05.12.2010 18:21:35
 *        <p/>
 *        TODO: add class description
 */
public class RegexpImpl implements Regexp {

    public static final String NAME = "regexp";

    public String search(String text, String regexp) {
        return StringUtil.search(text, regexp);
    }

    public List<String> searchAll(String text, String regexp, int group, int start) {
        return StringUtil.searchAll(text, regexp, group, start);
    }

    public List<String> searchAll(String text, String regexp) {
        return StringUtil.searchAll(text, regexp);
    }

    public String getName() {
        return NAME;
    }

}
