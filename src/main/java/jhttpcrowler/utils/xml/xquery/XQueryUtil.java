package jhttpcrowler.utils.xml.xquery;

import jhttpcrowler.utils.xml.xslt.XsltUtil;
import net.sf.saxon.Configuration;
import net.sf.saxon.om.DocumentInfo;
import net.sf.saxon.query.DynamicQueryContext;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;
import net.sf.saxon.trans.XPathException;
import org.apache.commons.io.IOUtils;

import javax.xml.transform.OutputKeys;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for performinq <b>XQuery</b> transformations
 *
 * @author Sergey Prilukin
 */
public final class XQueryUtil {
    //indentation
    private static final Properties properties = new Properties();

    static {
        properties.setProperty(OutputKeys.METHOD, "xml");
        properties.setProperty(OutputKeys.INDENT, "yes");
    }

    private static String getQuery(Object query) throws IOException {
        if (query == null) {
            return null;
        }

        if (query instanceof String) {
            return (String)query;
        } else if (query instanceof InputStream) {
            return new String(IOUtils.toByteArray((InputStream)query), "UTF-8");
        } else if (query instanceof File) {
            return new String(IOUtils.toByteArray(new FileInputStream((File)query)), "UTF-8");
        } else {
            throw new IllegalArgumentException(String.format("Type not supported: %s", query.getClass().getName()));
        }
    }

    public static void transform(Object xml, Object query, Object result, Properties properties) throws XPathException, IOException {

        //create a Configuration object
        Configuration configuration = new Configuration();

        //static and dynamic context
        StaticQueryContext staticQueryContext = configuration.newStaticQueryContext();
        DynamicQueryContext dynamicQueryContext = new DynamicQueryContext(configuration);

        //compilation
        XQueryExpression xQueryExpression = staticQueryContext.compileQuery(getQuery(query));

        //get the XML ready
        DocumentInfo documentInfo = configuration.buildDocument(XsltUtil.getSource(xml));
        dynamicQueryContext.setContextItem(documentInfo);

        //evaluating
        xQueryExpression.run(dynamicQueryContext, XsltUtil.getResult(result), properties);
    }

    public static void transform(Object xml, Object query, Object result) throws XPathException, IOException {
        transform(xml, query, result, properties);
    }
}
