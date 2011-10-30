package jhttpcrowler.core.impl;

import jhttpcrowler.core.DynamicEngine;
import jhttpcrowler.core.Plugin;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

/**
 * @author Sergey
 * @since 04.12.2010 19:03:46
 *        <p/>
 *        TODO: add class description
 */
public class DynamicScriptEngine extends BaseEngine implements DynamicEngine {

    //possible values: "jruby" etc.
    private String engineName;

    public DynamicScriptEngine() {
        /* default constructor */
    }

    public DynamicScriptEngine(String engineName) {
        this.engineName = engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    private Object executeScript(InputStream script, Map<String, Object> params) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();

        ScriptEngine scriptEngine = manager.getEngineByName(engineName);
        scriptEngine.put("engine", engineName);

        Bindings bindings = new SimpleBindings();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry: params.entrySet()) {
                bindings.put(entry.getKey(), entry.getValue());
            }
        }

        //Set default plugins
        for (String plugin: getDefaultPlugins()) {
            Plugin pluginInstance = getPlugin(plugin);
            if (plugin != null) {
                bindings.put(pluginInstance.getName(), pluginInstance);
            }
        }


        Reader reader = new InputStreamReader(script);
        Object result = scriptEngine.eval(reader, bindings);
        reader.close();
        script.close();
        return result;
    }

    public Object execute(Object script, Map<String, Object> params) throws Exception {
        if (script == null) {
            return null;
        }

        InputStream is;

        if (script instanceof String) {
            is = new ByteArrayInputStream(script.toString().getBytes("UTF-8"));
        } else if (script instanceof InputStream) {
            is = (InputStream)script;
        } else if (script instanceof File) {
            is = new BufferedInputStream(new FileInputStream((File)script));
        } else {
            String message = String.format("Such kind of script object is not supported: %s", script.toString());
            throw new UnsupportedOperationException(message);
        }

        return executeScript(is, params);
    }
}
