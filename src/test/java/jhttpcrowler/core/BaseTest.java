package jhttpcrowler.core;

import anhttpserver.DefaultSimpleHttpServer;
import anhttpserver.HttpRequestContext;
import anhttpserver.SimpleHttpHandlerAdapter;
import anhttpserver.SimpleHttpServer;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.unitils.UnitilsJUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Parent class for all functional tests.
 * It starts instance of {@link SimpleHttpServer}
 * and binds it to example of html page
 *
 * @author Sergey Prilukin.
 */
public abstract class BaseTest extends UnitilsJUnit4 {
    private final String HTTP_RESOURCES_BASE = "jhttpcrowler/core/http";

    protected SimpleHttpServer server;
    private final Map<String, byte[]> resourceCache = new HashMap<String, byte[]>();
    private final static Map<String, String> MIMETypes = new HashMap<String, String>();
    static {
        MIMETypes.put("html", "text/html; charset=UTF-8");
        MIMETypes.put("jpg", "image/jpeg");
        MIMETypes.put("js", "text/javascript");
    }

    // Returns MIME type for file extension, text/html by default
    private String getContentType(String path) {
        String extension = path.substring(path.lastIndexOf(".") + 1, path.length());
        return MIMETypes.containsKey(extension) ? MIMETypes.get(extension) : MIMETypes.get("html");
    }

    @Before
    public void start() {
        server = new DefaultSimpleHttpServer();
        server.start();
        server.addHandler("/", new SimpleHttpHandlerAdapter() {
            public byte[] getResponse(HttpRequestContext httpRequestContext) throws IOException {
                String path = httpRequestContext.getRequestURI().getPath();
                byte[] response = resourceCache.get(path);

                if (response == null) {
                    synchronized (resourceCache) {
                        if (response == null) {
                            try {
                                InputStream is = getResourceAsStream(HTTP_RESOURCES_BASE + path);
                                response = IOUtils.toByteArray(is);
                                is.close();
                                resourceCache.put(path, response);
                            } catch (Exception e) {
                                //ignore
                            }
                        }
                    }
                }

                this.setResponseHeader("Content-Type", getContentType(path));
                return response;
            }

            @Override
            public int getResponseCode(HttpRequestContext httpRequestContext) {
                String path = httpRequestContext.getRequestURI().getPath();
                return resourceCache.containsKey(path)
                        ? super.getResponseCode(httpRequestContext)
                        : HttpURLConnection.HTTP_NOT_FOUND;
            }
        });
    }

    protected InputStream getResourceAsStream(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

    @After
    public void stop() {
        server.stop();
    }
}
