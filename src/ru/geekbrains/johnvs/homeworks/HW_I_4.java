package ru.geekbrains.johnvs.homeworks;

import java.util.Scanner;

public class HW_I_4 {

    /*
    Общие примечания к игре:
    -- В случае установки большого размера поля (от 10x10), рекомендуется устанавливать значение
    количества точек для выигрыша не менее 5, т. к. в "пустом" поле, где до краев довольно далеко компьютер чисто
    технически не успеет заблокировать все выигрышные ходы, т. к. он, как и пользователь ограничен всего 1 ходом подряд,
    при этом заблокировав одно направление, он может не успеть заблокировать несколько других.
    -- Возможный диапазон установки значений переменных:
        - размер поля: минимум 3x3, максимум ограничен 15x15, но алгоритм позволяет дойти до 100 (дальше
        надо править вывод в консоль),
        - кол-во точек для выигрыша: минимум 3, максимум - не больше ширины/длины игрового поля,
        - в случае выбора игроком "O", компьютер будет ходить первым, т. к. "X" всегда ходит первым,
        - уровни сложности игры:
            1. Рандом - генерируем рандомные значения, проверяем клетку на занятость. Если пустая - ставим символ,
            2. Блок - в приоритете блокировка потенциально выигрышных ходов игрока. Подробнее в описаниие blockAloneDot
            и blockWinLine,
            3. Выигрыш - проверка возможности выигрыша компьютера на глубине 1 и 2. Если выигрыш возможен за 1 ход, то
            действия игрока не проверяются, если за 2 - сначала проверяем возможность выигрыша игрока. Подробнее в
            описании levelMediumPlus.
            4. MiniMax для поля 3x3. Отдельный режим игры, где невозможно выиграть :)
    -- В любом поле ввода можно ввести -1, чтобы завершить игру
     */

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        gameStart(true);
    }

    static final char emptyDot = '•';
    //Значения не заданы специально. Подробнее см. в описании метода setGameParameters
    static char userDot, aiDot;

    static int sizeX, sizeY, turnCounter = 1, difLevel;
    static char[][] board;

    //Длина выигрышной линии. Длина проверяемой линии. Подробнее в функции проверки выигрыша makeWinStar
    static int dotToWin, len;
    //Переменные аналогичные предыдущим, но с уменьшенными значениями.
    //Необходимы для работы метода блокировки потенциально выигрышных ходов
    static int lessDots, lessLen;
    //Комбинации выигрышных линий. Подробнее см. в описании функции getCombsWin
    static String[] combsWin = new String[4];

    //При первом запуске игры выводим заголовок
    static boolean first = true;

    public static void gameStart(boolean setP) {
        turnCounter = 1;
        if (first) {
            System.out.println("""
        \n\t\t\t\tПриветствую в игре "Крестики-нолики"
        
        Внимание! Координатная сетка инвертирована: X - вертикаль, Y - горизонталь");
        Сначала вводится координата X, затем через пробел координата Y. Удачи :)
        
        \t-- Введите -1 в любом поле ввода, чтобы завершить игру --""");
            first = false;
        }
        if (setP) {
            if (setGameParameters()) {
                System.out.println("\nЗавершаем процессы...");
                return;
            }
        }
        makeBoard();
        System.out.printf("\nПоле для игры размером %dx%d\nX - вертикаль, Y - горизонталь!\n\n", sizeX, sizeY);
        showBoard();
        System.out.println("""
        Игра начинается! "X" ходит первым!
                
        Введите -1 при вводе координат, если желаете завершить игру
        """);
        boolean gameOn;
        while (true) {
            //Если user выбрал "O", то первым ходит компьютер
            if (userDot == 'O') {
                gameOn = aiTurn();
                if (!gameOn) break;
            }
            gameOn = userTurn();
            if (!gameOn) break;
            if (userDot == 'X') {
                gameOn = aiTurn();
                if (!gameOn) break;
            }
        }
        int choice = getChoice("Сыграем еще раз? (1 - да, 0 - нет) ", 0);
        if (choice == -1 || choice == 0) {
            System.out.println("Завершаем процессы");
        } else {
            System.out.println();
            choice = getChoice("Сохраняем прежние параметры? (1 - да, 0 - выберем новые) ", 0);
            if (choice == 1) gameStart(false);
            else if (choice == 0) gameStart(true);
            else System.out.println("Завершаем процессы...");
        }
    }

    /**
     * Настройка переменных, необходимых для игрового процесса. Предлагаем выбор между значениями по умолчанию
     * и возможностью задать свои значения.
     * @return если введено условие выхода -1, возвращаем true, иначе - false
     */
    public static boolean setGameParameters() {
        System.out.println("""
                
                |\t\t\tЗначения по умолчанию:
                |
                |\t-- Уровень сложности 3 - Medium+ (доступно еще три уровня: 1 - easy, 2 - medium и 4 - hard),
                |\t      Если выбрать ур. 4 (МиниМакс), то вы попадете в спец. режим непобедимого МиниМакса :)
                |\t      Поле 3x3, количество точек для выигрыша - 3.Это сделано для упрощения "жизни" алгоритму
                |\t      и ускорения хода компьютера. В противном случае ход компьютера придется ждать очень долго.
                |\t-- Поле 10x12 (Максимальный размер поля 15x15),
                |\t-- 4 точки для выигрыша (Возможно установить длину не меньше 3 и не больше длины/ширины поля),
                |\t-- Вы играете "X" (в случае выбора "O" компьютер ходит первым как "X").
                """);
        int choice = getChoice("Корректируем параметры игры? (1 - да, 0 - нет): ", 0);
        if (choice == -1) return true;
        if (choice == 1) {
            System.out.println();
            // Если выбран МиниМакс (ур. 4), то мы "упрощаем ему жизнь" при выборе дальнейших настроек,
            // чтобы не ждать ход компьютера 10-15 минут
            if (getChoice("Выберите уровень сложности (1 - easy, 2 - medium, 3 - medium+, 4 - hard): ", 1) == -1) {
                return true;
            }
            System.out.println();
            if (difLevel == 4) {
                sizeX = 3;
                sizeY = 3;
                dotToWin = 3;
                System.out.println("""
                        Размер поля: 3x3
                        
                        Количество точек для выигрыша: 3
                        """);
            } else {
                if (getChoice("Введите ширину и высоту игрового поля через пробел (минимум 3x3): ", 2) == -1) {
                    return true;
                }
                System.out.println();
                if (getChoice("Введите количество точек для выигрыша (не меньше 3 и не больше ширины/длины поля): ",
                        3) == -1) return true;
                System.out.println();
            }
            if (getChoice("Выберите \"X\" или \"O\" (\"X\" ходит первым) (0 - \"O\", 1 - \"X\"): ", 4) == -1) {
                return true;
            }
        } else {
            System.out.println("\nПринято! Оставляем значения игры по умолчанию. Удачи :)");
            sizeX = 10;
            sizeY = 12;
            dotToWin = 4;
            difLevel = 3;
            userDot = 'X';
            aiDot = 'O';
        }
        if (difLevel != 1 && difLevel != 4) makeMediumVariables();
        turnCounter = 1;
        len = dotToWin * 2 - 1;
        getCombsWin();
        return false;
    }

    /**
     * Переиспользуем код для сокращения его объема :) Передавая различные значения text/marker, запрашиваем
     * ввод необходимых для игры параметров: размера поля, кол-во точек для выигрыша, уровень сложности и
     * маркер игрока (X/O).
     * Отдельно стоят case -1 и case 0:
     *     Case -1 - выбор между значениями переменных в игре по умолчанию и возможностью присвоить им свои значения
     *     Case 0 - это набор предложений с однозначным ответом да/нет:задать параметры игры, сохранить параметры при
     *     повторной игре, сыграть заново после завершения игры
     * @param text текст вопроса
     * @param marker цифровое значение вопроса, от которого зависит конкретный case
     * @return значения необходимых для игры переменных.
     * -1 это общий вариант возвращаемого значения для всех case. В этом случае игра будет завершена
     */
    public static int getChoice(String text, int marker) {
        boolean active = true;
        do {
            System.out.print(text);
            if (sc.hasNextInt()) {
                switch (marker) {
                    // Предложение изменить параметры игры
                    // Запрос повторной игры
                    // При повторной игре выбор между сохранением параметров и назначением новых
                    case 0 -> {
                        int choice = sc.nextInt();
                        if (choice == -1 || choice == 0 || choice == 1) return choice;
                        sc.nextLine();
                    }
                    // Выбор сложности игры
                    case 1 -> {
                        difLevel = sc.nextInt();
                        if (difLevel == -1) return difLevel;
                        if (difLevel == 1 || difLevel == 2 || difLevel == 3 || difLevel == 4) active = false;
                        sc.nextLine();
                    }
                    // Ввод размеров игрового поля
                    case 2 -> {
                        sizeY = sc.nextInt();
                        if (sizeY == -1) return sizeY;
                        sizeX = sc.nextInt();
                        if (sizeX == -1) return sizeX;
                        if (sizeX >= 3 && sizeY >= 3 && sizeX <= 15 && sizeY <= 15) active = false;
                        sc.nextLine();
                    }
                    // Ввод количества точек для выигрыша
                    case 3 -> {
                        dotToWin = sc.nextInt();
                        if (dotToWin == -1) return dotToWin;
                        if (dotToWin >= 3 && dotToWin <= sizeY || dotToWin <= sizeX) active = false;
                        sc.nextLine();
                    }
                    // Выбор игроком X или O
                    case 4 -> {
                        int dot = sc.nextInt();
                        if (dot == -1) return dot;
                        if (dot == 0 || dot == 1) active = false;
                        userDot = dot == 1 ? 'X' : 'O';
                        aiDot = userDot == 'X' ? 'O' : 'X';
                        sc.nextLine();
                    }
                }
            } else {
                sc.nextLine();
            }
        } while (active);
        return 1;
    }

    /**
     * Генерируем выигрышные последовательности. Это строки из символов 'X' и 'O', количество которых
     * определяется dotToWin. В массиве первые две строки для пользователя и для компьютера.
     * Это позволяет не генерировать подобные строки каждый раз при проверке наличия выигрышной комбинации.
     * Длина строки ограничена dotsToWin.
     * Если не включен средний уровень сложности, 3 и 4 строка в массиве остается "null".
     */
    public static void getCombsWin() {
        combsWin[0] = String.valueOf(aiDot).repeat(Math.max(0, dotToWin));
        combsWin[1] = String.valueOf(userDot).repeat(Math.max(0, dotToWin));
    }

    /**
     * Задает значения необходимых переменных для работы метода блокировки потенциально выигрышных ходов
     * на среднем уровне сложности. Логика проста: убавляем количество точек, необходимых для выигрыша, на 1
     * и в таком виде проверяем наличие выигрышной комбинации. Это позволяет опережать игрока.
     */
    public static void makeMediumVariables() {
        lessDots = dotToWin - 1;
        lessLen = lessDots * 2 - 1;
        combsWin[2] = String.valueOf(userDot).repeat(Math.max(0, lessDots));
        combsWin[3] = String.valueOf(aiDot).repeat(Math.max(0, lessDots));
    }

    public static void makeBoard() {
        board = new char[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                board[x][y] = emptyDot;
            }
        }
    }

    public static void showBoard() {
        System.out.print("x/y");
        for (int i = 1; i <= sizeY; i++) {
            if (i < 10) {
                System.out.printf(" %d ", i);
            } else System.out.printf(" %d", i);
        }
        System.out.println();
        for (int i = 0; i < sizeX; i++) {
            if (i < 9) {
                System.out.printf(" %d ", i+1);
            } else System.out.printf(" %d", i+1);
            for (int j = 0; j < sizeY; j++) {
                System.out.printf(" %c ", board[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean isEmpty(int x, int y) {
        return (board[y][x] == emptyDot);
    }

    public static boolean isValid(int x, int y) {
        return (x >= 0 && x < sizeY && y >= 0 && y < sizeX);
    }

    /**
     * Логика проверки выигрыша - все возможные линии относительно координат хода:
     * Проверяется не вся линия, а лишь ее необходимая часть, длина которой зависит от кол-ва точек для выигрыша.
     * Берем часть строки и проверяем в ней наличие выигрышной комбинации методом contains()
     * вертикаль/горизонталь/левая диагональ/правая диагональ - звезда
     * @param x координата хода,
     * @param y координата хода,
     * @param turn чья очередь хода (1 - человек, 0 - компьютер)
     * @return true в случае выигрыша, иначе - false
     */
    public static boolean makeWinStar(int x, int y, int turn, int dotToWin, int len) {
        /*
        Общие примечания к генерации и проверке линий:
            - В условиях проверки каждой линии заложены ограничения по размеру поля,
            чтобы проверяемая точка не "вышла" индексом за его пределы,
            - Каждая линия после генерации тут же проверяется на наличие выигрышной комбинации
            для исключения ненужной дальнейшей проверки в случае выигрыша на одной из линий
         */
        String[] lines = new String[4];
        //Горизонтальная линия
        for (int i = 0; i < len; i++) {
            if ((i + y - dotToWin+1) >= 0 && (i + y - dotToWin+1) < sizeY) {
                lines[0] += String.valueOf(board[x][i + y - dotToWin+1]);
            }
        }
        if (lines[0].contains(combsWin[turn])) return true;
        //Вертикальная линия
        for (int i = 0; i < len; i++) {
            if ((i + x - dotToWin + 1) >= 0 && (i + x - dotToWin + 1) < sizeX) {
                lines[1] += String.valueOf(board[i + x - dotToWin + 1][y]);
            }
        }
        if (lines[1].contains(combsWin[turn])) return true;
        //Левая диагональ (сверху вниз, слева направо)
        for (int i = 0; i < len; i++) {
            if ((i + x - dotToWin + 1) >= 0 && (i + x - dotToWin + 1) < sizeX
                    && (i + y - dotToWin + 1) >= 0 && (i + y - dotToWin + 1) < sizeY) {
                lines[2] += String.valueOf(board[i + x - dotToWin + 1][i + y - dotToWin + 1]);
            }
        }
        if (lines[2].contains(combsWin[turn])) return true;
        //Правая диагональ (сверху вниз, справа налево)
        for (int i = 0; i < len; i++) {
            if ((i + x - dotToWin + 1) >= 0 && (i + x - dotToWin + 1) < sizeX
                    && (y + dotToWin - i - 1) >= 0 && (y + dotToWin - i - 1) < sizeY) {
                lines[3] += String.valueOf(board[i + x - dotToWin + 1][y + dotToWin - i - 1]);
            }
        }
        /*
        Формат проверки этой линии отличается от предыдущих, т. к. после проверки последней линии в случае
        отсутствия выигрыша возвращается false. Отдельной строкой писать возврат false нет смысла.
         */
        return lines[3].contains(combsWin[turn]);
    }

    /**
     * Именно этот метод назван checkWin, потому что он занимается обработкой результатов makeWinStar
     * и выводом результатов
     * @param x координата хода для передачи в makeWinStar,
     * @param y координата хода для передачи в makeWinStar,
     * @param turn очередь хода (1 - человек, 0 - компьютер).
     * @return в случае прекращения игры (независимо от того, кто выиграл) возвращает false, иначе - true.
     * В месте вызова результат этой функции обрабатывается для продолжения или завершения игры.
     */
    public static boolean checkWinAndDraw(int x, int y, int turn) {
        String endLine = "==================================";
        boolean winStatus = makeWinStar(x, y, turn, dotToWin, len);
        if (winStatus && turn == 1) {
            System.out.println(endLine);
            System.out.println("\tВы выиграли! Поздравляю :)");
            return false;
        } else if (winStatus && turn == 0) {
            System.out.println(endLine);
            System.out.println("\t\tВы проиграли :(");
            return false;
        } if (checkFullBoard()) {
            System.out.println(endLine);
            System.out.println("OMG! Это ничья :)\n");
            return false;
        } else return true;
    }

    /**
     * Проверка на ничью. Все предельно просто: если поле заполнено и ранее не сработало условие
     * на победу, то это ничья
     * @return true если пустых клеток нет, иначе - false
     */
    public static boolean checkFullBoard() {
        for (char[] chars : board) {
            for (char aChar : chars) {
                if (aChar == emptyDot) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Принимаем координаты хода игрока и проверяем их на допустимое значение, ввод чисел.
     * Здесь же отрисовываем доску и проверяем на победу или ничью.
     * @return результат проверки на победу. От этого булева значения зависит игровой процесс. В случае выигрыша он
     * будет прерван и в консоль выведется запрос повторной игры
     */
    public static boolean userTurn() {
        int[] dot = new int[2];
        boolean active = true;
        do {
            do {
                System.out.printf("Ход %d. Введите координаты хода x/y через пробел: ", turnCounter);
                if (sc.hasNextInt()) {
                    dot[1] = sc.nextInt() - 1;
                    if (dot[1] == -2) {
                        System.out.println("\nЗавершаем процессы...\n");
                        return false;
                    }
                    dot[0] = sc.nextInt() - 1;
                    active = false;
                    sc.nextLine();
                } else {
                    sc.nextLine();
                }
            } while (active);
        } while (!isValid(dot[0], dot[1]) || !isEmpty(dot[0], dot[1]));
        System.out.println();
        board[dot[1]][dot[0]] = userDot;
        turnCounter++;
        showBoard();
        return checkWinAndDraw(dot[1], dot[0], 1);
    }

    /**
     * Определяем координаты хода компьютера в зависимости от выбранного уровня сложности.
     * Здесь же отрисовываем доску и проверяем на победу или ничью.
     * @return результат проверки на победу. От этого булева значения зависит игровой процесс. В случае выигрыша он
     * будет прерван и в консоль выведется запрос повторной игры
     */
    public static boolean aiTurn() {
        int[] dot = new int[2];
        switch (difLevel) {
            case 1 -> dot = levelRandom();
            case 2 -> dot = levelMedium(userDot);
            case 3 -> dot = levelMediumPlus();
            case 4 -> dot = levelHard();
        }
        board[dot[0]][dot[1]] = aiDot;
        System.out.printf("Ход %d. Компьютер выбрал точку: %d, %d%n\n", turnCounter, dot[0] + 1, dot[1] + 1);
        turnCounter++;
        showBoard();
        return checkWinAndDraw(dot[0], dot[1], 0);
    }

    public static int[] levelRandom() {
        int[] dot = new int[2];
        do {
            dot[0] = (int) (Math.random() * sizeX);
            dot[1] = (int) (Math.random() * sizeY);
        } while (!isEmpty(dot[1], dot[0]));
        return dot;
    }

    /**
     * Проверяем и блокируем каждую "одинокую точку". Если таковой нет, то ищем потенциально выигрышные ходы
     * и блокируем их. Если и таковых нет, то возвращаем рандомную точку.
     * @return массив с координатами хода компьютера.
     */
    public static int[] levelMedium(char dotTurn) {
        int[] dot = blockAloneDot(dotTurn);
        if (isValid(dot[1], dot[0])) return dot;
        dot = blockWinLine(dotTurn, 2, lessDots, lessLen);
        if (isValid(dot[0], dot[1])) return dot;
        if (difLevel == 3) {
            dot[0] = sizeX * 2;
            dot[1] = sizeY * 2;
            return dot;
        }
        return levelRandom();
    }

    /**
     * Метод проверки наличия выигрышного хода (с глубиной 2) для компьютера. Игроку теперь нужно думать не только о
     * своих точках и попытках обойти блок компьютера. Если компьютер увидит возможность выиграть в течение двух
     * ходов, он обязательно воспользуется ею, не забыв заблокировать игроку выигрышный ход
     * В приоритете компьютера выигрыш, так что сначала будет проверка на его наличие, а уже потом блок ходов игрока.
     * Сначала проверяем возможность компьютера выиграть в один ход. Если ее нет, блокируем user выигрышные ходы.
     * Если нет и таковых, возвращаем рандомную точку.
     * @return массив с координатами хода компьютера.
     */
    public static int[] levelMediumPlus() {
        int[] dot = blockWinLine(aiDot, 0, dotToWin, len);
        if (isValid(dot[1], dot[0])) return dot;
        dot = blockWinLine(userDot, 2, lessDots, lessLen);
        if (isValid(dot[0], dot[1])) return dot;
        dot = blockWinLine(aiDot, 3, lessDots, lessLen);
        if (isValid(dot[1], dot[0])) return dot;
        dot = blockAloneDot(userDot);
        if (isValid(dot[1], dot[0])) return dot;
        return levelRandom();
    }

    /**
     * Проверяем наличие выигрышной линии и определяем точку, блокирующую/продолжающую ее. Метод используется для
     * проверки наличия блокирующих потенциально выигрышные ходы игрока и нахождения выигрышных ходов компьютера.
     * @param dotTurn X/Y - маркер компьютера/игрока,
     * @param turn цифровое обозначение хода,
     * @param line количество точек для выигрыша. Нельзя связывать с оригинальным dotToWin, т. к. это не позволит
     *             проверять выигрышные ходы компьютера,
     * @param len длина выигрышной линии. Нельзя связывать с оригинальной len по той же причине, что и line выше
     * @return массив с координаты выигрышной/проигрышной точки. Если не найдена - координаты несуществующей точки.
     */
    public static int[] blockWinLine(char dotTurn, int turn, int line, int len) {
        int[] dot = {sizeX*2, sizeY*2};
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (isEmpty(j, i)) {
                    board[i][j] = dotTurn;
                    if (makeWinStar(i, j, turn, line, len)) {
                        dot[0] = i;
                        dot[1] = j;
                        board[i][j] = emptyDot;
                        return dot;
                    }
                    board[i][j] = emptyDot;
                }
            }
        }
        return dot;
    }

    /**
     * Если начать игру не с края поля (особенно при dotToWin == 3 и размере поля от 3x3), то игрок выиграет за
     * несколько ходов, т. к. от точки можно развить линию в любую сторону и заблокировать ее не удастся.
     * Текущий метод частично (с одной рандомно выбранной стороны) блокирует "одинокую" точку,
     * чтобы в случае развития ее игроком заблокировать потенциально выигрышную линию методом blockWinLine
     * @return в случае обнаружения такой точки возвращаем координаты блокирующей точки, иначе - координаты
     * заведомо несуществующей точки, чтобы не принимать ее в расчет в levelMedium.
     */
    public static int[] blockAloneDot(char dotTurn) {
        int[] randArr = {-1, 1}, randInt = new int[2], dot = {sizeX * 2, sizeY * 2};
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (board[i][j] == dotTurn && checkSquare(i, j, 3)) {
                    do {
                        randInt[0] = (int) (Math.random() * 2);
                        randInt[1] = (int) (Math.random() * 2);
                    } while (!isValid(j - randArr[randInt[1]], i - randArr[randInt[0]]));
                    dot[1] = j - randArr[randInt[1]];
                    dot[0] = i - randArr[randInt[0]];
                    return dot;
                }
            }
        }
        return dot;
    }

    /**
     * Определяем "одинокость" точки для метода blockAloneDot.
     * Проверяем квадрат со стороной size вокруг точки, исключая ее саму.
     * @param x проверяемая координата хода
     * @param y проверяемая координата хода
     * @return true, если точка "одинока", false, если возле нее уже что-либо есть
     * (если уже есть userDot, то это будет точкой отсчета для blockWinLine, а если есть userDot,
     * то опять же это область работы для blockWinLine, т. к. это алгоритм для "одиноких" точек)
     */
    public static boolean checkSquare(int x, int y, int size) {
        int emptyCounter = 0, cellCounter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isValid(y - 1 + i, x - 1 + j) && (x - 1 + i != x || y - 1 + j != y)) {
                    cellCounter++;
                    if (isEmpty(y - 1 + i, x - 1 + j)) {
                        emptyCounter++;
                    }
                }
            }
        }
        return emptyCounter == cellCounter;
    }

    /**
     * Все-таки написал МиниМакс по приколу :) Правда, ограничил область его применения размером поля 3x3
     * @return массив с координатами хода компьютера
     */
    public static int[] levelHard() {
        int[] dot = new int[2];
        int bestScore = Integer.MIN_VALUE;
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (board[i][j] == emptyDot) {
                    board[i][j] = aiDot;
                    int level = miniMax(i, j, board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = emptyDot;
                    if (level > bestScore) {
                        bestScore = level;
                        dot[1] = j;
                        dot[0] = i;
                    }
                }
            }
        }
        return dot;
    }

    /**
     * Проверяем и оцениваем ситуацию в (почти) всех возможных ситуациях на доске. Ограничен альфа/бета отсечением -
     * если оценка одной ветви выше/ниже другой, то не проверяем подветви, т. к. их оценка уже не важна.
     * @param row координата x
     * @param col координата y
     * @param board текущее состояние доски
     * @param turn очередь хода: 1 - человек, 0 - компьютер
     * @param alpha отсечение ненужных ветвей. Значительно сокращаем время проверки
     * @param beta см. в alpha
     * @return оценку состояния игры в зависимости от очереди хода
     */
    public static int miniMax(int row, int col, char[][] board, int turn, int alpha, int beta) {
        if (makeWinStar(row, col, 1, dotToWin, len)) return -10;
        if (makeWinStar(row, col, 0, dotToWin, len)) return 10;
        if (checkFullBoard()) return 0;
        if (turn == 1) {
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < sizeX; i++) {
                for (int j = 0; j < sizeY; j++) {
                    if (board[i][j] == emptyDot) {
                        board[i][j] = aiDot;
                        max = Math.max(max, miniMax(i, j, board, 0, alpha, beta));
                        board[i][j] = emptyDot;
                        alpha = Math.max(max, alpha);
                        if (alpha >= beta) {
                            return max;
                        }
                    }
                }
            }
            return max;
        } else {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < sizeX; i++) {
                for (int j = 0; j < sizeY; j++) {
                    if (board[i][j] == emptyDot) {
                        board[i][j] = userDot;
                        min = Math.min(min, miniMax(i, j, board, 1, alpha, beta));
                        board[i][j] = emptyDot;
                        beta = Math.min(min, beta);
                        if (beta <= alpha) {
                            return min;
                        }
                    }
                }
            }
            return min;
        }
    }
}
