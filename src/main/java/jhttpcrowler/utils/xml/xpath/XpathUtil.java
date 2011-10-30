package jhttpcrowler.utils.xml.xpath;

import jhttpcrowler.utils.xml.XmlUtil;
import net.sf.saxon.lib.NamespaceConstant;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for performinq <b>XPath</b> queries
 *
 * @author Sergey Prilukin
 */
public final class XpathUtil {
    public static final String XPATH_FACTORY_KEY = "javax.xml.xpath.XPathFactory";
    public static final String SAXON_XPATH_FACTORY_NAMESPACE = XPATH_FACTORY_KEY + ":" + NamespaceConstant.OBJECT_MODEL_SAXON;
    public static final String SAXON_XPATH_FACTORY = "net.sf.saxon.xpath.XPathFactoryImpl";

    private static Object eval(Object xml, String expression, QName returnType)
            throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {

        XPathFactory xPathFactory = null;

        //Try to create saxon XPath factory if appropriate system property is set
        if (SAXON_XPATH_FACTORY.equals(System.getProperty(SAXON_XPATH_FACTORY_NAMESPACE))) {
            try {
                xPathFactory = XPathFactory.newInstance(NamespaceConstant.OBJECT_MODEL_SAXON);
            } catch (XPathFactoryConfigurationException e) {
                //If cannot create saxon factory - fallback to default
                xPathFactory = null;
            }
        }


        if (xPathFactory == null) {
            xPathFactory = XPathFactory.newInstance();
        }

        XPath xpath = xPathFactory.newXPath();
        // XPath Query for showing all nodes value
        XPathExpression expr = xpath.compile(expression);

        return expr.evaluate(XmlUtil.toXml(xml), returnType);
    }

    /**
     * Returns {@link List} of {@link Node} which are results of executing XPath query
     *
     * @param xml xml on which Xpath query will be executed
     * @param expression XPart expression
     * @return {@link List} of {@link Node} which are results of executing XPath query
     * @throws XPathExpressionException if xpath expression is invalid
     * @throws ParserConfigurationException if exception occurs during getting document builder
     * @throws SAXException if exception during parsing xml occured
     * @throws IOException if exception during input\output occured
     */
    public static List<Node> nodeList(Object xml, String expression)
            throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        NodeList nodes = (NodeList)eval(xml, expression, XPathConstants.NODESET);
        List<Node> nodesList = new ArrayList<Node>(nodes.getLength());
        for (int i = 0; i < nodes.getLength(); i++) {
            nodesList.add(nodes.item(i));
        }

        return nodesList;
    }

    /**
     * Returns {@link Node} which is results of executing XPath expression
     *
     * @param xml xml on which Xpath query will be executed
     * @param expression XPart expression
     * @return {@link List} of {@link Node} which are results of executing XPath query
     * @throws XPathExpressionException if xpath expression is invalid
     * @throws ParserConfigurationException if exception occurs during getting document builder
     * @throws SAXException if exception during parsing xml occured
     * @throws IOException if exception during input\output occured
     */
    public static Node node(Object xml, String expression)
            throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        return (Node)eval(xml, expression, XPathConstants.NODE);
    }

    /**
     * Returns {@link String} which is results of executing XPath expression
     *
     * @param xml xml on which Xpath query will be executed
     * @param expression XPart expression
     * @return {@link List} of {@link Node} which are results of executing XPath query
     * @throws XPathExpressionException if xpath expression is invalid
     * @throws ParserConfigurationException if exception occurs during getting document builder
     * @throws SAXException if exception during parsing xml occured
     * @throws IOException if exception during input\output occured
     */
    public static String string(Object xml, String expression)
            throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        return (String)eval(xml, expression, XPathConstants.STRING);
    }

    /**
     * Returns {@link Boolean} which is results of executing XPath expression
     *
     * @param xml xml on which Xpath query will be executed
     * @param expression XPart expression
     * @return {@link List} of {@link Node} which are results of executing XPath query
     * @throws XPathExpressionException if xpath expression is invalid
     * @throws ParserConfigurationException if exception occurs during getting document builder
     * @throws SAXException if exception during parsing xml occured
     * @throws IOException if exception during input\output occured
     */
    public static Boolean bool(Object xml, String expression)
            throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        return (Boolean)eval(xml, expression, XPathConstants.BOOLEAN);
    }

    /**
     * Returns {@link Double} which is results of executing XPath expression
     *
     * @param xml xml on which Xpath query will be executed
     * @param expression XPart expression
     * @return {@link List} of {@link Node} which are results of executing XPath query
     * @throws XPathExpressionException if xpath expression is invalid
     * @throws ParserConfigurationException if exception occurs during getting document builder
     * @throws SAXException if exception during parsing xml occured
     * @throws IOException if exception during input\output occured
     */
    public static Double number(Object xml, String expression)
            throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        return (Double)eval(xml, expression, XPathConstants.NUMBER);
    }

}
