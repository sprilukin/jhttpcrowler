package jhttpcrowler.core.plugin.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey
 * Date: 20.07.11
 * Time: 9:23
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionParametersBuilder {
    private Map<String, String> connectionParams = new HashMap<String, String>();

    public ConnectionParametersBuilder add(String name, String value) {
        this.connectionParams.put(name, value);
        return this;
    }

    public Map<String, String> get() {
        return this.connectionParams;
    }
}
