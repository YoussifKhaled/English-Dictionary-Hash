package com.perfecthashing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class mySet<T> implements Iterable<T> {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public mySet() {
        list = new ArrayList<T>();
    }

    public void add(T item) {
        if (!contains(item)) {
            list.add(item);
        }
    }

    public void remove(T item) {
        list.remove((Object)item);
    }

    public boolean contains(T item) {
        return list.contains(item);
    }

    public int size(){
        return list.size();
    }

    @Override
    public Iterator<T> iterator() {
        return new myIterator();
    }

    private class myIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < list.size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements to iterate");
            }
            return list.get(currentIndex++);
        }
    }

}
