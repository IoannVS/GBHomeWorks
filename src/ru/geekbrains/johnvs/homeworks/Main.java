package ru.geekbrains.johnvs.homeworks;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int[] array = new int[5];
        Random random = new Random();
        for (int j = 0; j < array.length; j++) array[j] = random.nextInt(11);
        boolean result = compare(array);
        System.out.println(Arrays.toString(array));
        System.out.println(result);
    }

    public static boolean compare(int[] arr) {
        int sumLeft = 0, sumRight, sum=0;
        for (int k : arr) sum += k;
        for (int j : arr) {
            sumLeft += j;
            sumRight = sum - sumLeft;
            if (sumLeft == sumRight) return true;
        }
        return false;
    }
}

