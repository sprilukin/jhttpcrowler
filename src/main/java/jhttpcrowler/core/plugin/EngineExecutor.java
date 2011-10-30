package jhttpcrowler.core.plugin;

import jhttpcrowler.core.Engine;
import jhttpcrowler.core.Plugin;

import java.util.Map;

/**
 * @author Sergey
 * @since 15.09.2011 16:49:56
 *        <p/>
 *        TODO: add class description
 */
public interface EngineExecutor extends Plugin {
    public void execute(String type, Object script, Map<String, Object> params, Engine.ExecutionCallback callback) throws Exception;
}
