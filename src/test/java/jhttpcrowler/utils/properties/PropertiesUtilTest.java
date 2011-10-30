package jhttpcrowler.utils.properties;

import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * PropertiesUtilTest
 *
 * @author Sergey Pilukin
 */
public class PropertiesUtilTest {
    private InputStream inputStream;

    @Test
    public void testStreamUtil() {
        Properties def = new Properties();
        def.put("def", "value");

        Properties props = PropertiesUtil.load("jhttpcrowler/utils/properties/props.properties", def);

        assertNotNull(props);
        assertEquals(1, props.size());
        assertEquals("value", props.getProperty("def"));
        assertEquals("value", props.getProperty("key"));
    }
}
