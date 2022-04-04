package ru.geekbrains.johnvs.homeworks;

import HWaddons.Employee;

public class HW_I_5 {

    public static void main(String[] args) {
        show();
    }

    public static void show() {

        System.out.println();

        Employee[] employees = new Employee[5];
        employees[0] = new Employee("John Castle");
        employees[0].setPhoneNumber(79324567890L);
        employees[0].setEmail("john_teamlead@gmail.com");
        employees[0].setPosition("Team Lead");
        employees[1] = new Employee("Erika Jane Smith","Fullstack Developer", "ErikaJ@gmail.com");
        employees[1].setAge(42);
        employees[2] = new Employee("Stan Lee","Creative Designer", "DrawMan@gmail.com",
                79567876451L, 110000);
        employees[3] = new Employee("Abraham Wild","Mobile Developer");
        employees[3].setAge(45);
        employees[3].setEmail("Tiger1984@gmail.com");
        employees[4] = new Employee("Julia Jessika West","Frontend Developer",
                "WestTexas1984@gmail.com", 79567876451L, 110000, 44);

        System.out.printf("\tВ штате компании: %d сотрудников\n\n", Employee.getCounter());

        System.out.println("\tВыводим данные сотрудников старше 40 лет");
        for (Employee every: employees) {
            if (every.getAge() > 40) {
                every.showInfo();
            }
        }
    }
}
