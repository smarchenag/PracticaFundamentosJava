package HilosYConcurrencia.Practice2;

import java.util.LinkedList;
import java.util.Queue;

public class SharedBuffer {
    private Queue<Integer> buffer = new LinkedList<>();
    private int maxSize;

    public SharedBuffer(int maxSize) {
        this.maxSize = maxSize;
    }

    //Llamado por el Producer
    public synchronized void produce (int value) throws InterruptedException {
        while (buffer.size() == maxSize) {
            wait(); //Buffer lleno -> espera
        }
        buffer.add(value);
        System.out.println("Produced: " + value + " | Buffer: " + buffer);
        notifyAll(); // Avisa al consumer que hay datos
    }

    //Llamado por el consumer
    public synchronized int consume () throws InterruptedException {
        while (buffer.isEmpty()) {
            wait(); // Buffer vacio -> espera
        }
        int value = buffer.poll();
        System.out.println("Consumed: " + value + " | Buffer: " + buffer);
        notifyAll();
        return value;
    }
}
