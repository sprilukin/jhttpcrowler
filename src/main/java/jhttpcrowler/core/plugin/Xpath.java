package jhttpcrowler.core.plugin;

import jhttpcrowler.core.Plugin;
import org.w3c.dom.Node;

import java.util.List;

/**
 * @author Sergey
 * @since 05.12.2010 3:05:59
 *        <p/>
 *        TODO: add class description
 */
public interface Xpath extends Plugin {
    public List<Node> nodeList(Object xml, String expression) throws Exception;
    public Node node(Object xml, String expression) throws Exception;
    public String string(Object xml, String expression) throws Exception;
    public Boolean bool(Object xml, String expression) throws Exception;
    public Double number(Object xml, String expression) throws Exception;
}
