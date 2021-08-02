package dev.hoksv.common.random;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Random collection
 * @param <T> Object to put in the collection
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

    /**
     * Add an element to the collection
     * @param chance The chance from 1-100
     * @param obj Object to add
     */
    public void add(double chance, T obj) {
        if (chance <= 0.0D) return;

        total += chance;
        map.put(total, obj);
    }

    /**
     *  Size of the map
     * @return size of the map
     */
    public int size() {
        return map.size();
    }

    /**
     * Clears the map
     */
    public void clear() {
        map.clear();
    }

    /**
     * Gets the next element the collection
     * @return The next element in the collection.
     */
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