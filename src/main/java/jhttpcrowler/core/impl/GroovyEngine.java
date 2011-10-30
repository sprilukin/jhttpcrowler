package jhttpcrowler.core.impl;

import groovy.lang.GroovyClassLoader;
import jhttpcrowler.core.Plugin;
import jhttpcrowler.core.Script;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey
 * @since 04.12.2010 19:03:46
 *        <p/>
 *        TODO: add class description
 */
public class GroovyEngine extends BaseEngine {
    private Object executeClassDynamically(Class<? extends Script> groovyClass, Map<String, Object> params) throws Exception {
        // let's call some method on an instance
        Script script = groovyClass.newInstance();
        Map<String, Plugin> pluginsMap = new HashMap<String, Plugin>();

        for (String plugin: getDefaultPlugins()) {
            Plugin pluginInstance = getPlugin(plugin);
            if (pluginInstance != null) {
                pluginsMap.put(pluginInstance.getName(), pluginInstance);
            }
        }

        return script.execute(pluginsMap, params);
    }

    private GroovyClassLoader getGroovyClassLoader() {
        ClassLoader parent = getClass().getClassLoader();
        return new GroovyClassLoader(parent);
    }

    protected Class<? extends Script> getGroovyClass(Object groovyScript) throws Exception {
        Class clazz = null;

        if (groovyScript instanceof String) {
            clazz = getGroovyClassLoader().parseClass((String)groovyScript);
        } else if (groovyScript instanceof InputStream) {
            clazz = getGroovyClassLoader().parseClass(new String(IOUtils.toByteArray((InputStream)groovyScript), "UTF-8"));
        } else if (groovyScript instanceof File) {
            clazz = getGroovyClassLoader().parseClass((File)groovyScript);
        } else if (groovyScript instanceof Class) {
            clazz = (Class)groovyScript;
        } else {
            String message = String.format("Such kind of script object is not supported: %s", groovyScript.toString());
            throw new UnsupportedOperationException(message);
        }

        if (!Script.class.isAssignableFrom(clazz)) {
            String message = String.format("script class should extends jhttpcrowler.core.Script interface: %s", clazz.getName());
            throw new UnsupportedOperationException(message);
        }

        return (Class<? extends Script>)clazz;
    }

    public Object execute(Object script, Map<String, Object> params) throws Exception {
        if (script == null) {
            return null;
        }

        return executeClassDynamically(getGroovyClass(script), params);
    }

}
