package jhttpcrowler.core;

import java.util.Map;

/**
 * Defines engine which can execute scripts.
 *
 * @author Sergey Prilukin
 */
public interface Engine {

    /**
     * Execute script asynchronously using specified params
     * and callback
     *
     * @param script script to execute. Can be {@link String}, {@link java.io.InputStream} or of other types
     *  (different implementations can accept different types)
     * @param params map of script params which will be bound and can be used inside script
     * @param callback instance of {@link ExecutionCallback} which method {@link ExecutionCallback#callback(Object)}
     *  will be called after execution will be finished.
     * @throws Exception if exception during execution occurs.
     */
    public void execute(Object script, Map<String, Object> params, ExecutionCallback callback) throws Exception;

    /**
     * Same as {@link #execute(Object, java.util.Map, jhttpcrowler.core.Engine.ExecutionCallback)}
     * but no params passed.
     *
     * @param script script to execute. Can be {@link String}, {@link java.io.InputStream} or of other types
     *  (different implementations can accept different types)
     * @param callback instance of {@link ExecutionCallback} which method {@link ExecutionCallback#callback(Object)}
     *  will be called after execution will be finished.
     * @throws Exception if exception during execution occurs.
     */
    public void execute(Object script, ExecutionCallback callback) throws Exception;

    /**
     * Execute script synchronously with passed params
     *
     * @param script script to execute. Can be {@link String}, {@link java.io.InputStream} or of other types
     *  (different implementations can accept different types)
     * @param params map of script params which will be bound and can be used inside script
     * @return result of script execution
     * @throws Exception if exception during execution occurs.
     */
    public Object execute(Object script, Map<String, Object> params) throws Exception;

    /**
     * Same as {@link #execute(Object, java.util.Map)}
     * but no params allowed
     *
     * @param script script to execute. Can be {@link String}, {@link java.io.InputStream} or of other types
     *  (different implementations can accept different types)
     * @return result of script execution
     * @throws Exception if exception during execution occurs.
     */
    public Object execute(Object script) throws Exception;

    /**
     * Defines callback which can be called after asynchronous script execution is finished.
     */
    public interface ExecutionCallback {
        public void callback(Object result);
    }
}
