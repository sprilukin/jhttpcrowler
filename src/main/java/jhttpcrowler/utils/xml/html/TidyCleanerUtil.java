package jhttpcrowler.utils.xml.html;

import jhttpcrowler.utils.xml.xslt.XsltUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.tidy.Tidy;

import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.Properties;

/**
 * <p>Utility class which allows convert any html source to xml
 * using w3c recommended Tidy library</p>
 */
public final class TidyCleanerUtil {

    public static final String DEFAULT_CHARSET = "UTF-8";

    private static Tidy getTidy(Properties properties) {
        Tidy tidy = new Tidy();
        OutputStream es = new ByteArrayOutputStream();
        tidy.setErrout(new PrintWriter(es));

        if (properties != null) {
            tidy.setConfigurationFromProps(properties);
        }

        return tidy;
    }

    private static Node cleanHtml(Closeable html, Properties properties) throws IOException {
        if (html == null) {
            return null;
        }

        Document doc;
        if (html instanceof InputStream) {
            doc = getTidy(properties).parseDOM((InputStream)html, null);
        } else if (html instanceof Reader) {
            Writer writer = new OutputStreamWriter(null);
            doc = getTidy(properties).parseDOM((Reader)html, writer);
        } else {
            throw new IllegalArgumentException(String.format("Type not supported: %s", html.getClass().getName()));
        }

        html.close();

        return doc;
    }

    /**
     * <p>Returns {@code xml} representation of passed {@code html} object</p>
     * <p>
     *     html can be instance of following types:<br/>
     *     {@link InputStream}, {@link File}, {@link String}, {@link URL}, {@link Reader}
     * </p>
     *
     * @param html html source which needs to be xmlized
     * @param properties Tidy configuration properties
     * @return xml version of passed html object
     * @throws IOException if exception occurs during parsing
     * @see {@link org.w3c.tidy.Configuration}
     * @see {@link Tidy}
     */
    public static Node toXml(Object html, Properties properties) throws IOException {
        if (html == null) {
            return null;
        }

        if (html instanceof InputStream) {
            return cleanHtml((InputStream)html, properties);
        } else if (html instanceof File) {
            FileInputStream fis = new FileInputStream((File)html);
            return cleanHtml(fis, properties);
        } else if (html instanceof String) {
            ByteArrayInputStream bais = new ByteArrayInputStream(((String)html).getBytes(DEFAULT_CHARSET));
            return cleanHtml(bais, properties);
        } else if (html instanceof URL) {
            return cleanHtml(((URL) html).openStream(), properties);
        } else if (html instanceof Reader) {
            return cleanHtml((Reader)html, properties);
        } else {
            throw new IllegalArgumentException(String.format("Type not supported: %s", html.getClass().getName()));
        }
    }

    /**
     * Same as {@link #toXml(Object, java.util.Properties)}
     * but uses default config properties
     *
     * @param html html source which needs to be xmlized
     * @return xml version of passed html object
     * @throws IOException if exception occurs during parsing
     */
    public static Node toXml(Object html) throws IOException {
        return toXml(html, null);
    }

    /**
     * Returns string epresentation of cleaned html
     *
     * @param html html source which needs to be xmlized
     * @return string version of passed html object
     * @throws IOException if exception occurs during parsing
     * @throws TransformerException if exception occurs during converting to string
     */
    public static String toString(Object html) throws IOException, TransformerException {
        Node xml = toXml(html, null);
        return XsltUtil.xmlToString(xml);
    }
}
