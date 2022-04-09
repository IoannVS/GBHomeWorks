package ru.geekbrains.johnvs.homeworks;

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static int counterHomeWorks = 7;

    public static void main(String[] args) {

        System.out.printf("""
                
                \tПриветствую! Здесь можно выбрать урок для проверки.
                
                Доступно %d домашних задания, новые добавляются по мере обучения:
                """, counterHomeWorks);
        menu();

    }

    public static void menu() {

        boolean active = true;
        String startLine = "--------------------------------------------------";
        String endLine = "==================================================";
        System.out.println();
        for (int i = 1; i <= counterHomeWorks; i++) {
            System.out.printf("   -- \"%d\" - четверть 1, ДЗ %d;\n", i, i);
        }
        System.out.println("\n   -- \"0\" - выход из программы.");
        System.out.print("\nВведите номер урока: ");
        while (active) {
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
                    case 4 -> {
                        System.out.println(startLine);
                        HW_I_4.gameStart(true);
                        System.out.println(endLine);
                        other();
                        active = false;
                    }
                    case 5 -> {
                        System.out.println(startLine);
                        HW_I_5.show();
                        System.out.println(endLine);
                        other();
                        active = false;
                    }
                    case 6 -> {
                        System.out.println(startLine);
                        HW_I_6.show();
                        System.out.println(endLine);
                        other();
                        active = false;
                    }
                    case 7 -> {
                        System.out.println(startLine);
                        HW_I_7.show();
                        System.out.println(endLine);
                        other();
                        active = false;
                    }
                    case 0 -> {
                        System.out.println("\nЗавершаем процесс... Хорошего дня!");
                        active = false;
                    }
                    default -> System.out.print("Попробуйте еще раз: ");
                }
            } else {
                System.out.print("Попробуйте еще раз: ");
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
                        System.out.print("Попробуйте еще раз: ");
                        flag = true;
                    }
                }
            } else {
                System.out.print("Попробуйте еще раз: ");
                sc.nextLine();
            }
        } while (flag);
    }
}

