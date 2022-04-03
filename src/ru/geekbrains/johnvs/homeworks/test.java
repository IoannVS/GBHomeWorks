package ru.geekbrains.johnvs.homeworks;

public class test {

    public static void main(String[] args) {
        myMethod(5);
    }

    public static void myMethod(int x) {
        if (x == 0) {
            return;
        }
        x--;
        System.out.println(x);
        myMethod(x);
    }
}
