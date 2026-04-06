package HilosYConcurrencia.Practice1;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    AtomicInteger counter = new AtomicInteger(0);

    public void increment () {
        counter.incrementAndGet();
    }

    public int getCounter() {
        return counter.get();
    }
}
