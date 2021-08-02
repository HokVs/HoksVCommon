package dev.hoksv.common.reflection;

public class ReflectionUtils {

    /**
     * See if a class exists without throwing class not found exceptions
     * @param className Class name to check for
     * @return true if it exists, false if not.
     */
    public static boolean safeClassForName(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            // this doesn't exist :D
            return false;
        }
    }
}
