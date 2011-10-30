package jhttpcrowler.core.impl;

import jhttpcrowler.core.Plugin;
import jhttpcrowler.utils.properties.PropertiesUtil;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.Properties;

/**
 * @author Sergey
 * @since 04.12.2010 19:03:46
 *        <p/>
 *        TODO: add class description
 */
public class JavaScriptEngine extends BaseEngine {

    public static final String PROPERTIES_FILE = "jhttpcrowler.properties";
    public static final String PROPERTIES_SCRIPTS_KEY = "java.scripts";
    public static final String PROPERTIES_SCRIPT_TO_EVAL_KEY = "java.script.eval";
    public static final String PROPERTIES_SCRIPTS_SEPARATOR = ",";

    private String getScriptFromClasspath(String path) throws Exception {
        InputStream fileResource = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        return new String(IOUtils.toByteArray(fileResource), "UTF-8");
    }

    private void loadLibs(Context cx, Scriptable scope) throws Exception {
        Properties pluginProperties = PropertiesUtil.load(PROPERTIES_FILE, null);

        String scriptToEval = (String)pluginProperties.get(PROPERTIES_SCRIPT_TO_EVAL_KEY);
        if (scriptToEval != null && !"".equals(scriptToEval.trim())) {
            cx.evaluateString(scope, scriptToEval, "evalscript", 1, getScriptProtectionDomain());
        }

        String scripts = (String)pluginProperties.get(PROPERTIES_SCRIPTS_KEY);
        if (scripts != null && !"".equals(scripts.trim())) {
            String[] scriptsArray = scripts.split(PROPERTIES_SCRIPTS_SEPARATOR);
            for (String scriptAsString: scriptsArray) {
                cx.evaluateString(scope, getScriptFromClasspath(scriptAsString), "script", 1, getScriptProtectionDomain());
            }
        }
    }

    private void loadParamsAndPlugins(ScriptableObject scope, Map<String, Object> params) throws Exception {
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry: params.entrySet()) {
                scope.defineProperty(entry.getKey(), entry.getValue(), ScriptableObject.PERMANENT);
            }
        }

        for (String plugin: getDefaultPlugins()) {
            Plugin pluginInstance = getPlugin(plugin);
            if (pluginInstance != null) {
                scope.defineProperty(pluginInstance.getName(), pluginInstance, ScriptableObject.PERMANENT);
            }
        }
    }

    private Object executeScript(String javaScript, Map<String, Object> params) throws Exception {
        Context cx = Context.enter();
        cx.setOptimizationLevel(-1);
        cx.setLanguageVersion(Context.VERSION_1_5);

        try {
            ScriptableObject scope = cx.initStandardObjects();

            // evaluate js file
            try {
                loadLibs(cx, scope);
                loadParamsAndPlugins(scope, params);

                return cx.evaluateString(scope, javaScript, "run", 1, getScriptProtectionDomain());
            } catch (EcmaError e) {
                throw new Exception("Error during script evaluation [" + e.lineNumber() + "] " + e.getErrorMessage());
            }
        } finally {
            Context.exit();
        }
    }

    protected ProtectionDomain getScriptProtectionDomain() {
        // if there's no security manager, don't use a domain
        if (System.getSecurityManager() == null) {
            return null;
        }

        return getClass().getProtectionDomain();
    }

    public Object execute(Object script, Map<String, Object> params) throws Exception {
        if (script == null) {
            return null;
        }

        String scriptText;

        if (script instanceof String) {
            scriptText = script.toString();
        } else if (script instanceof InputStream) {
            scriptText = new String(IOUtils.toByteArray((InputStream) script), "UTF-8");
        } else if (script instanceof File) {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream((File)script));
            scriptText = new String(IOUtils.toByteArray(bis), "UTF-8");
        } else {
            String message = String.format("Such kind of script object is not supported: %s", script.toString());
            throw new UnsupportedOperationException(message);
        }

        return executeScript(scriptText, params);
    }

}
