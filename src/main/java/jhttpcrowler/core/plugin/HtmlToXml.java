package jhttpcrowler.core.plugin;

import jhttpcrowler.core.Plugin;

import java.util.Properties;

/**
 * @author Sergey
 * @since 05.12.2010 4:19:40
 *        <p/>
 *        TODO: add class description
 */
public interface HtmlToXml extends Plugin {
    public Object clean(Object is, int returnType, Properties props) throws Exception;
    public String clean(Object is, Properties props) throws Exception;
    public String clean(Object is) throws Exception;
    public Object clean(Object is, int returnType) throws Exception;
}
