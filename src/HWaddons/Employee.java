package HWaddons;

public class Employee {

    public String fullName;
    public String position;
    public String email;
    public long phoneNumber;
    public int salary;
    public int age;

    public Employee() {
        fullName = "John Erik Castle";
        position = "manager";
        email = "employee@gmail.com";
        phoneNumber = 79001234567L;
        salary = 100000;
        age = 27;
    }

    public Employee(String fullName) {
        this.fullName = fullName;
        position = "manager";
        email = "employee@gmail.com";
        phoneNumber = 79001234567L;
        salary = 100000;
        age = 27;
    }

    public Employee(String fullName, String position) {
        this.fullName = fullName;
        this.position = position;
        email = "employee@gmail.com";
        phoneNumber = 79001234567L;
        salary = 100000;
        age = 27;
    }

    public Employee(String fullName, String position, String email) {
        this.fullName = fullName;
        this.position = position;
        this.email = email;
        phoneNumber = 79001234567L;
        salary = 100000;
        age = 27;
    }

    public Employee(String fullName, String position, String email, long phoneNumber) {
        this.fullName = fullName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        salary = 100000;
        age = 27;
    }

    public Employee(String fullName, String position, String email, long phoneNumber, int salary) {
        this.fullName = fullName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        age = 27;
    }

    public Employee(String fullName, String position, String email, long phoneNumber, int salary, int age) {
        this.fullName = fullName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.age = age;
    }

    public void showInfo() {
        System.out.printf("""
            
            \t\tФИО: %s
            
             - должность: %s,
             - email: %s,
             - номер телефона: %s
             - зарплата: %s,
             - возраст: %s
             
             """, fullName, position, email, phoneNumber, salary, age);
    }

}