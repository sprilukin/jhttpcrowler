package jhttpcrowler.core;

import java.util.Map;

/**
 * Interface for all script classes which are executed by
 * {@link jhttpcrowler.core.impl.GroovyEngine}
 *
 * @author Sergey Prilukin
 */
public interface Script {

    /**
     * Invoke script with parameters.
     *
     * @param plugins map of plugins. {@link Plugin}
     * @param params params which will be bind inside script
     * @return result of script invocation.
     */
    public Object execute(Map<String, Plugin> plugins, Map<String, Object> params);
}
