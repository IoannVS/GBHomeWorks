package ru.geekbrains.johnvs.homeworks;

public class HW_I_8 {

    public static void main(String[] args) {
        show();
    }

    public static void show () {

        System.out.println("""
        \tНачинаем прохождение полосы препятствий!
        
        В состязании участвуют 6 существ:
        \t- 2 человека,
        \t- 2 робота,
        \t- 2 кота.
        
        Да начнется игра!
        """);

        // Массив с участниками забега
        Object[] participants = new Object[6];

        participants[0] = new Robot();
        participants[1] = new Robot("C-3PO", 5, 300);

        participants[2] = new FatCat();
        participants[3] = new FatCat("Шустрик", 3, 25);

        participants[4] = new Human();
        participants[5] = new Human("Пётр Петрович (старичок)", 2, 17);

        // Массив с препятствиями
        Object[] obstacles = new Object[5];

        obstacles[0] = new Treadmill();
        obstacles[1] = new Wall();
        obstacles[2] = new Treadmill();
        ((Treadmill) obstacles[2]).setDistance(25);
        obstacles[3] = new Wall();
        ((Wall) obstacles[3]).setWallHeight(3);
        obstacles[4] = new Treadmill();
        ((Treadmill) obstacles[4]).setDistance(275);

        // Сам забег сделан через простой for для упрощения процесса
        int counter = 0;
        boolean active = true;
        for (Object every: participants) {
            for (Object obstacle: obstacles) {
                active = true;
                if (every instanceof FatCat) {
                    if (obstacle instanceof Treadmill) {
                        active = ((FatCat) every).run((Treadmill) obstacle);
                    }
                    if (obstacle instanceof Wall) {
                        active = ((FatCat) every).jump((Wall) obstacle);
                    }
                } else if (every instanceof Robot) {
                    if (obstacle instanceof Treadmill) {
                        active = ((Robot) every).run((Treadmill) obstacle);
                    }
                    if (obstacle instanceof Wall) {
                        active = ((Robot) every).jump((Wall) obstacle);
                    }
                } else {
                    if (obstacle instanceof Treadmill) {
                        active = ((Human) every).run((Treadmill) obstacle);
                    }
                    if (obstacle instanceof Wall) {
                        active = ((Human) every).jump((Wall) obstacle);
                    }
                }
                // Прерываем процесс, если участник не смог пройти одно из препятствий
                if (!active) break;
            }
            if (active) {
                counter++;
                System.out.println("---\nУчастник прошел испытание!\n");
            } else System.out.println("---\nУчастник не смог пройти испытание :(\n");
        }
        System.out.printf(counter == 0 ? "Никто из участников не смог пройти полосу препятствий\n" :
                "Количество участников, прошедших полосу препятствий: %d\n", counter);
    }
}

class Robot{

    private String name;
    private int maxHeight;
    private int maxDistance;

    {
        name = "R2-D2";
        maxHeight = 4;
        maxDistance = 200;
    }

    public Robot () {}

    public Robot(String name, int maxHeight, int maxDistance) {
        this.name = name;
        this.maxHeight = maxHeight;
        this.maxDistance = maxDistance;
    }

    public boolean run(Treadmill distance) {
        boolean success = distance.run(maxDistance);
        String text = success ? "Робот %s пробежал %d км.\n" :
                "Робот %s не смог пробежать %d км.\n";
        System.out.printf(text, name, distance.getDistance());
        return success;
    }

    public boolean jump(Wall height) {
        boolean success = height.jump(maxHeight);
        String text = success ? "Робот %s перепрыгнул препятствие высотой %d м.\n" :
                "Робот %s не смог перепрыгнуть препятствие высотой %d м.\n";
        System.out.printf(text, name, height.getWallHeight());
        return success;
    }

    public String getName() {return name;}

    public double getMaxHeight() {return maxHeight;}

    public int getMaxDistance() {return maxDistance;}
}

class Human {

    private String name;
    private int maxHeight;
    private int maxDistance;

    {
        name = "Вася";
        maxHeight = 3;
        maxDistance = 64;
    }

    public Human () {}

    public Human(String name, int maxHeight, int maxDistance) {
        this.name = name;
        this.maxHeight = maxHeight;
        this.maxDistance = maxDistance;
    }

    public boolean run(Treadmill distance) {
        boolean success = distance.run(maxDistance);
        String text = success ? "%s пробежал %d км.\n" :
                "%s не смог пробежать %d км.\n";
        System.out.printf(text, name, distance.getDistance());
        return success;
    }

    public boolean jump(Wall height) {
        boolean success = height.jump(maxHeight);
        String text = success ? "%s перепрыгнул препятствие высотой %d м.\n" :
                "%s не смог перепрыгнуть препятствие высотой %d м.\n";
        System.out.printf(text, name, height.getWallHeight());
        return success;
    }

    public String getName() {return name;}

    public double getMaxHeight() {return maxHeight;}

    public int getMaxDistance() {return maxDistance;}
}

class FatCat {

    private String name;
    private int maxHeight;
    private int maxDistance;

    {
        name = "Пушистик";
        maxHeight = 2;
        maxDistance = 20;
    }

    public FatCat () {}

    public FatCat(String name, int maxHeight, int maxDistance) {
        this.name = name;
        this.maxHeight = maxHeight;
        this.maxDistance = maxDistance;
    }

    public boolean run(Treadmill distance) {
        boolean success = distance.run(maxDistance);
        String text = success ? "Кот %s пробежал %d км.\n" :
                "Кот %s не смог пробежать %d км.\n";
        System.out.printf(text, name, distance.getDistance());
        return success;
    }

    public boolean jump(Wall height) {
        boolean success = height.jump(maxHeight);
        String text = success ? "Кот %s перепрыгнул препятствие высотой %d м.\n" :
                "Кот %s не смог перепрыгнуть препятствие высотой %d м.\n";
        System.out.printf(text, name, height.getWallHeight());
        return success;
    }

    public String getName() {return name;}

    public double getMaxHeight() {return maxHeight;}

    public int getMaxDistance() {return maxDistance;}
}

class Wall {

    private int wallHeight;

    {
        wallHeight = 1;
    }

    public boolean jump (int maxHeight) {
        return maxHeight >= wallHeight;
    }

    public int getWallHeight() {return wallHeight;}

    public void setWallHeight(int wallHeight) {
        if (wallHeight > 0) {
            this.wallHeight = wallHeight;
        } else this.wallHeight = 1;

    }
}

class Treadmill {

    private int distance;

    {
        distance = 15;
    }

    public Treadmill () {}

    public boolean run (int maxDistance) {
        return maxDistance >= distance;
    }

    public int getDistance() {return distance;}

    public void setDistance(int distance) {
        if (distance > 0) {
            this.distance = distance;
        } else this.distance = 15;
    }
}
