package jhttpcrowler.core.plugin;

import jhttpcrowler.core.Plugin;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Sergey
 * @since 05.12.2010 4:04:56
 *        <p/>
 *        TODO: add class description
 */
public interface File extends Plugin {
    public String readAsString(String path) throws IOException;
    public InputStream readAsStream(String path) throws IOException ;
    public void save(String file, String path) throws IOException ;
    public void save(InputStream file, String path) throws IOException ;
    public Boolean exists(String path) throws IOException;
}
