package jhttpcrowler.core.plugin;

import jhttpcrowler.utils.xml.XmlUtil;
import jhttpcrowler.utils.xml.xslt.XsltUtil;
import org.w3c.dom.Node;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author Sergey
 * @since 05.12.2010 3:16:47
 *        <p/>
 *        TODO: add class description
 */
public class XsltImpl implements Xslt {

    public static final String NAME = "xslt";

    public InputStream asStream(Object xml, Object xslt, Map<String, String> properties) throws Exception {
        return new BufferedInputStream(new ByteArrayInputStream(asByteArray(xml, xslt, properties)));
    }

    public byte[] asByteArray(Object xml, Object xslt, Map<String, String> properties) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XsltUtil.transform(xml, xslt, baos, properties);

        return baos.toByteArray();
    }

    public Node asXml(Object xml, Object xslt, Map<String, String> properties) throws Exception {
        Node node = XmlUtil.newXml();
        XsltUtil.transform(xml, xslt, node, properties);
        return node;
    }

    public InputStream asStream(Object xml, Object xslt, Properties properties) throws Exception {
        return new BufferedInputStream(new ByteArrayInputStream(asByteArray(xml, xslt, properties)));
    }

    public byte[] asByteArray(Object xml, Object xslt, Properties properties) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XsltUtil.transform(xml, xslt, baos, properties);

        return baos.toByteArray();
    }

    public Node asXml(Object xml, Object xslt, Properties properties) throws Exception {
        Node node = XmlUtil.newXml();
        XsltUtil.transform(xml, xslt, node, properties);
        return node;
    }

    public InputStream asStream(Object xml, Object xslt) throws Exception {
        return new BufferedInputStream(new ByteArrayInputStream(asByteArray(xml, xslt)));
    }

    public byte[] asByteArray(Object xml, Object xslt) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XsltUtil.transform(xml, xslt, baos);

        return baos.toByteArray();
    }

    public Node asXml(Object xml, Object xslt) throws Exception {
        Node node = XmlUtil.newXml();
        XsltUtil.transform(xml, xslt, node);
        return node;
    }

    public String getName() {
        return NAME;
    }
}
