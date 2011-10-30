package jhttpcrowler.core;

/**
 * Define contract for usage in implementation of {@link Engine}.
 * Implementation of this interface will allows execute scripts
 * asynchronously.
 *
 * @author Sergey Prilukin
 */
public interface Queue {

    /**
     * Add instance of {@link Runnable} for deferred execution in different thread.
     *
     * @param task instance of {@link Runnable}
     */
    public void add(Runnable task);
}
