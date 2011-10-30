package jhttpcrowler.core;

import jhttpcrowler.core.impl.GroovyShellEngine;
import org.junit.Test;
import org.unitils.inject.annotation.TestedObject;
import org.w3c.dom.Node;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests {@link GroovyShellEngine}
 *
 * @author Sergey Prilukin
 */
public class GroovyShellEngineTest extends BaseTest {

    @TestedObject
    private GroovyShellEngine engine;

    @Test
    public void testEngineShellExecutor() throws Exception {
        InputStream resourceAsStream = getResourceAsStream("jhttpcrowler/core/script.groovy");
        List<Node> result = (List<Node>)engine.execute(resourceAsStream);
        assertNotNull(result);
        assertEquals(4, result.size());
    }
}
