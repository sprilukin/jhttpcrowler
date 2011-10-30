package jhttpcrowler.utils.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class which allows to convert different source types
 * like {@code String}, {@code InputStream}, {@code File}
 * to xml, namely to {@link org.w3c.dom.Node}
 * using java DOM parser
 *
 * @author Sergey Prilukin
 */
public final class XmlUtil {

    private static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory domFactory =
                DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        return domFactory.newDocumentBuilder();
    }

    /**
     * Returns new {@link Node}.
     * Used namely for creating XML node root
     *
     * @return new {@link Node}
     * @throws ParserConfigurationException if exception occurs during getting document builder
     */
    public static Node newXml() throws ParserConfigurationException {
        Document document = getDocumentBuilder().newDocument();
        document.setXmlStandalone(true);
        return document;
    }

    /**
     * Converts object to XML
     *
     * @param xml object which should be instance of one of following types:
     *      {@link String}, {@link InputStream}, {@link File}, {@link Node}, {@link InputSource}
     *
     * @return {@link Node} object which is root object of parsed XML
     * @throws ParserConfigurationException if exception occurs during getting document builder
     * @throws SAXException if exception during parsing xml occured
     * @throws IOException if exception during input\output occured
     */
    public static Node toXml(Object xml) throws ParserConfigurationException, SAXException, IOException {
        if (xml == null) return null;

        if (xml instanceof String) {
            return getDocumentBuilder().parse(new ByteArrayInputStream(((String)xml).getBytes("UTF-8")));
        } else if (xml instanceof InputStream) {
            return getDocumentBuilder().parse((InputStream)xml);
        } else if (xml instanceof InputSource) {
            return getDocumentBuilder().parse((InputSource)xml);
        } else if (xml instanceof File) {
            return getDocumentBuilder().parse((File)xml);
        } else if (xml instanceof Node) {
            return (Node)xml;
        } else {
            throw new IllegalArgumentException(String.format("Source class not supported: %s", xml.getClass().getName()));
        }
    }
    
}
