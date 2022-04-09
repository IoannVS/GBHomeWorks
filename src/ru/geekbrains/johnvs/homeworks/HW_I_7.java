package ru.geekbrains.johnvs.homeworks;

public class HW_I_7 {

    public static void main(String[] args) {
        show();
    }

    public static void show() {

        /*
        В тарелке по умолчанию 20 единиц еды. Аппетит котенка - 7 единиц

        В массиве 10 котят. У каждого есть свой индикатор "сыт"/"голоден"

        Кормежка №1
        Два котенка поели, остальным не хватило. По 4 пункту дз мы не кормим котенка, если ему не хватает еды
        до полного насыщения. Это упрощает работу с пунктом 2, где говорится, что в тарелке не может быть отрицательных
        значений еды. Условие простое: если аппетит котенка больше кол-ва еды в тарелке, то мы не кормим его. Выполняем
        сразу 2 и 4 пункт ДЗ.

        Сытых котят 2, голодных - 8

        Кормежка №2
        Добавили 100 единиц еды в тарелку и отправили кушать только голодных котят

        Все сыты :)
         */

        Plate food = new Plate();

        // Количество котят
        int kNum = 10;

        Kitty[] kitties = new Kitty[kNum];
        for (int c = 0; c < kNum; c++) {
            kitties[c] = new Kitty();
        }

        // Кормежка 1
        System.out.println("Кормежка № 1:\n");

        food.printInfo();
        System.out.println();

        for (int i = 0; i < kNum; i++) {
            kitties[i].eat(food);
            kitties[i].printInfo();
        }
        System.out.println();
        food.printInfo();
        System.out.println();

        // Кормежка 2
        System.out.println("Кормежка № 2:\n");

        food.addFood(100);
        food.printInfo();
        System.out.println();

        for (int i = 0; i < kNum; i++) {
            if (!kitties[i].getIsWellFed()) {
                kitties[i].eat(food);
            }
            kitties[i].printInfo();
        }
        System.out.println();
        food.printInfo();
    }
}

class Plate {

    private int food;

    {
        food = 20;
    }

    public Plate() {}

    public Plate(int food) {
        this.food = food;
    }

    /**
     * Это стандартный сеттер для food. Вдруг тарелка будет другого размера :)
     * @param food кол-во еды, которое собираются из тарелки съесть
     */
    public void setFood(int food) {
        if (food > 0) this.food = food;
    }

    /**
     * "Доливаем" молоко для котят. Добавляем к текущему значению
     * @param food кол-во еды, которое собираются из тарелки съесть
     */
    public void addFood(int food) {
        if (food > 0) this.food += food;
    }

    /**
     * Процесс еды. Метод возвращает булево значение, означающее смог ли наесться тот, кто подошел к тарелке
     * @param appetite кол-во еды, которое собираются из тарелки съесть
     * @return true если наелся тот, кто собирался есть, иначе - false
     */
    public boolean decreaseFood(int appetite) {
        if (food >= appetite) {
            food -= appetite;
            return true;
        }
        return false;
    }

    public void printInfo () {
        System.out.printf("Еды в тарелке: %d\n", food);
    }

}

// Класс с котами создавали на прошлом уроке, решил кормить голодных котят (имена классов совпадают) :)
class Kitty {

    private static int counter;

    private final int id;
    private String name;
    private int appetite;
    private boolean isWellFed;

    {
        id = ++counter;
        name = "Барсик";
        appetite = 7;
        isWellFed = false;
    }

    public Kitty() {}

    public Kitty(String name) {
        this(name, 7);
    }

    public Kitty(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
    }

    /**
     * Метод сохраняет булево значение в переменную сытости котенка
     * @param food источник еды
     */
    public void eat(Plate food) {
        isWellFed = food.decreaseFood(appetite);
    }

    public void printInfo() {
        System.out.printf("""
                № %d. Кот %s — %s
                """, id, name, isWellFed ? "поел" : "голоден");
    }
    public boolean getIsWellFed() {return isWellFed;}
}