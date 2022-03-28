package ru.geekbrains.johnvs.homeworks;

import java.util.Scanner;

public class HW_I_3 {

    public static void main(String[] args) {
        menu();
    }

    static Scanner sc = new Scanner(System.in);

    public static void taskOne() {

        System.out.println("\n\t\t\tИграем в \"Угадай число\"");
        System.out.println("\nЗадача - угадать число в диапазоне от 0 до 9");
        System.out.println("У вас есть 3 попытки, чтобы отгадать. Удачи :)");

        int number = (int) Math.round(Math.random() * 9), userTry, counter = 0;

        do {
            System.out.print("\nВведите число в диапазоне 0 - 9: ");

            if (sc.hasNextInt()) {
                userTry = sc.nextInt();
                sc.nextLine();
                counter++;

                if (userTry < 0 || userTry > 9) {
                    System.out.println("Число должно быть в диапазоне 0 - 9");
                    counter--;
                } else if (counter == 3 && userTry != number) {
                    System.out.println("\nПопытки закончились :( Загаданное число - " + number);
                } else if (userTry < number) {
                    System.out.println("Загаданное число больше " + userTry);
                    System.out.println("У вас осталось попыток: " + (3 - counter));
                } else if (userTry > number) {
                    System.out.println("Загаданное число меньше " + userTry);
                    System.out.println("У вас осталось попыток: " + (3 - counter));
                } else {
                    System.out.println("\nВы выиграли! Загаданное число: " + number);
                    System.out.println("Понадобилось попыток: " + counter);
                    break;
                }
            } else {
                System.out.println("Попробуйте еще раз :)");
                sc.nextLine();
            }
        } while (counter < 3);
        // Предлагаем сыграть в эту игру еще раз
        boolean active = false;
        do {
            System.out.print("\nСыграем еще раз? (1 - да, 0 - нет) - ");
            if (sc.hasNextInt()) {
                int oneMore = sc.nextInt();
                sc.nextLine();
                switch (oneMore) {
                    case 1 -> taskOne();
                    case 0 -> System.out.println("Возвращаемся в меню выбора игры");
                    default -> {
                        System.out.println("Попробуйте еще раз :)");
                        active = true;
                    }
                }
            } else {
                System.out.println("Попробуйте еще раз :)");
                sc.nextLine();
            }
        } while (active);
    }

    public static void taskTwo() {

        System.out.println("\n\t\t\tИграем в \"Угадай слово\"");
        System.out.println("\n\tЗагадано слово, которое вам предлагается отгадать.");
        System.out.println("Вводите догадку в поле ввода. В строке ###############");
        System.out.println("будут показаны буквы, которое \"попали\" на свои места.");
        System.out.println("\nИгра продолжается до полной отгадки слова. Удачи :)");
        System.out.println("\nВведите \"0\" в любой момент, чтобы завершить игру--");

        String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado",
                "broccoli", "carrot", "cherry", "garlic", "grape", "melon",
                "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea",
                "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};
        String askWord = words[(int) (Math.random() * 25)];
        String showRight = "###############";

        int counterLetter = 0, counterTry = 0; // Счетчики отгаданных букв и попыток
        while (true) {
            System.out.print("\nВведите слово: ");
            String guess = sc.nextLine().toLowerCase();
            // Пишем условие выхода из игры
            if (guess.contains("0") && guess.length() == 1) {
                System.out.println("\nЗавершаем игру \"Угадай слово\"");
                System.out.println("Загаданное слово \"" + askWord + "\".");
                break;
            }
            counterTry++;
            // Вписываем совпавшие буквы на нужное место
            for (int i = 0; i < guess.length(); i++) {
                if (i >= askWord.length()) break;
                /* В условии ниже "И" нужно для правильного счета кол-ва отгаданных букв.
                Если не будет сравнения с уже отгаданными символами, то при повторном вводе уже отгаданной буквы,
                счетчик будет увеличиваться, что не совсем корректно. Доп. условие это исключает.
                 */
                if (askWord.charAt(i) == guess.charAt(i) && showRight.charAt(i) != guess.charAt(i)) {
                    char[] chars = showRight.toCharArray();
                    chars[i] = guess.charAt(i);
                    showRight = String.valueOf(chars);
                    counterLetter++;
                }
            }
            // После отгадки слова выводим его на экран и завершаем игру
            if (showRight.contains(askWord)) {
                System.out.println("\nПоздравляем! Вы отгадали слово \"" + askWord + "\".");
                System.out.println("Понадобилось попыток: " + counterTry);
                break;
            }
            // В случае, если слово еще не отгадано, вывовдим на экран промежуточный результат
            System.out.println(showRight);
            if (counterLetter == 0) System.out.println("Вы не отгадали ни одной буквы :(");
            else {
                System.out.println("Отгадано букв: " + counterLetter);
            }
        }
        // Предлагаем сыграть в эту игру еще раз
        boolean active = false;
        do {
            System.out.print("\nСыграем еще раз? (1 - да, 0 - нет) - ");
            if (sc.hasNextInt()) {
                int oneMore = sc.nextInt();
                sc.nextLine();
                switch (oneMore) {
                    case 1 -> taskTwo();
                    case 0 -> System.out.println("Возвращаемся в меню выбора игры");
                    default -> {
                        System.out.println("Попробуйте еще раз :)");
                        active = true;
                    }
                }
            } else {
                System.out.println("Попробуйте еще раз :)");
                sc.nextLine();
            }
        } while (active);
    }

    public static void menu() {

        System.out.println("\nК проверке ДЗ №3 готовы оба задания.");
        String startLine = "--------------------------------------------------";
        String endLine = "==================================================";
        boolean active = true;
        do {
            System.out.println("\n  -- \"1\" - игра \"Угадай число\";");
            System.out.println("  -- \"2\" - игра \"Угадай слово\";");
            System.out.println("\n  -- \"0\" - выход из программы.");
            System.out.print("\nДля выбора игры введите ее номер: ");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 0 -> {
                        System.out.println("\nЗавершаем процессы...");
                        active = false;
                    }
                    case 1 -> {
                        System.out.println(startLine);
                        taskOne();
                        System.out.println(endLine);
                    }
                    case 2 -> {
                        System.out.println(startLine);
                        taskTwo();
                        System.out.println(endLine);
                    }
                    default -> System.out.println("Не совсем понял, что Вы имеете в виду. Попробуйте еще раз :)");
                }
            } else {
                System.out.println("Попробуйте выбрать еще раз :)");
                sc.nextLine();
            }
        } while (active);
    }
}
