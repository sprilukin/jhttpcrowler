package jhttpcrowler.utils.properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class to load {@link java.util.Properties} instance from classpath
 *
 * @author Sergey Pilukin
 */
public class PropertiesUtil {
    private static final Log log = LogFactory.getLog(PropertiesUtil.class);

    public static Properties load(String file, Properties defaultProps) {
        Properties props = new Properties(defaultProps);

        InputStream fileResource = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        if (fileResource == null) {
            if (log.isWarnEnabled()) {
                log.warn(String.format("[%s] doesn't exists in classpath", file));
            }
            return null;
        }

        try {
            props.load(fileResource);
            if (log.isDebugEnabled()) {
                log.debug(String.format("[%s] loaded successfully", file));
            }
        } catch (IOException e) {
            if (log.isWarnEnabled()) {
                log.warn(String.format("error loading properties file: [%s]", file));
            }
            return null;
        }

        return props;
    }
}
