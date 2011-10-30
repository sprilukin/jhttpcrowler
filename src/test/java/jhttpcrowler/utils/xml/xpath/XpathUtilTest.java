package jhttpcrowler.utils.xml.xpath;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * Tests for {@link XpathUtil}
 *
 * @author Sergey Prilukin
 */
public class XpathUtilTest {

    private String testXml;

    @Before
    public void init() throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("jhttpcrowler/utils/xml/xpath.xml");
        testXml = new String(IOUtils.toByteArray(is));
    }

    @Test
    public void testNodeList() throws Exception {
        List<Node> nodes = XpathUtil.nodeList(testXml, "//person/name/text()");
        Assert.assertNotNull(nodes);

        List<String> textNodes = new ArrayList<String>();
        for (Node node: nodes) {
            textNodes.add(node.getNodeValue());
        }

        assertReflectionEquals(asList("Deep", "Kumar", "Deepali"), textNodes);
    }

    @Test
    public void testNode() throws Exception {
        Node node = XpathUtil.node(testXml, "/information/person/name");
        assertNotNull(node);
        assertEquals("name", node.getNodeName());
    }

    @Test
    public void testString() throws Exception {
        String string = XpathUtil.string(testXml, "/information/person/name/text()");
        assertNotNull(string);
        assertEquals("Deep", string);
    }

    @Test
    public void testBoolean() throws Exception {
        Boolean bool = XpathUtil.bool(testXml, "count(//person) > 0");
        assertNotNull(bool);
        assertTrue(bool);
    }

    @Test
    public void testNumber() throws Exception {
        Double number = XpathUtil.number(testXml, "/information/person/@id");
        assertNotNull(number);
        assertEquals(Double.valueOf(1.0), number);
    }

    @Test
    public void testSaxonFactory() throws Exception {
        System.setProperty(XpathUtil.SAXON_XPATH_FACTORY_NAMESPACE, XpathUtil.SAXON_XPATH_FACTORY);
        List<Node> result = XpathUtil.nodeList(testXml, "//name[matches(.,'Deep')]");
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
