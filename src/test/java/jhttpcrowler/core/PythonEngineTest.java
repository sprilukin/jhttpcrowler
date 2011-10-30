package jhttpcrowler.core;

import jhttpcrowler.core.impl.PythonEngine;
import org.junit.Test;
import org.unitils.inject.annotation.TestedObject;
import org.w3c.dom.Node;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests http client
 *
 * @author Sergey Prilukin
 */
public class PythonEngineTest extends BaseTest {

    @TestedObject
    private PythonEngine engine;

    @Test
    public void pythonEngineTest() throws Exception {
        InputStream resourceAsStream = getResourceAsStream("jhttpcrowler/core/script.py");
        List<Node> result = (List<Node>)engine.execute(resourceAsStream);
        assertNotNull(result);
        assertEquals(4, result.size());
    }

}
