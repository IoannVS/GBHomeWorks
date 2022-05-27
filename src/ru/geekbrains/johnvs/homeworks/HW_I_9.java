package ru.geekbrains.johnvs.homeworks;

import static java.lang.Integer.parseInt;

public class HW_I_9 {

    public static void main(String[] args) {
        show();
    }

    public static void show() {

        //Массив соответствует всем параметрам
        String[][] right_array = {{"1", "2", "3", "4"}, {"5", "6", "7", "8"}, {"9", "10", "11", "12"}, {"13", "14", "15", "16"}};
        try {
            System.out.println(checkArray(right_array));
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }

        //Неверный размер массива
        String[][] wrong_size_array = {{"1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}};
        try {
            System.out.println(checkArray(wrong_size_array));
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }

        //Неверное значение в массиве
        String[][] wrong_value_array = {{"1", "2", "3", "4"}, {"5", "6", "7", "8"}, {"9", "Десять", "11", "12"}, {"13", "14", "15", "16"}};
        try {
            System.out.println(checkArray(wrong_value_array));
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
    }

    public static int checkArray(String[][] array) throws MyArraySizeException, MyArrayDataException {

        String textWrongSize = "Массив не соответствует заданному размеру";
        if (array.length != 4) throw new MyArraySizeException(textWrongSize);
        for (String[] strings : array) {
            if (strings.length != 4) throw new MyArraySizeException(textWrongSize);
        }
        int sum = 0;
        for (int line = 0; line < array.length; line++) {
            for (int element = 0; element < array[line].length; element++) {
                try {
                    sum += Integer.parseInt(array[line][element]);
                } catch (NumberFormatException e) {
                    String text = "В строке № " + (line + 1) + " элемент № " + (element + 1) +
                            " невозможно преобразовать в число: " + array[line][element];
                    throw new MyArrayDataException(text);
                }
            }
        }
        return sum;
    }
}

class MyArraySizeException extends Throwable {
    public MyArraySizeException(String message) {
        super(message);
    }
}

class MyArrayDataException extends Throwable {
    public MyArrayDataException(String message) {
        super(message);
    }
}

