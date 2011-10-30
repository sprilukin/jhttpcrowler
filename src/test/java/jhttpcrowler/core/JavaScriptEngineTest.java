package jhttpcrowler.core;

import jhttpcrowler.core.impl.JavaScriptEngine;
import org.junit.Test;
import org.mozilla.javascript.NativeJavaObject;
import org.unitils.inject.annotation.TestedObject;
import org.w3c.dom.Node;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests {@link JavaScriptEngine}
 *
 * @author Sergey Prilukin
 */
public class JavaScriptEngineTest extends BaseTest {

    @TestedObject
    private JavaScriptEngine engine;

    @Test
    public void javaScriptEngineTest() throws Exception {
        InputStream resourceAsStream = getResourceAsStream("jhttpcrowler/core/script.js");
        List<Node> result = (List<Node>)((NativeJavaObject)engine.execute(resourceAsStream)).unwrap();
        assertNotNull(result);
        assertEquals(4, result.size());
    }

}
