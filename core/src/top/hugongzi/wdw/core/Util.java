package top.hugongzi.wdw.core;

public class Util {
    public static boolean equalsEpsilon(float f, float eq, float epsilon) {
        return Math.abs(f - eq) < epsilon;
    }
}
