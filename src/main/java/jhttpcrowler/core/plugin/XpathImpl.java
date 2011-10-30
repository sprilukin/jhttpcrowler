package jhttpcrowler.core.plugin;

import jhttpcrowler.utils.xml.xpath.XpathUtil;
import org.w3c.dom.Node;

import java.util.List;

/**
 * @author Sergey
 * @since 05.12.2010 3:09:07
 *        <p/>
 *        TODO: add class description
 */
public class XpathImpl implements Xpath {

    public static final String NAME = "xpath";

    public List<Node> nodeList(Object xml, String expression) throws Exception {
        return XpathUtil.nodeList(xml, expression);
    }

    public Node node(Object xml, String expression) throws Exception {
        return XpathUtil.node(xml, expression);
    }

    public String string(Object xml, String expression) throws Exception {
        return XpathUtil.string(xml, expression);
    }

    public Boolean bool(Object xml, String expression) throws Exception {
        return XpathUtil.bool(xml, expression);
    }

    public Double number(Object xml, String expression) throws Exception {
        return XpathUtil.number(xml, expression);
    }

    public String getName() {
        return NAME;
    }
}
