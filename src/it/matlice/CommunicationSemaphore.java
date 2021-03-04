package it.matlice;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class CommunicationSemaphore<T> extends Semaphore {

    private Queue<T> returns = new LinkedList<>();

    /**
     * Creates a {@code Semaphore} with the given number of
     * permits and nonfair fairness setting.
     *
     * @param permits the initial number of permits available.
     *                This value may be negative, in which case releases
     *                must occur before any acquires will be granted.
     */
    public CommunicationSemaphore(int permits) {
        super(permits);
    }

    public void acquire(){
        throw new RuntimeException();
    }
    public void release(){
        throw new RuntimeException();
    }

    public T r_acquire() throws InterruptedException {
        super.acquire();
        return returns.size() == 0 ? null : returns.poll();
    }

    public void r_release(T data) {
        this.returns.add(data);
        super.release();
    }
}
