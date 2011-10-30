package jhttpcrowler.core.plugin;

import jhttpcrowler.core.Engine;
import jhttpcrowler.core.impl.EngineFactoryImpl;

import java.util.Map;

/**
 * TODO: add description
 *
 * @author Sergey Prilukin
 *         Date: 15.09.11 17:25
 */
public class EngineExecutorImpl implements EngineExecutor {
    public static final String NAME = "engine";

    public void execute(String type, Object script, Map<String, Object> params, Engine.ExecutionCallback callback) throws Exception {
        Engine engine = EngineFactoryImpl.getInstance().getEngine(type);
        engine.execute(script, params, callback);
    }

    public String getName() {
        return NAME;
    }
}
