package jhttpcrowler.utils.xml.html;

import jhttpcrowler.utils.xml.XmlUtil;
import org.htmlcleaner.BrowserCompactXmlSerializer;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.CompactXmlSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.SimpleXmlSerializer;
import org.htmlcleaner.TagNode;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class which allows to convert any html to pure xml
 *
 * @author Sergey Prilukin
 */
public final class HtmlCleanerUtil {

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static Properties DEFAULT_PROPERTIES = new Properties();
    static {
        DEFAULT_PROPERTIES.setProperty(HtmlCleanerConst.USE_EMPTY_ELEMENT_TAGS, "false");
        DEFAULT_PROPERTIES.setProperty(HtmlCleanerConst.NAMESPACES_AWARE, "false");
    }

    private static void parseProperties(CleanerProperties properties, Map props) {
        if (props.get(HtmlCleanerConst.ADVANCED_XML_ESCAPE) != null) {
            properties.setAdvancedXmlEscape(Boolean.parseBoolean(HtmlCleanerConst.ADVANCED_XML_ESCAPE));
        }

        if (props.get(HtmlCleanerConst.CDATA_FOR_SCRIPT_AND_STYLE) != null) {
            properties.setUseCdataForScriptAndStyle(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.CDATA_FOR_SCRIPT_AND_STYLE))));
        }

        if (props.get(HtmlCleanerConst.SPECIAL_ENTITIES) != null) {
            properties.setTranslateSpecialEntities(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.SPECIAL_ENTITIES))));
        }

        if (props.get(HtmlCleanerConst.RECOGNIZE_UNICODE_CHARS) != null ) {
            properties.setRecognizeUnicodeChars(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.RECOGNIZE_UNICODE_CHARS))));
        }

        if (props.get(HtmlCleanerConst.OMIT_UNKNOWN_TAGS) != null ) {
            properties.setOmitUnknownTags(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.OMIT_UNKNOWN_TAGS))));
        }

        if (props.get(HtmlCleanerConst.USE_EMPTY_ELEMENT_TAGS) != null ) {
            properties.setUseEmptyElementTags(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.USE_EMPTY_ELEMENT_TAGS))));
        }

        if (props.get(HtmlCleanerConst.TREAT_UNKNOWNTAGS_AS_CONTENT) != null ) {
            properties.setTreatUnknownTagsAsContent(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.TREAT_UNKNOWNTAGS_AS_CONTENT))));
        }

        if (props.get(HtmlCleanerConst.OMIT_DEPRECATED_TAGS) != null ) {
            properties.setOmitDeprecatedTags(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.OMIT_DEPRECATED_TAGS))));
        }

        if (props.get(HtmlCleanerConst.TREAT_DEPRTAGS_AS_CONTENT) != null ) {
            properties.setTreatDeprecatedTagsAsContent(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.TREAT_DEPRTAGS_AS_CONTENT))));
        }

        if (props.get(HtmlCleanerConst.OMIT_XML_DECL) != null ) {
            properties.setOmitXmlDeclaration(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.OMIT_XML_DECL))));
        }

        if (props.get(HtmlCleanerConst.OMIT_COMMENTS) != null ) {
            properties.setOmitComments(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.OMIT_COMMENTS))));
        }

        if (props.get(HtmlCleanerConst.OMIT_HTML_ENVELOPE) != null ) {
            properties.setOmitHtmlEnvelope(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.OMIT_HTML_ENVELOPE))));
        }

        if (props.get(HtmlCleanerConst.OMIT_DOCTYPE_DECLARATION) != null ) {
            properties.setOmitDoctypeDeclaration(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.OMIT_DOCTYPE_DECLARATION))));
        }

        if (props.get(HtmlCleanerConst.ALLOW_MULTIWORD_ATTRIBUTES) != null ) {
            properties.setAllowMultiWordAttributes(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.ALLOW_MULTIWORD_ATTRIBUTES))));
        }

        if (props.get(HtmlCleanerConst.ALLOW_HTML_INSIDE_ATTRIBUTES) != null ) {
            properties.setAllowHtmlInsideAttributes(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.ALLOW_HTML_INSIDE_ATTRIBUTES))));
        }

        if (props.get(HtmlCleanerConst.NAMESPACES_AWARE) != null ) {
            properties.setNamespacesAware(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.NAMESPACES_AWARE))));
        }

        if (props.get(HtmlCleanerConst.HYPHEN_REPLACEMENT) != null ) {
            properties.setHyphenReplacementInComment(String.valueOf(props.get(HtmlCleanerConst.HYPHEN_REPLACEMENT)));
        }

        if (props.get(HtmlCleanerConst.PRUNE_TAGS) != null ) {
            properties.setPruneTags(String.valueOf(props.get(HtmlCleanerConst.PRUNE_TAGS)));
        }

        if (props.get(HtmlCleanerConst.BOOLEAN_ATTS) != null ) {
            properties.setBooleanAttributeValues(String.valueOf(props.get(HtmlCleanerConst.BOOLEAN_ATTS)));
        }

        if (props.get(HtmlCleanerConst.IGNORE_QUEST_AND_EXCLAM) != null ) {
            properties.setIgnoreQuestAndExclam(
                    Boolean.parseBoolean(String.valueOf(props.get(HtmlCleanerConst.IGNORE_QUEST_AND_EXCLAM))));
        }
    }

    private static HtmlCleaner getCleaner(Properties props) {
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties properties = cleaner.getProperties();
        Properties newProps = new Properties(DEFAULT_PROPERTIES);

        if (props != null) {
            for (Map.Entry entry: props.entrySet()) {
                newProps.put(entry.getKey(), entry.getValue());
            }
        }

        parseProperties(properties, newProps);

        return cleaner;
    }

    private static HtmlCleaner getCleaner() {
        return getCleaner(null);
    }

    private static Object convertResult(TagNode node, int returnType, CleanerProperties properties)
            throws IOException, ParserConfigurationException, SAXException {

        Object result;

        if ((returnType & HtmlCleanerConst.TO_SIMPLE_STRING) == HtmlCleanerConst.TO_SIMPLE_STRING) {
            result = new SimpleXmlSerializer(properties).getAsString(node);
        } else if ((returnType & HtmlCleanerConst.TO_PRETTY_STRING) == HtmlCleanerConst.TO_PRETTY_STRING) {
            result = new PrettyXmlSerializer(properties).getAsString(node);
        } else if ((returnType & HtmlCleanerConst.TO_BROWSER_COMPACT_STRING) == HtmlCleanerConst.TO_BROWSER_COMPACT_STRING) {
            result = new BrowserCompactXmlSerializer(properties).getAsString(node);
        } else {
            result = new CompactXmlSerializer(properties).getAsString(node);
        }

        if ((returnType & HtmlCleanerConst.TO_NODE) == HtmlCleanerConst.TO_NODE) {
            result = XmlUtil.toXml(result);
        } if ((returnType & HtmlCleanerConst.TO_INPUT_STREAM) == HtmlCleanerConst.TO_INPUT_STREAM) {
            result = new ByteArrayInputStream(((String)result).getBytes(DEFAULT_CHARSET));
        }

        return result;
    }

    private static TagNode cleanHtml(Object html, HtmlCleaner cleaner) throws IOException {
        if (html == null) {
            return null;
        }

        if (html instanceof InputStream) {
            return cleaner.clean((InputStream)html, DEFAULT_CHARSET);
        } else if (html instanceof File) {
            return cleaner.clean((File)html, DEFAULT_CHARSET);
        } else if (html instanceof String) {
            return cleaner.clean((String)html);
        } else if (html instanceof URL) {
            return cleaner.clean((URL)html);
        } else if (html instanceof Reader) {
            return cleaner.clean((Reader)html);
        } else {
            throw new IllegalArgumentException(String.format("Type not supported: %s", html.getClass().getName()));
        }
    }

    /**
     * <p>Returns xmlized version of passed html.</p>
     * <p>
     *     html can be one of the following types:<br/>
     *     {@link InputStream}, {@link File}, {@link String}, {@link URL}, {@link Reader}
     * </p>
     * <p>
     *     returned object cen be of type:<br/>
     *     {@link String}, {@link org.w3c.dom.Node}, {@link InputStream}
     * </p>
     *
     * @param html html which needs to be cleaned
     * @param returnType type of the result. see {@link HtmlCleanerConst}
     * @param props HtmlCleaner properties
     * @return xml representation of passed html object
     * @throws IOException if IOException exception during cleaning occurs
     * @throws ParserConfigurationException if return type was {@code HtmlCleanerConst.TO_NODE} and exception
     *      during conversion to {@link org.w3c.dom.Node} occurs
     * @throws SAXException if return type was {@code HtmlCleanerConst.TO_NODE} and exception
     *      during conversion to {@link org.w3c.dom.Node} occurs
     */
    public static Object clean(Object html, int returnType, Properties props)
            throws IOException, ParserConfigurationException, SAXException {
        HtmlCleaner cleaner = getCleaner(props);
        return convertResult(cleanHtml(html, cleaner), returnType, cleaner.getProperties());
    }

    /**
     * Same as {@link #clean(Object, int, java.util.Properties)}
     * but always returns a string
     *
     * @param html html which needs to be cleaned
     * @param props HtmlCleaner properties
     * @return xml representation of passed html object
     * @throws IOException if IOException exception during cleaning occurs
     * @throws ParserConfigurationException if return type was {@code HtmlCleanerConst.TO_NODE} and exception
     *      during conversion to {@link org.w3c.dom.Node} occurs
     * @throws SAXException if return type was {@code HtmlCleanerConst.TO_NODE} and exception
     *      during conversion to {@link org.w3c.dom.Node} occurs
     * @see #clean(Object, int, java.util.Properties)
     */
    public static String clean(Object html, Properties props)
            throws IOException, ParserConfigurationException, SAXException {
        HtmlCleaner cleaner = getCleaner(props);
        return (String)convertResult(cleanHtml(html, cleaner), HtmlCleanerConst.TO_DEFAULT, cleaner.getProperties());
    }

    /**
     * Same as {@link #clean(Object, java.util.Properties)}
     * but uses default cleaner properties
     *
     * @param html html which needs to be cleaned
     * @return xml representation of passed html object
     * @throws IOException if IOException exception during cleaning occurs
     * @throws ParserConfigurationException if return type was {@code HtmlCleanerConst.TO_NODE} and exception
     *      during conversion to {@link org.w3c.dom.Node} occurs
     * @throws SAXException if return type was {@code HtmlCleanerConst.TO_NODE} and exception
     *      during conversion to {@link org.w3c.dom.Node} occurs
     * @see #clean(Object, int, java.util.Properties)
     */
    public static String clean(Object html)
            throws IOException, ParserConfigurationException, SAXException {
        HtmlCleaner cleaner = getCleaner();
        return (String)convertResult(cleanHtml(html, cleaner), HtmlCleanerConst.TO_DEFAULT, cleaner.getProperties());
    }

    /**
     * Same as {@link #clean(Object, int, java.util.Properties)}
     * but uses default cleaner properties
     *
     * @param html html which needs to be cleaned
     * @param returnType type of the result. see {@link HtmlCleanerConst}
     * @return xml representation of passed html object
     * @throws IOException if IOException exception during cleaning occurs
     * @throws ParserConfigurationException if return type was {@code HtmlCleanerConst.TO_NODE} and exception
     *      during conversion to {@link org.w3c.dom.Node} occurs
     * @throws SAXException if return type was {@code HtmlCleanerConst.TO_NODE} and exception
     *      during conversion to {@link org.w3c.dom.Node} occurs
     * @see #clean(Object, int, java.util.Properties)
     */
    public static Object clean(Object html, int returnType)
            throws IOException, ParserConfigurationException, SAXException {
        HtmlCleaner cleaner = getCleaner();
        return convertResult(cleanHtml(html, cleaner), returnType, cleaner.getProperties());
    }
}
