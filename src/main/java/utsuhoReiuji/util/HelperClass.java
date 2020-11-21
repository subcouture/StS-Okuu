package utsuhoReiuji.util;

import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;

//taken from my boy erasels, 1000 times smarter than me

public class HelperClass {
    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String capitalize(String str, String match) {
        return str.replace(match, capitalize(match));
    }

    public static <T> T getRandomItem(ArrayList<T> list, Random rng) {
        return list.isEmpty() ? null : list.get(rng.random(list.size() - 1));
    }
}