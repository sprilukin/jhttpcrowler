package jhttpcrowler.core.impl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import jhttpcrowler.core.Plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey
 * Date: 08.07.11
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */
public class GroovyShellEngine extends BaseEngine {

    private void bindPlugin(Binding binding, String plugin) throws Exception {
        Plugin instance = getPlugin(plugin);
        binding.setVariable(instance.getName(), instance);
    }

    private Binding getBinding(Map<String, Object> params) throws Exception {
        Binding binding = new Binding();
        for (String plugin: getDefaultPlugins()) {
            if (plugin != null) {
                bindPlugin(binding, plugin);
            }
        }

        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry: params.entrySet()) {
                binding.setVariable(entry.getKey(), entry.getValue());
            }
        }

        return binding;
    }

    private GroovyShell getGroovyShell(Map<String, Object> params) throws Exception {
        Binding binding = getBinding(params);
        return new GroovyShell(binding);
    }

    public Object execute(Object script, Map<String, Object> params) throws Exception {
        if (script == null) {
            return null;
        }

        Object result;

        if (script instanceof String) {
            result = getGroovyShell(params).evaluate((String) script);
        } else if (script instanceof InputStream) {
            Reader in = new BufferedReader(new InputStreamReader((InputStream)script));
            result = getGroovyShell(params).evaluate(in);
        } else if (script instanceof File) {
            result = getGroovyShell(params).evaluate((File) script);
        } else {
            throw new UnsupportedOperationException("Such kind of script object is not supported");
        }

        return result;
    }
}
