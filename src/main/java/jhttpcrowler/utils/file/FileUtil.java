package jhttpcrowler.utils.file;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Helper class for file operations
 *
 * @author Sergey Prilukin
 */
public final class FileUtil {

    private static final String FILE_NAME_FORBIDDEN_SYMBOLS = "[\\.]+";
    private static final String FILE_NAME_FORBIDDEN_SYMBOLS_REPLACEMENT = "_";

    /**
     * Save byte array to file
     *
     * @param bytes byte array o be saved
     * @param path  path to file
     * @throws IOException if saving fails
     */
    public static void saveBytesToFile(byte[] bytes, String path) throws IOException {
        FileOutputStream file = new FileOutputStream(new File(path));
        file.write(bytes);
        file.flush();
        file.close();
    }

    /**
     * Save specified string to a file.

     * @param string string to be saved
     * @param path path to file to save string
     * @throws IOException if error uccured while saving
     */
    public static void saveStringToFile(String string, String path) throws IOException {
        FileWriter fw = new FileWriter(path);
        fw.write(string);
        fw.flush();
        fw.close();
    }

    public static InputStream readFileToInputStream(String path) throws IOException {
        return new BufferedInputStream(new FileInputStream(path));
    }

    public static String readFileToString(String path) throws IOException {
        InputStream inputStream = readFileToInputStream(path);
        String result = new String(IOUtils.toByteArray(inputStream), "UTF-8");
        inputStream.close();
        return result;
    }

    /**
     * Creates directory if it doesnt exists
     * 
     * @param dir path to directory
     * @throws IOException if creation fails
     */
    public static void createDirIfNotExist(String dir) throws IOException {
        File directory = new File(dir);
        
        if (!directory.exists()) {
            Boolean result = directory.mkdirs();
            if (!result) {
                throw new IOException("Could not create directory: " + dir);
            }
        }
    }

    /**
     * Checks wether file or directory exists
     *
     * @param path fill path to a file or a directory
     * @return {@code true} if file exists {@code false} otherwise
     */
    public static boolean exists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static String removeIllegalCharacters(String fileName) {
        return fileName != null
                ? fileName.replaceAll(
                        FILE_NAME_FORBIDDEN_SYMBOLS,
                        FILE_NAME_FORBIDDEN_SYMBOLS_REPLACEMENT
                   )
                : null;
    }
}
