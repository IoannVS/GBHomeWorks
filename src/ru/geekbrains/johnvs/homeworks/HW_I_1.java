package ru.geekbrains.johnvs.homeworks;

public class HW_I_1 {

    public static void main(String[] args) {
        show();
    }

    public static void show() {
        //1.Метод main вызывается автоматически при выполнении программы :)

        System.out.println("\n  -- Задание №1. Написать метод main");
        System.out.println("\nГотово :) Иначе Вы бы не увидели это сообщение");

        //2. Объявление и инициализация всех изученных типов переменных:
        System.out.println("\n  -- Задание №2. Объявление и инициализация всех изученных типов переменных:");
        System.out.println("\n\t\tОбъявлена и инциализирована:");
        byte byteValue = 43;
        System.out.println("   -- переменная byteValue типа byte со значением: " + byteValue);
        short shortValue = -31675;
        System.out.println("   -- переменная shortValue типа short со значением: " + shortValue);
        int intValue = 2016463231;
        System.out.println("   -- переменная intValue типа int со значением: " + intValue);
        long longValue = 8213375026844678897L;
        System.out.println("   -- переменная longValue типа long со значением: " + longValue);
        float floatValue = 6.8789f;
        System.out.println("   -- переменная floatValue типа float со значением: " + floatValue);
        double doubleValue = 78.98489034;
        System.out.println("   -- переменная doubleValue типа double со значением: " + doubleValue);
        char charValue = '\uf0ff';
        System.out.println("   -- переменная charValue типа byte со значением: " + charValue);
        boolean booleanValue = false;
        System.out.println("   -- переменная booleanValue типа boolean со значением: " + booleanValue);

        /*3. Метод с вычислениями по формуле
        Для методов не прописано описание на русском только по причине их предельной простоты :)
        */
        System.out.println("\n  -- Задание №3. Написать метод для вычисления значения выражения по формуле");
        System.out.println("\nФормула: a * (b + (c / d))");
        float a = 4.8f, b = 5.6f, c = 7.9f, d = 1.2f;
        System.out.println("Значения переменных: " + a + ", " + b + ", " + c + ", " + d + ".");
        float res3 = doCount(a, b, c, d);
        System.out.println("Результат выражения: " + res3);

        //4. Метод определения попадания в числовой промежуток
        System.out.println("\n  -- Задание №4. Написать метод определения попадания суммы чисел в числовой промежуток 10 - 20");
        int i = 7, j = 6;
        System.out.println("\nЗаданные значения: " + i + ", " + j);
        boolean res4 = inRange(i, j);
        System.out.println("Результат проверки: " + res4);

        //5. Метод определения знака числа (+/-) с выводом в консоль
        System.out.println("\n  -- Задание №5. Написать метод определения знака числа (+/-)");
        int n = (int) (Math.round(Math.random() * 100 - 50));
        isPositive(n);

        //6. Метод определения знака числа (+/-) с возвратом булева значения
        System.out.println("\n  -- Задание №6. Написать метод определения знака числа (+/-) с возвратом булева значения");
        System.out.println("\t\ttrue - отрицательное, false - положительное");
        int z = (int) (Math.round(Math.random() * 100 - 50));
        boolean res6 = isNegative(z);
        System.out.println("\nРезультат проверки числа " + z + ": " + res6);

        //7. Метод приветствия по переданному имени
        System.out.println("\n  -- Задание №7. Написать метод приветствия по переданному имени");
        String[] names = {"Elsa", "John", "Alex"};
        String name = names[(int) (Math.round(Math.random() * 2))];
        System.out.println("\nЗаданное имя: " + name);
        greetUser(name);

        //8. Метод проверки года на високосность
        System.out.println("\n  -- Задание №8. Написать метод проверки года на високосность");
        int year = (int) (Math.round(Math.random() * 500 + 1000));
        System.out.println("\nЗадан год: " + year);
        isLeap(year);
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
            System.out.println("\nЧисло " + value + " положительное");
        } else {
            System.out.println("\nЧисло " + value + " отрицательное");
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
