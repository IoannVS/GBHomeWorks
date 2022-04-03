package ru.geekbrains.johnvs.homeworks;

import java.util.Scanner;

public class test {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

    }

    public static void game() {

        int counter = 0;
        do {
            if (sc.hasNextInt()) {

            } else {
                System.out.print("Попробуйте еще раз: ");
                sc.nextLine();
            }
        } while (counter < 3);

    }

}
