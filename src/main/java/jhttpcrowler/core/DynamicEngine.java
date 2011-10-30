package jhttpcrowler.core;

/**
 * Defines interface for dynamic languages.
 * Implementation of this interface will pass execution to
 * {@link javax.script.ScriptEngine}
 *
 * @author Sergey Prilukin
 */
public interface DynamicEngine extends Engine {

    /**
     * Sets engine name
     *
     * @param engineName engine name
     */
    public void setEngineName(String engineName);
}
