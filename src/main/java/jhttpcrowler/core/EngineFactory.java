package jhttpcrowler.core;

/**
 * Defines factory which allows to retreive implementation of {@link Engine}
 *
 * @author Sergey Prilukin
 */
public interface EngineFactory {

    /**
     * Returns implementation of {@link Engine} by its name
     * or by name of script language.
     *
     * @param engineName name of engine or name of script language
     * @return {@link Engine} for this name
     * @throws Exception if exception occurs during retreiving engine.
     */
    public Engine getEngine(String engineName) throws Exception;
}
