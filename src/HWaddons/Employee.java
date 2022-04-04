package HWaddons;

public class Employee {

    public static int counter;

    private String fullName;
    private String position;
    private String email;
    private long phoneNumber;
    private int salary;
    private int age;
    private int id;

    {
        fullName = "John Erik Castle";
        position = "manager";
        email = "employee@gmail.com";
        phoneNumber = 79001234567L;
        salary = 100000;
        age = 27;
        id = ++counter;
    }

    public static int getCounter() {return counter;}

    public Employee() {}

    public Employee(String fullName) {
        this(fullName, "manager", "employee@gmail.com", 79001234567L, 100000, 27);
    }

    public Employee(String fullName, String position) {
        this(fullName, position, "employee@gmail.com", 79001234567L, 100000, 27);
    }

    public Employee(String fullName, String position, String email) {
        this(fullName, position, email, 79001234567L, 100000, 27);
    }

    public Employee(String fullName, String position, String email, long phoneNumber) {
        this(fullName, position, email, phoneNumber, 100000, 27);
    }

    public Employee(String fullName, String position, String email, long phoneNumber, int salary) {
        this(fullName, position, email, phoneNumber, salary, 27);
    }

    public Employee(String fullName, String position, String email, long phoneNumber, int salary, int age) {
        this.fullName = fullName;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        setAge(age);
    }

    public int getAge() {return age;}

    public void setAge(int age) {
        if (age <= 0) this.age = 1;
        else this.age = age;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void showInfo() {
        System.out.printf("""
            
            \t№ %d. ФИО: %s
            
             - должность: %s,
             - email: %s,
             - номер телефона: +%s
             - зарплата: %s,
             - возраст: %s
             
             """, id, fullName, position, email, phoneNumber, salary, age);
    }
}