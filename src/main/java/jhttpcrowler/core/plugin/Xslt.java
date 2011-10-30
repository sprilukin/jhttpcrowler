package jhttpcrowler.core.plugin;

import jhttpcrowler.core.Plugin;
import org.w3c.dom.Node;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author Sergey
 * @since 05.12.2010 3:11:57
 *        <p/>
 *        TODO: add class description
 */
public interface Xslt extends Plugin {
    public InputStream asStream(Object xml, Object xslt, Map<String, String> properties) throws Exception;
    public byte[] asByteArray(Object xml, Object xslt, Map<String, String> properties) throws Exception;
    public Node asXml(Object xml, Object xslt, Map<String, String> properties) throws Exception;
    public InputStream asStream(Object xml, Object xslt, Properties properties) throws Exception;
    public byte[] asByteArray(Object xml, Object xslt, Properties properties) throws Exception;
    public Node asXml(Object xml, Object xslt, Properties properties) throws Exception;
    public InputStream asStream(Object xml, Object xslt) throws Exception;
    public byte[] asByteArray(Object xml, Object xslt) throws Exception;
    public Node asXml(Object xml, Object xslt) throws Exception;
}
