package jhttpcrowler.core;

import jhttpcrowler.core.impl.GroovyEngine;
import org.junit.Test;
import org.unitils.inject.annotation.TestedObject;
import org.w3c.dom.Node;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests {@link GroovyEngine}
 *
 * @author Sergey Prilukin
 */
public class GroovyEngineTest extends BaseTest {

    @TestedObject
    private GroovyEngine engine;

    @Test
    public void testMultithreadEngineClassExecutor() throws Exception {
        InputStream resourceAsStream = getResourceAsStream("jhttpcrowler/core/GroovyScript.groovy");
        List<Node> result = (List<Node>)engine.execute(resourceAsStream);
        assertNotNull(result);
        assertEquals(4, result.size());
    }

}
