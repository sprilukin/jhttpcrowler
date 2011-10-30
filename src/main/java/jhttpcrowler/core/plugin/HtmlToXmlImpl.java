package jhttpcrowler.core.plugin;

import jhttpcrowler.utils.xml.html.HtmlCleanerUtil;

import java.util.Properties;

/**
 * @author Sergey
 * @since 05.12.2010 4:30:26
 *        <p/>
 *        TODO: add class description
 */
public class HtmlToXmlImpl implements HtmlToXml {
    public static final String NAME = "htmlToXml";

    public Object clean(Object is, int returnType, Properties props) throws Exception {
        return HtmlCleanerUtil.clean(is, returnType, props);
    }

    public String clean(Object is, Properties props) throws Exception {
        return HtmlCleanerUtil.clean(is, props);
    }

    public String clean(Object is) throws Exception {
        return HtmlCleanerUtil.clean(is);
    }

    public Object clean(Object is, int returnType) throws Exception {
        return HtmlCleanerUtil.clean(is, returnType);
    }

    public String getName() {
        return NAME;
    }
}
