package dev.hoksv.common.spigot.location;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Class to deal with
 * locations
 *
 * @author andyl
 * @version $Id: $Id
 */
public class LocationUtils {

    private final static Random rand = new Random();

    /**
     * Deserializes a serialized location in the form of `43,12,309` to
     * a location
     *
     * @param wrld The world that the location should be in
     * @param loc Serialized Location
     * @return A deserialized Bukkit Location
     */
    public static Location deserialize(World wrld, String loc) {
        String[] location = loc.split(",");
        double x = Double.parseDouble(location[0]);
        double y = Double.parseDouble(location[1]);
        double z = Double.parseDouble(location[2]);
        return new Location(wrld, x, y, z);
    }

    /**
     * Deserializes a list of locations in the form of `43,129,392`
     *
     * @param serializedLocs List of serialized locations
     * @return List of Lazy Locations
     */
    public static List<LazyLocation> deserialize(List<String> serializedLocs) {
        List<LazyLocation> locations = new ArrayList<>();
        for (String serializedLoc : serializedLocs) {
            locations.add(deserialize(serializedLoc));
        }
        return locations;
    }

    /**
     * Deserialize a serialized location
     *
     * @param loc Location to deserialize
     * @return Lazy Location of that serialized location
     */
    public static LazyLocation deserialize(String loc) {
        String[] location = loc.split(",");
        int x = Integer.parseInt(location[0]);
        int y = Integer.parseInt(location[1]);
        int z = Integer.parseInt(location[2]);

        return new LazyLocation(x, y, z);
    }

    /**
     * Check to see if a Bukkit location is in bounds between 2 bukkit locations
     *
     * @param loc Bukkit {@link org.bukkit.Location}
     * @param point1 First Bukkit {@link org.bukkit.Location}
     * @param point2 Second Bukkit {@link org.bukkit.Location}
     * @return a boolean
     */
    public static boolean inBounds(Location loc, Location point1, Location point2) {
        int x1 = Math.min(point1.getBlockX(), point2.getBlockX());
        int y1 = Math.min(point1.getBlockY(), point2.getBlockY());
        int z1 = Math.min(point1.getBlockZ(), point2.getBlockZ());
        int x2 = Math.max(point1.getBlockX(), point2.getBlockX());
        int y2 = Math.max(point1.getBlockY(), point2.getBlockY());
        int z2 = Math.max(point1.getBlockZ(), point2.getBlockZ());
        Vector p1 = new Vector(x1, y1, z1);
        Vector p2 = new Vector(x2, y2, z2);

        return loc.getBlockX() >= p1.getBlockX() && loc.getBlockX() <= p2.getBlockX()
                && loc.getBlockY() >= p1.getBlockY() && loc.getBlockY() <= p2.getBlockY()
                && loc.getBlockZ() >= p1.getBlockZ() && loc.getBlockZ() <= p2.getBlockZ();
    }

    /**
     * <p>inBounds.</p>
     *
     * @param loc a {@link org.bukkit.Location} object
     * @param lowerBounds a {@link java.lang.String} object
     * @param upperBounds a {@link java.lang.String} object
     * @return a boolean
     */
    public static boolean inBounds(Location loc, String lowerBounds, String upperBounds) {
        LazyLocation point1 = deserialize(lowerBounds);
        LazyLocation point2 = deserialize(upperBounds);

        return inBounds(loc, point1, point2);
    }

    /**
     * Gets a random location within a list
     *
     * @param locations List of locations
     * @return A single locations
     */
    public static LazyLocation getRandomLocation(List<LazyLocation> locations) {
        return locations.get(rand.nextInt(locations.size()));
    }

    /**
     * Get's a random location between 2 coordinates
     *
     * @param loc1 Location 1
     * @param loc2 Location 2
     * @return a Lazy location between the points
     */
    public static LazyLocation getRandomLocation(LazyLocation loc1, LazyLocation loc2) {
        float randX = pickRandomPointBetween(loc1.getX(), loc2.getX());
        float randY = pickRandomPointBetween(loc1.getY(), loc2.getY());
        float randZ = pickRandomPointBetween(loc1.getZ(), loc2.getZ());
        return new LazyLocation(randX, randY, randZ);
    }

    /**
     * Picks a random point between 2 points
     *
     * @param corner1 Corner 1
     * @param corner2 Corner 2
     * @return A point between
     */
    public static float pickRandomPointBetween(float corner1, float corner2) {
        if (corner1 == corner2) {
            return corner1;
        }
        float delta = corner2 - corner1;
        float offset = rand.nextFloat() * delta;
        return corner1 + offset;
    }

    /**
     * Checks whether a location is between 2 locations
     *
     * @param loc Location to check
     * @param lb Lower bounds of the first location
     * @param ub Upper bounds of the second location
     * @return In bounds
     */
    public static boolean inBounds(Location loc, LazyLocation lb, LazyLocation ub) {
        int x1 = Math.min(lb.getX(), ub.getX());
        int y1 = Math.min(lb.getY(), ub.getY());
        int z1 = Math.min(lb.getZ(), ub.getZ());
        int x2 = Math.max(lb.getX(), ub.getX());
        int y2 = Math.max(lb.getY(), ub.getY());
        int z2 = Math.max(lb.getZ(), ub.getZ());
        Vector point1 = new Vector(x1, y1, z1);
        Vector point2 = new Vector(x2, y2, z2);

        return loc.getBlockX() >= point1.getBlockX() && loc.getBlockX() <= point2.getBlockX()
                && loc.getBlockY() >= point1.getBlockY() && loc.getBlockY() <= point2.getBlockY()
                && loc.getBlockZ() >= point1.getBlockZ() && loc.getBlockZ() <= point2.getBlockZ();
    }

    /**
     * <p>serialize.</p>
     *
     * @param location a {@link org.bukkit.Location} object
     * @return a {@link java.lang.String} object
     */
    public static String serialize(Location location) {
        return location.getBlockX() + "," + location.getBlockY() + "," +location.getBlockZ();
    }
}
