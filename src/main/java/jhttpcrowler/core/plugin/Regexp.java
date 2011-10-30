package jhttpcrowler.core.plugin;

import jhttpcrowler.core.Plugin;

import java.util.List;

/**
 * @author Sergey
 * @since 05.12.2010 18:15:04
 *        <p/>
 *        TODO: add class description
 */
public interface Regexp extends Plugin {
    public String search(String text, String regexp);
    public List<String> searchAll(String text, String regexp, int group, int start);
    public List<String> searchAll(String text, String regexp);
}
