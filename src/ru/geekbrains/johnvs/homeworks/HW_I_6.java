package ru.geekbrains.johnvs.homeworks;

public class HW_I_6 {

    public static void main(String[] args) {

        show();

    }

    public static void show() {

        Animal cat01 = new Cat("Шустрик");
        Animal cat02 = new Cat("Барсик", "Черный");
        Animal cat03 = new Cat();
        Animal cat04 = new Cat();

        Animal dog01 = new Dog("Верный");
        Animal dog02 = new Dog("Бим", "Белый");
        Animal dog03 = new Dog();

        cat01.run(120);
        cat02.run(350);
        cat03.swim(12);
        cat04.run(0);

        System.out.println();

        dog01.swim(120);
        dog02.run(75);
        dog03.run(-50);

        System.out.printf("""
            
            Животных создано: %d
            
            \t-- Котов: %d,
            \t-- Собак: %d.
            
            """, Animal.getCounter(), Cat.getCounter(), Dog.getCounter());
    }

}

abstract class Animal {

    private static int counter;

    {
        counter++;
    }

    public static int getCounter() {
        return counter;
    }

    Animal() {}

    public abstract void run(int distance);

    public abstract void swim(int distance);

    public abstract void getInfo();

}

class Cat extends Animal {


    private static int counter;
    private String name;
    private String color;
    private final int id;

    {
        name = "Матроскин";
        color = "Белый";
        id = ++counter;
    }

    Cat() {}

    Cat(String name) {
        this.name = name;
        color = "Белый";
    }

    Cat(String name, String color) {
        this.name = name;
        this.color = color;
    }
    public static int getCounter() {
        return counter;
    }

    @Override
    public void run(int distance) {
        if (distance > 200) distance = 200;
        else if (distance <= 0) distance = 1;
        System.out.printf("Кот %s пробежал: %d м.\n", name, distance);
    }

    @Override
    public void swim(int distance) {
        System.out.printf("Кот %s не умеет плавать :(\n", name);
    }

    @Override
    public void getInfo() {
        System.out.printf("""
                
                Кот № %d: %s
                \t- Цвет: %s
                
                """, id, name, color);
    }

}

class Dog extends Animal {

    private static int counter;
    private String name;
    private String color;
    private final int id;

    {
        name = "Шарик";
        color = "Серый";
        id = ++counter;
    }

    Dog() {}

    Dog(String name) {
        this.name = name;
        color = "Серый";
    }

    Dog(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public static int getCounter() {
        return counter;
    }

    @Override
    public void run(int distance) {
        if (distance > 500) distance = 500;
        else if (distance <= 0) distance = 1;
        System.out.printf("Собака %s пробежала: %d м.\n", name, distance);
    }

    @Override
    public void swim(int distance) {
        if (distance > 10) distance = 10;
        else if (distance <= 0) distance = 1;
        System.out.printf("Собака %s проплыла: %d м.\n", name, distance);
    }

    @Override
    public void getInfo() {
        System.out.printf("""
                
                Собака № %d: %s
                \t- Цвет: %s
                
                """, id, name, color);
    }

}