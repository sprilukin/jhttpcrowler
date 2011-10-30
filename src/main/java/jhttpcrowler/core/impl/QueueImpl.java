package jhttpcrowler.core.impl;

import jhttpcrowler.core.Queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Sergey
 * @since 05.12.2010 23:10:04
 *        <p/>
 *        TODO: add class description
 */
public class QueueImpl implements Queue {
    private BlockingQueue<Runnable> poolQueue = new LinkedBlockingQueue<Runnable>();
    private ExecutorService executor = new ThreadPoolExecutor(5, 5, 30, TimeUnit.SECONDS, poolQueue);
    
    public void add(Runnable task) {
        executor.execute(task);
    }
}
