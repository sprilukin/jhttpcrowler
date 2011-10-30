package jhttpcrowler.core.plugin;

import jhttpcrowler.utils.file.FileUtil;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * @author Sergey
 * @since 05.12.2010 4:08:08
 *        <p/>
 *        TODO: add class description
 */
public class FileImpl implements jhttpcrowler.core.plugin.File {
    public static final String NAME = "file";

    public String readAsString(String path) throws IOException {
        return FileUtil.readFileToString(path);
    }

    public InputStream readAsStream(String path) throws IOException {
        return FileUtil.readFileToInputStream(path);
    }

    private void createDirIfNotExists(String path) throws IOException {
        int slashPos = path.lastIndexOf(java.io.File.pathSeparator);
        if (slashPos < 0) {
            slashPos = path.lastIndexOf("\\") >= 0 ? path.lastIndexOf("\\") : path.lastIndexOf("/");
        }

        String dir = path.substring(0, slashPos);
        FileUtil.createDirIfNotExist(dir);
    };

    public void save(String file, String path) throws IOException {
        createDirIfNotExists(path);
        FileUtil.saveBytesToFile(file.getBytes("UTF-8"), path);
    }

    public void save(InputStream file, String path) throws IOException {
        createDirIfNotExists(path);
        FileUtil.saveBytesToFile(IOUtils.toByteArray(file), path);
    }

    public Boolean exists(String path) throws IOException {
        return FileUtil.exists(path);
    }

    public String getName() {
        return NAME;
    }
}
