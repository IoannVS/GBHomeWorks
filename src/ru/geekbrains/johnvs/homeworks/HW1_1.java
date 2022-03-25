package ru.geekbrains.johnvs.homeworks;

public class HW1_1 {

    public static void main(String[] args) {

        //1.Метод main вызывается автоматически при выполнении программы :)

        //2. Объявление и инициализация всех изученных типов переменных:
        byte byteValue = 43;
        short shortValue = -31675;
        int intValue = 2016463231;
        long longValue = 8213375026844678897L;
        float floatValue = 6.8789f;
        double doubleValue = 78.98489034;
        char charValue = '\uffff';
        boolean booleanValue = false;

        /*3. Метод с вычислениями по формуле
        Для методов не прописано описание на русском только по причине их предельной простоты :)
        */
        float res3 = doCount(4.8f, 5.6f, 7.9f, 1.2f);

        //4. Метод определения попадания в числовой промежуток
        boolean res4 = inRange(7, 6);

        //5. Метод определения знака числа (+/-) с выводом в консоль
        isPositive(67);

        //6. Метод определения знака числа (+/-) с возвратом булева значения
        boolean res6 = isNegative(-45);

        //7. Метод приветствия по переданному имени
        greetUser("Elsa");

        //8. Метод проверки года на високосность
        isLeap(1589);
    }

    public static float doCount(float a, float b, float c, float d) {
        return (a * (b + (c / d)));
    }

    public static boolean inRange(int a, int b) {
        int sum = a + b;
        return (sum >= 10 && sum <= 20);
    }

    public static void isPositive(int value) {
        if (value >= 0) {
            System.out.println("Число " + value + " положительное");
        } else {
            System.out.println("Число " + value + " отрицательное");
        }
    }

    public static boolean isNegative(int value) {
        return !(value >= 0);
    }

    public static void greetUser(String name) {
        System.out.println("Привет, " + name + "!");
    }

    public static void isLeap(int year) {
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) System.out.println(year + " год - високосный");
        else System.out.println(year + " год - не високосный");
    }
}

