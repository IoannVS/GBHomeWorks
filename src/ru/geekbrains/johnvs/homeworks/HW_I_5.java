package ru.geekbrains.johnvs.homeworks;

import HWaddons.Employee;

public class HW_I_5 {

    public static void main(String[] args) {
        show();
    }

    public static void show() {

        System.out.println("");

        Employee[] employees = new Employee[5];
        employees[0] = new Employee("John Castle","Team Lead", "john_teamlead@gmail.com",
                79324567890L, 230000, 34);
        employees[1] = new Employee("Erika Jane Smith","Fullstack Developer", "ErikaJ@gmail.com",
                79765563412L, 170000, 42);
        employees[2] = new Employee("Stan Lee","Creative Designer", "DrawMan@gmail.com",
                79567876451L, 110000, 37);
        employees[3] = new Employee("Abraham Wild","Mobile Developer", "Tiger1000@gmail.com",
                79567345783L, 150000, 41);
        employees[4] = new Employee("Julia Jessika West","Frontend Developer",
                "WestTexas1984@gmail.com", 79567876451L, 110000, 44);

        System.out.println("\tВыводим данные сотрудников старше 40 лет");
        for (Employee every: employees) {
            if (every.age > 40) {
                every.showInfo();
            }
        }
    }

}
