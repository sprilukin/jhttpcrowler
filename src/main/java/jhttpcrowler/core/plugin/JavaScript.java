package jhttpcrowler.core.plugin;

import jhttpcrowler.core.Plugin;

import java.util.Map;

/**
 * TODO: add description
 *
 * @author Sergey Prilukin
 *         Date: 22.07.11 17:20
 */
public interface JavaScript extends Plugin {
    public Object eval(Object script) throws Exception;
    public Object eval(Object script, Object... params) throws Exception;
    public Object eval(Object script, Map<String, Object> params) throws Exception;
    public Object envjs(Object script) throws Exception;
    public Object envjs(Object script, Object... params) throws Exception;
    public Object envjs(Object script, Map<String, Object> params) throws Exception;
}
