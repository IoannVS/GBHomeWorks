package ru.geekbrains.johnvs.homeworks;

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("\n\tПриветствую! Здесь можно выбрать урок для проверки.");
        System.out.println("\nДоступно 3 домашних задания, новые добавляются по мере обучения:");
        menu();

    }

    public static void menu() {

        boolean active = true;
        String startLine = "--------------------------------------------------";
        String endLine = "==================================================";
        while (active) {
            System.out.println("\n   -- \"1\" - четверть 1, ДЗ 1;");
            System.out.println("   -- \"2\" - четверть 1, ДЗ 2;");
            System.out.println("   -- \"3\" - четверть 1, ДЗ 3;");
            System.out.println("\n   -- \"0\" - выход из программы.");
            System.out.print("\nВведите номер урока: ");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> {
                        System.out.println(startLine);
                        HW_I_1.show();
                        System.out.println(endLine);
                        other();
                        active = false;
                    }
                    case 2 -> {
                        System.out.println(startLine);
                        HW_I_2.show();
                        System.out.println(endLine);
                        other();
                        active = false;
                    }
                    case 3 -> {
                        System.out.println(startLine);
                        HW_I_3.menu();
                        System.out.println(endLine);
                        other();
                        active = false;
                    }
                    case 0 -> {
                        System.out.println("\nЗавершаем процесс... Хорошего дня!");
                        active = false;
                    }
                    default -> System.out.println("Попробуйте еще раз :)");
                }
            } else {
                System.out.println("Попробуйте еще раз :)");
                sc.nextLine();
            }
        }
    }

    public static void other() {
        boolean flag = false;
        do {
            System.out.print("\nВыберем другое ДЗ? (1 - да, 0 - нет) - ");
            if (sc.hasNextInt()) {
                int oneMore = sc.nextInt();
                sc.nextLine();
                switch (oneMore) {
                    case 1 -> {
                        System.out.println("Возвращаемся в меню выбора ДЗ");
                        menu();
                    }
                    case 0 -> {
                        System.out.println("\nЗавершаем процесс... Хорошего дня!");
                        flag = false;
                    }
                    default -> {
                        System.out.println("Попробуйте еще раз :)");
                        flag = true;
                    }
                }
            } else {
                System.out.println("Попробуйте еще раз :)");
                sc.nextLine();
            }
        } while (flag);
    }
}

