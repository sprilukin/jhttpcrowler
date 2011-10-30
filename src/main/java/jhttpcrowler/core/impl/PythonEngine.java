package jhttpcrowler.core.impl;

import jhttpcrowler.core.Plugin;
import org.python.util.PythonInterpreter;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Sergey
 * @since 04.12.2010 19:03:46
 *        <p/>
 *        TODO: add class description
 */
public class PythonEngine extends BaseEngine {

    public static final String RESULT_NAME = "resultWrapper";

    public class ResultWrapper {
        private Object result;

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }
    }

    private Object executeScript(InputStream script, Map<String, Object> params) throws Exception {
        PythonInterpreter pi = new PythonInterpreter(null);

        //Set default plugins
        for (String plugin: getDefaultPlugins()) {
            Plugin pluginInstance = getPlugin(plugin);
            if (plugin != null) {
                pi.set(pluginInstance.getName(), pluginInstance);
            }
        }

        //set additional params
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry: params.entrySet()) {
                pi.set(entry.getKey(), entry.getValue());
            }
        }

        //Set result object
        ResultWrapper resultWrapper = new ResultWrapper();
        pi.set(RESULT_NAME, resultWrapper);

        pi.execfile(script);

        return resultWrapper.getResult();
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
