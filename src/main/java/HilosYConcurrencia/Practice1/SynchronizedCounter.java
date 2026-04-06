package HilosYConcurrencia.Practice1;

public class SynchronizedCounter {
    private int counter = 0;

    public synchronized void increment () {
        counter++;
    }

    public int getCounter() {
        return counter;
    }
}
