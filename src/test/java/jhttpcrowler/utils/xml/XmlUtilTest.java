package jhttpcrowler.utils.xml;

import org.junit.Test;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for {@link XmlUtil}
 *
 * @author Sergey Prilukin
 */
public class XmlUtilTest {
    private String xmlString  = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<persons>\n" +
            "  <person username=\"MP123456\">\n" +
            "    <name>John</name>\n" +
            "    <surname>Smith</surname>\n" +
            "  </person>\n" +
            "  <person username=\"PK123456\">\n" +
            "    <name>Jim</name>\n" +
            "    <surname>Who</surname>\n" +
            "  </person>\n" +
            "</persons>";

    private void checkNode(Node node) {
        assertNotNull(node);
        assertEquals(1, node.getChildNodes().getLength());
        Node persons = node.getChildNodes().item(0);
        assertEquals("persons", persons.getNodeName());
    }

    @Test
    public void testLoadFromInputStream() throws Exception {
        InputStream xml = Thread.currentThread().getContextClassLoader().getResourceAsStream("jhttpcrowler/utils/xml/xslt.xml");
        Node node = XmlUtil.toXml(xml);
        checkNode(node);
    }

    @Test
    public void testLoadFromString() throws Exception {
        Node node = XmlUtil.toXml(xmlString);
        checkNode(node);
    }

    @Test
    public void testLoadFromFile() throws Exception {
        String tempDir = System.getProperty("java.io.tmpdir");
        String testXmlFile = tempDir + "\\" + "jhttpcrowlertest.xml";
        File f = new File(testXmlFile);
        if (f.exists()) {
            if (!f.delete()) {
                fail("Error during removing temp file");
            }
        }

        BufferedWriter out = new BufferedWriter(new FileWriter(testXmlFile));
        out.write(xmlString);
        out.close();

        Node node = XmlUtil.toXml(new File(testXmlFile));
        checkNode(node);
    }

    @Test
    public void testLoadFromInputSource() throws Exception {
        InputStream xml = Thread.currentThread().getContextClassLoader().getResourceAsStream("jhttpcrowler/utils/xml/xslt.xml");
        InputSource is = new InputSource(xml);

        Node node = XmlUtil.toXml(is);
        checkNode(node);
    }

    @Test
    public void testLoadFromNode() throws Exception {
        Node node = XmlUtil.toXml(xmlString);
        assertEquals(node, XmlUtil.toXml(node));
    }

    @Test(expected = SAXException.class)
    public void testParsingException() throws Exception {
        XmlUtil.toXml("Bad xml");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotSupportedType() throws Exception {
        XmlUtil.toXml(new Object());
    }
}
