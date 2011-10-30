package jhttpcrowler.core.plugin;


import anhttpclient.RequestMethod;
import anhttpclient.WebBrowser;
import anhttpclient.WebRequest;
import anhttpclient.WebResponse;
import anhttpclient.impl.DefaultWebBrowser;
import anhttpclient.impl.request.HttpGetWebRequest;
import anhttpclient.impl.request.HttpPostWebRequest;
import org.apache.http.cookie.Cookie;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sergey
 * @since 04.12.2010 16:49:10
 *        <p/>
 *        TODO: add class description
 */
public class HttpImpl implements Http {

    public static final String NAME = "http";

    WebBrowser webBrowser = new DefaultWebBrowser(true);
    WebRequest webRequest;
    WebResponse webResponse;

    public void setWebBrowser(WebBrowser webBrowser) {
        this.webBrowser = webBrowser;
    }

    public String getName() {
        return NAME;
    }

    public Http fetch(String url) throws IOException {
        if (getRequest() == null) {
            newRequest().setUrl(url);
        } else {
            getRequest().setUrl(url);
        }
        
        return fetch();
    }

    public Http fetch() throws IOException {
        this.webResponse = webBrowser.getResponse(getRequest());
        return this;
    }

    public String fetchAsString(String url) throws IOException {
        return fetch(url).getResponse().getText();
    }

    public String fetchAsString() throws IOException {
        return fetch().getResponse().getText();
    }

    public WebRequest getRequest() {
        return this.webRequest;
    }

    public WebRequest newRequest() {
        return newRequest("GET");
    }

    public WebRequest newRequest(String requestType) {
        this.webRequest =
                RequestMethod.valueOf(requestType.toUpperCase()).equals(RequestMethod.POST)
                        ? new HttpPostWebRequest()
                        : new HttpGetWebRequest();

        return getRequest();
    }

    public WebResponse getResponse() {
        return this.webResponse;
    }

    public Map<String, String> getDefaultHeaders() {
        return this.webBrowser.getHeaders();
    }

    public Map<String, String> getAllHeaders() {
        Map<String, String> browserHeaders = this.webBrowser.getHeaders();
        Map<String, String> requestHeaders = getRequest() != null ? getRequest().getHeaders() : newRequest().getHeaders();

        Map<String, String> headers = new HashMap<String, String>();
        headers.putAll(browserHeaders);
        headers.putAll(requestHeaders);

        return Collections.unmodifiableMap(headers);
    }

    public String getDefaultHeader(String headerName) {
        return getDefaultHeaders().get(headerName);
    }

    public String getAnyHeader(String headerName) {
        return getAllHeaders().get(headerName);
    }

    public Http addDefaultHeaders(Map<String, String> headers) {
        this.webBrowser.addHeaders(headers);
        return this;
    }

    public Http addDefaultHeader(String name, String value) {
        this.webBrowser.addHeader(name, value);
        return this;
    }

    public Http setDefaultHeaders(Map<String, String> headers) {
        this.webBrowser.setDefaultHeaders(headers);
        return this;
    }

    public Integer getRetryCount() {
        return this.webBrowser.getRetryCount();
    }

    public Http setRetryCount(Integer retryCount) {
        this.webBrowser.setRetryCount(retryCount);
        return this;
    }

    public Integer getSocketTimeout() {
        return this.webBrowser.getSocketTimeout();
    }

    public Http setSocketTimeout(Integer socketTimeout) {
        this.webBrowser.setSocketTimeout(socketTimeout);
        return this;
    }

    public Integer getConnectionTimeout() {
        return this.webBrowser.getConnectionTimeout();
    }

    public Http setConnectionTimeout(Integer connectionTimeout) {
        this.webBrowser.setConnectionTimeout(connectionTimeout);
        return this;
    }

    public List<Cookie> getCookies() {
        return this.webBrowser.getCookies();
    }

    public Http addCookie(Cookie cookie) {
        this.webBrowser.addCookie(cookie);
        return this;
    }

    public Http addCookies(List<Cookie> cookies) {
        this.webBrowser.addCookies(cookies);
        return this;
    }

    public Http clearAllCookies() {
        this.webBrowser.clearAllCookies();
        return this;
    }

    public Http setProxy(String url, int port) {
        this.webBrowser.setProxy(url, port);
        return this;
    }

    public Http clearProxy() {
        this.webBrowser.clearProxy();
        return this;
    }
}
