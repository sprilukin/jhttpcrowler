package jhttpcrowler.core.plugin;

import anhttpclient.WebRequest;
import anhttpclient.WebResponse;
import jhttpcrowler.core.Plugin;
import org.apache.http.cookie.Cookie;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Sergey
 * @since 04.12.2010 12:16:17
 *
 * TODO: add class description
 */
public interface Http extends Plugin {

    public Http fetch(String url) throws IOException;

    public Http fetch() throws IOException;

    public String fetchAsString(String url) throws IOException;

    public String fetchAsString() throws IOException;

    public WebRequest getRequest();

    public WebRequest newRequest();

    public WebRequest newRequest(String requestType);

    public WebResponse getResponse();

    public Map<String, String> getDefaultHeaders();

    public Map<String, String> getAllHeaders();

    public String getDefaultHeader(String headerName);

    public String getAnyHeader(String headerName);

    public Http addDefaultHeaders(Map<String, String> headers);

    public Http addDefaultHeader(String name, String value);

    public Http setDefaultHeaders(Map<String, String> headers);

    public Integer getRetryCount();

    public Http setRetryCount(Integer retryCount);

    public Integer getSocketTimeout();

    public Http setSocketTimeout(Integer socketTimeout);

    public Integer getConnectionTimeout();

    public Http setConnectionTimeout(Integer connectionTimeout);

    public List<Cookie> getCookies();

    public Http addCookie(Cookie cookie);

    public Http addCookies(List<Cookie> cookies);

    public Http clearAllCookies();

    public Http setProxy(String url, int port);

    public Http clearProxy();
}
