package jhttpcrowler.core.plugin;

import jhttpcrowler.core.Plugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO: add description
 *
 * @author Sergey Prilukin
 * Date: 22.07.11 15:39
 */
public final class DefaultPluginFactory implements PluginFactory {

    private static final Log log = LogFactory.getLog(DefaultPluginFactory.class);

    public Plugin getPlugin(String pluginClass) throws Exception {
        Class clazz = null;
        try {
            clazz = Class.forName(pluginClass);
        } catch (ClassNotFoundException e) {
            if (log.isWarnEnabled()) {
                log.warn(String.format("Class not found: %s", pluginClass));
            }
        }

        if (clazz == null) {
            return null;
        }

        if (!Plugin.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("Plugin should implement jhttpcrowler.core.Plugin interface");
        }

        return (Plugin)clazz.newInstance();
    }
}
