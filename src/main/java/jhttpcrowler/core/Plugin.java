package jhttpcrowler.core;

/**
 * Defines plugin which can be bound inside script.
 *
 * @author Sergey Prilukin
 */
public interface Plugin {

    /**
     * Returns name of the plugin which will be used to bound implementation of plugin
     * insude script.
     *
     * @return name of the plugin.
     */
    public String getName();
}
