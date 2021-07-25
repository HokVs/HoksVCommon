package dev.hoksv.common.spigot.nms;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <p>EntityReflection class.</p>
 *
 * @author andyl
 * @version $Id: $Id
 */
@SuppressWarnings("all")
public class EntityReflection {

    /**
     * <p>getClass.</p>
     *
     * @param name a {@link java.lang.String} object
     * @param args a {@link java.lang.Object} object
     * @return a {@link java.lang.Object} object
     * @throws java.lang.Exception if any.
     */
    public static Object getClass(String name, Object... args) throws Exception {
        Class c = Class.forName(EntityReflection.getPackageName() + "." + name);
        int params = 0;
        if (args != null) {
            params = args.length;
        }
        for (Constructor co : c.getConstructors()) {
            if (co.getParameterTypes().length != params) {
                continue;
            }
            return co.newInstance(args);
        }
        return null;
    }

    /**
     * <p>getMethod.</p>
     *
     * @param name a {@link java.lang.String} object
     * @param c a {@link java.lang.Class} object
     * @param params a int
     * @return a {@link java.lang.reflect.Method} object
     */
    public static Method getMethod(String name, Class<?> c, int params) {
        for (Method m : c.getMethods()) {
            if (!m.getName().equals(name) || m.getParameterTypes().length != params) {
                continue;
            }
            m.setAccessible(true);
            return m;
        }
        return null;
    }


    /**
     * <p>getPrivateField.</p>
     *
     * @param object a {@link java.lang.Object} object
     * @param field a {@link java.lang.String} object
     * @return a {@link java.lang.Object} object
     * @throws java.lang.SecurityException if any.
     * @throws java.lang.NoSuchFieldException if any.
     * @throws java.lang.IllegalArgumentException if any.
     * @throws java.lang.IllegalAccessException if any.
     */
    public static Object getPrivateField(Object object, String field) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field objectField = clazz.getDeclaredField(field);
        objectField.setAccessible(true);
        Object result = objectField.get(object);
        objectField.setAccessible(false);
        return result;
    }

    /**
     * <p>setValue.</p>
     *
     * @param instance a {@link java.lang.Object} object
     * @param fieldName a {@link java.lang.String} object
     * @param value a {@link java.lang.Object} object
     * @throws java.lang.Exception if any.
     */
    public static void setValue(Object instance, String fieldName, Object value) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }

    /**
     * <p>getPackageName.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public static String getPackageName() {
        return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }
}
