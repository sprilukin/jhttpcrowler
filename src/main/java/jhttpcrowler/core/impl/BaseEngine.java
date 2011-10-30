package jhttpcrowler.core.impl;

import jhttpcrowler.core.Engine;
import jhttpcrowler.core.Plugin;
import jhttpcrowler.core.Queue;
import jhttpcrowler.core.plugin.PluginFactory;
import jhttpcrowler.utils.properties.PropertiesUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey
 * Date: 08.07.11
 * Time: 18:14
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseEngine implements Engine {
    public static final String PROPERTIES_PLUGINS_FILE = "jhttpcrowler.properties";
    public static final String PROPERTIES_PLUGINS_KEY = "plugins";
    public static final String PROPERTIES_PLUGINS_FACTORY_KEY = "plugins.factory";
    public static final String PROPERTIES_PLUGINS_SEPARATOR = ",";

    private List<String> defaultPlugins = new ArrayList<String>();
    private PluginFactory pluginFactory;
    private Queue queue;

    public BaseEngine() {
        Properties props = PropertiesUtil.load(PROPERTIES_PLUGINS_FILE, null);

        String pluginFactoryString = (String)props.get(PROPERTIES_PLUGINS_FACTORY_KEY);
        try {
            pluginFactory = (PluginFactory)(Class.forName(pluginFactoryString)).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String plugins = (String)props.get(PROPERTIES_PLUGINS_KEY);
        if (plugins != null) {
            String[] pluginsArray = plugins.split(PROPERTIES_PLUGINS_SEPARATOR);
            Collections.addAll(defaultPlugins, pluginsArray);
        }
    }

    public BaseEngine(List<String> defaultPlugins) {
        this.defaultPlugins = defaultPlugins;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Collection<String> getDefaultPlugins() {
        return defaultPlugins;
    }

    protected Plugin getPlugin(String plugin) throws Exception  {
        return pluginFactory.getPlugin(plugin);
    }

    public void execute(final Object script, final Map<String, Object> params, final ExecutionCallback callback) throws Exception {
        queue.add(new Runnable() {
            public void run() {
                try {
                    Object result = execute(script, params);
                    if (callback != null) {
                        callback.callback(result);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void execute(Object script, ExecutionCallback callback) throws Exception {
        execute(script, null, callback);
    }

    public Object execute(Object script) throws Exception {
        return execute(script, (Map<String, Object>)null);
    }
}
