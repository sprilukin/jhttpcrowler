package jhttpcrowler.core.plugin;

import jhttpcrowler.core.Plugin;

/**
 * TODO: add description
 *
 * @author Sergey Prilukin
 * Date: 22.07.11 15:37
 */
public interface PluginFactory {
    public Plugin getPlugin(String pluginClass) throws Exception;
}
