package com.perfecthashing;

import java.util.ArrayList;

public interface PerfectHashing<T>{
    public int insert(T item);
    public int delete(T item);
    public boolean search(T item);
    public ArrayList<T> getElements();
}
