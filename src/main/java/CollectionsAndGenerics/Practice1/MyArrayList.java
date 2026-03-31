package CollectionsAndGenerics.Practice1;

import java.util.Arrays;

public class MyArrayList<T>{
    private Object[] array;

    private int size;

    public MyArrayList() {
        array = new Object[10];
    }

    public MyArrayList(int initialCapacity) {
        array = new Object[initialCapacity];
    }

    private void setArray(Object[] array) {
        this.array = array;
    }

    public int capacity(){
        return array.length;
    }

    public int size(){
        return size;
    }

    public void add(T item){
        if (size() < capacity()) {
            array[size()] = item;
        } else  {
            Object[] newArray = new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            newArray[size()] = item;
            this.setArray(newArray);
        }
        this.size++;
    }

    public T get(int index){
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        return (T) array[index];
    }

    public T remove(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T item = (T) array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[size - 1] = null;
        this.size--;
        return item;
    }

    public boolean contains(T item){
        return indexOf(item) >= 0;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public void clear() {
        this.array = new Object[10];
        this.size = 0;
    }

    public int indexOf(T item) {
        for (int i = 0; i < size; i++) {
            if (array[i] != null && array[i].equals(item)) return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(array[i]);
            if (i != size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
