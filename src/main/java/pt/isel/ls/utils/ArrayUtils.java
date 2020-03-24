package pt.isel.ls.utils;

import java.util.ArrayList;

public class ArrayUtils {
    public static String[] convertIterableToArray(Iterable<String> iterable) {
        ArrayList<String> array = new ArrayList<>();
        for (String str : iterable) {
            array.add(str);
        }
        String[] toReturn = new String[array.size()];
        return array.toArray(toReturn);
    }
}
