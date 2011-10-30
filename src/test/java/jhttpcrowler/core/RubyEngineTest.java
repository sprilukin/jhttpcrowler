package jhttpcrowler.core;

import jhttpcrowler.core.impl.DynamicScriptEngine;
import org.junit.Test;
import org.unitils.inject.annotation.TestedObject;
import org.w3c.dom.Node;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests {@link DynamicScriptEngine}
 * initialized with ruby language
 *
 * @author Sergey Prilukin
 */
public class RubyEngineTest extends BaseTest {

    @TestedObject
    private DynamicScriptEngine engine;

    @Test
    public void rubyEngineTest() throws Exception {
        engine.setEngineName("jruby");
        InputStream resourceAsStream = getResourceAsStream("jhttpcrowler/core/script.rb");
        List<Node> result = (List<Node>)engine.execute(resourceAsStream);
        resourceAsStream.close();
        assertNotNull(result);
        assertEquals(4, result.size());
    }

}
