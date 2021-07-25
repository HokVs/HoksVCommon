package dev.hoksv.common.random;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Random collection
 * @param <T>
 */
public class RandomCollection<T> {

    private NavigableMap<Double, T> map = new TreeMap<>();
    private Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random rand) {
        this.random = rand;
    }

    public void add(double chance, T obj) {
        if (chance <= 0.0D) return;

        total += chance;
        map.put(total, obj);
    }

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }

    public T next() {
        if (map.isEmpty()) return null;
        try {
            double d = random.nextDouble() * total;
            return map.ceilingEntry(d).getValue();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}