package ru.geekbrains.johnvs.homeworks;

import java.util.Arrays;

public class HW1_2 {

    public static void main(String[] args) {

        // 1. Инверсия 1 и 0 в массиве.
        int[] arr1 = new int[15];
        // Наполнение массива 1 и 0 в рандомном порядке
        for (int i=0; i < 15; i++) arr1[i] = (int) Math.round(Math.random());
        // Инверсия/замена значений в массиве
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] == 1) arr1[i] = 0;
            else arr1[i] = 1;
        }


        // 2. Заполнение массива из пустых элементов с помощью цикла числовыми значениями с шагом 3
        int[] arr2 = new int[8];
        int value = 0;
        for (int i=0; i < arr2.length; i++) {
            arr2[i] = value;
            value += 3;
        }


        // 3. Проверка элементов массива. Элемент со значением меньше 6 умножается на 2
        int[] arr3 = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        for (int i = 0; i < arr3.length; i++) {
            if (arr3[i] < 6) arr3[i] *= 2;
        }


        // 4. Создаем двумерный массив и заполняем его значениями по диагонали
        int size = 9;
        int[][] board = new int[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == y) board[x][y] = 1;
                board[x][size - x - 1] = 1;
            }
            System.out.println(Arrays.toString(board[x]));
        }


        // 5. Поиск минимального и максимального значения в массиве
        int number = 15, max = 50;
        int[] arr4 = new int[number];
        // Наполнение массива рандомными значениями
        for (int i=0; i < number; i++) arr4[i] = (int) Math.round(Math.random() * max);
        // Для будущего сравнения в условии проверки min и max заданы минимально/максимально возможными
        int min = max;
        max = 0;
        // Поиск минимального и максимального значения в массиве
        for (int j : arr4) {
            if (min > j) min = j;
            if (max < j) max = j;
        }


        // 6. Поиск места в массиве, в котором сумма левой и правой части массива равны
        int[] arr5 = {2, 2, 2, 1, 2, 2, 10, 1};
        // В метод можно передать массив arr4, заполненный 15-ю рандомными значениями в диапазоне (0 - max *задается переменной в задании 5))
        boolean half = isEqualHalf(arr5);


        // 7. Смещение элементов в массиве на n позиций (в любую сторону)
        int changer = 11;
        int[] arr6 = {1, 2, 3, 4, 5, 6, 7};
        int[] result = shift(arr6, changer);
    }

    public static boolean isEqualHalf(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int left = 0, right = 0;
            for (int l = 0; l <= i; l++) {
                left += arr[l];
            }
            for (int r = i+1; r < arr.length; r++) {
                right += arr[r];
            }
            if (left == right) {
                return true;
            }
        }
        return false;
    }

    public static int[] shift(int[] arr, int n) {
        int len = arr.length;
        if (n > 0) {
            for (int i = 1; i <= n; i++) {
                int end = arr[len - 1];
                for (int z = len - 2; z >= 0; z--) {
                    arr[z + 1] = arr[z];
                }
                arr[0] = end;
            }
        } else if (n < 0) {
            for (int i = n; i <= -1; i++) {
                int start = arr[0];
                for (int z = 1; z < len; z++) {
                    arr[z - 1] = arr[z];
                }
                arr[len - 1] = start;
            }
        }
        return arr;
    }
}
