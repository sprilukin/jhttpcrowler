package jhttpcrowler.core.plugin;

import jhttpcrowler.utils.properties.PropertiesUtil;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ScriptableObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * TODO: add description
 *
 * @author Sergey Prilukin
 *         Date: 22.07.11 17:23
 */
public class JavaScriptImpl implements JavaScript {
    public static String NAME = "javaScript";

    public static final String PROPERTIES_FILE = "jhttpcrowler.properties";
    public static final String SCRIPTS_KEY = "java.scripts";
    public static final String SCRIPT_TO_EVAL_KEY = "java.script.eval";
    public static final String SCRIPTS_SEPARATOR = ",";
    public static final String PARAMS_VAR = "params";

    private Context cx;
    private ScriptableObject scope;

    private Properties pluginProperties;

    private void init() {
        if (cx == null) {
            cx = ContextFactory.getGlobal().enterContext();
            cx.setOptimizationLevel(-1);
            cx.setLanguageVersion(Context.VERSION_1_5);
            scope = cx.initStandardObjects();

            pluginProperties = PropertiesUtil.load(PROPERTIES_FILE, null);
        }
    }

    private Object evaluateScript(InputStream script, Map<String, Object> params) throws Exception {
        if (params != null) {
            for (Map.Entry<String, Object> entry: params.entrySet()) {
                scope.defineProperty(entry.getKey(), entry.getValue(), ScriptableObject.PERMANENT);
            }
        }

        Reader reader = new InputStreamReader(script);
        return cx.evaluateReader(scope, reader, "run", 1, null);
    }

    public InputStream getScriptAsInputStream(Object script) throws Exception {
        InputStream is = null;


        if (script instanceof String) {
            is = new ByteArrayInputStream(script.toString().getBytes("UTF-8"));
        } else if (script instanceof InputStream) {
            is = (InputStream)script;
        } else if (script instanceof jhttpcrowler.core.plugin.File) {
            is = new BufferedInputStream(new FileInputStream((java.io.File)script));
        } else {
            String message = String.format("Such kind of script object is not supported: %s", script.toString());
            throw new UnsupportedOperationException(message);
        }

        return is;
    }

    public Object eval(Object script) throws Exception {
        init();
        return evaluateScript(getScriptAsInputStream(script), null);
    }

    public Object eval(Object script, Object... params) throws Exception {
        Map<String, Object> paramsMap = null;

        if (params != null && params.length > 0) {
            paramsMap = new HashMap<String, Object>();
            paramsMap.put(PARAMS_VAR, params);
        }

        init();
        return evaluateScript(getScriptAsInputStream(script), paramsMap);
    }

    public Object eval(Object script, Map<String, Object> params) throws Exception {
        init();
        return evaluateScript(getScriptAsInputStream(script), params);
    }

    private String getScriptFromClasspath(String path) throws Exception {
        InputStream fileResource = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        return new String(IOUtils.toByteArray(fileResource), "UTF-8");
    }

    private void loadLibs() throws Exception {
        init();

        String scriptToEval = (String)pluginProperties.get(SCRIPT_TO_EVAL_KEY);
        if (scriptToEval != null && !"".equals(scriptToEval.trim())) {
            cx.evaluateString(scope, scriptToEval, "evalscript", 1, null);
        }

        String scripts = (String)pluginProperties.get(SCRIPTS_KEY);
        if (scripts != null && !"".equals(scripts.trim())) {
            String[] scriptsArray = scripts.split(SCRIPTS_SEPARATOR);
            for (String scriptAsString: scriptsArray) {
                cx.evaluateString(scope, getScriptFromClasspath(scriptAsString), "script", 1, null);
            }
        }
    }

    public Object envjs(Object script) throws Exception {
        loadLibs();
        return eval(script);
    }

    public Object envjs(Object script, Object... params) throws Exception {
        loadLibs();
        return eval(script, params);
    }

    public Object envjs(Object script, Map<String, Object> params) throws Exception {
        loadLibs();
        return eval(script, params);
    }

    public String getName() {
        return NAME;
    }
}
