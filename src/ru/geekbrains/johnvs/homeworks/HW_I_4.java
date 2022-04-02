package ru.geekbrains.johnvs.homeworks;

import java.util.Scanner;

public class HW_I_4 {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        gameStart();
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
    static int winDots, lenDots;
    //Комбинации выигрышных линий. Подробнее см. в описании функции getCombsWin
    static String[] combsWin = new String[4];

    public static void gameStart() {
        System.out.println("\n\t\t\t\tПриветствую в игре \"Крестики-нолики\"\n");
        System.out.println("Внимание! Координатная сетка инвертирована: x - горизонталь, y - вертикаль");
        System.out.println("Сначала вводится координата x, затем через пробел координата y. Удачи :)");
        setGameParameters();
        makeBoard();
        System.out.printf("\nПоле для игры размером %dx%d\nX - вертикаль, Y - горизонталь\n\n", sizeX, sizeY);
        showBoard();
        System.out.println("\nИгра начинается! \"X\" ходит первым!");
        System.out.println();
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
        getChoice("Сыграем еще раз? (1 - да, 0 - нет) ", 0);
    }

    /**
     * Настройка переменных, необходимых для игрового процесса. Предлагаем выбор между значениями по умолчанию
     * и возможностью задать свои значения.
     */
    public static void setGameParameters() {
        if (getChoice("""
             Скорректируем параметры игры или оставим по умолчанию?
                        
             |\t\t\tЗначения по умолчанию:
             |
             |\t-- Поле 14x15 (Возможно установить поле любого размера, но рекомендуется не доходить до 100),
             |\t-- 4 точки для выигрыша (Возможно установить длину не меньше 3 и не больше длины/ширины поля),
             |\t-- Уровень сложности 1 - рандом (доступно еще два),
             |\t-- Вы играете "X" (в случае выбора "O" компьютер ходит первым как "X").
                        
             \t\tКорректируем параметры игры? (1- да, 0 - нет):\040""", -1)) {
            System.out.println();
            getChoice("Введите ширину и высоту игрового поля через пробел (минимум 3x3): ", 1);
            System.out.println();
            getChoice("Введите количество точек для выигрыша (не меньше 3 и не больше ширины/длины поля): ", 2);
            System.out.println();
            getChoice("Выберите уровень сложности (1 - рандом, 2 - блок, 3 - выигрыш): ", 3);
            System.out.println();
            getChoice("Выберите \"X\" или \"O\" (\"X\" ходит первым) (0 - \"O\", 1 - \"X\"): ", 4);
            if (difLevel != 1) ifBlock();
        } else {
            System.out.println("Принято! Оставляем значения игры по умолчанию. Удачи :)");
            sizeX = 14;
            sizeY = 15;
            dotToWin = 4;
            difLevel = 1;
            userDot = 'X';
            aiDot = 'O';
        }
        turnCounter = 1;
        len = dotToWin * 2 - 1;
        getCombsWin();
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
    public static void ifBlock() {
        winDots = dotToWin - 1;
        lenDots = winDots * 2 - 1;
        combsWin[2] = String.valueOf(userDot).repeat(Math.max(0, winDots));
        combsWin[3] = String.valueOf(aiDot).repeat(Math.max(0, winDots));
    }

    /**
     * <div>
     *     Переиспользуем код для сокращения его объема :) Передавая различные значения text/marker, запрашиваем
     *     ввод необходимых для игры параметров: размера поля, кол-во точек для выигрыша, уровень сложности и
     *     маркер игрока (X/O).
     * </div>
     * <div>
     *     Отдельно стоят case -1 и case 0:
     *     Case -1 - выбор между значениями переменных в игре по умолчанию и возможностью присвоить им свои значения
     *     Case 0 - это предложение сыграть заново после завершения игры.
     * </div>
     * @param text текст вопроса
     * @param marker цифровое значение вопроса, от которого зависит конкретный case
     */
    public static boolean getChoice(String text, int marker) {
        boolean active = true;
        do {
            System.out.print(text);
            if (sc.hasNextInt()) {
                switch (marker) {
                    case -1 -> {
                        int choice = sc.nextInt();
                        if (choice == 0 || choice == 1) return choice == 1;
                        sc.nextLine();
                    }
                    case 0 -> {
                        int choice = sc.nextInt();
                        if (choice == 0 || choice == 1) active = false;
                        if (choice == 1) gameStart();
                        else System.out.println("Завершаем процессы...");
                        sc.nextLine();
                    }
                    case 1 -> {
                        sizeY = sc.nextInt();
                        sizeX = sc.nextInt();
                        if (sizeX >= 3 && sizeY >= 3) active = false;
                        sc.nextLine();
                    }
                    case 2 -> {
                        dotToWin = sc.nextInt();
                        if (dotToWin >= 3 && dotToWin <= sizeY && dotToWin <= sizeX ) active = false;
                        sc.nextLine();
                    }
                    case 3 -> {
                        difLevel = sc.nextInt();
                        if (difLevel == 1 || difLevel == 2 || difLevel == 3) active = false;
                        sc.nextLine();
                    }
                    case 4 -> {
                        int dot = sc.nextInt();
                        userDot = dot == 1 ? 'X' : 'O';
                        aiDot = userDot == 'X' ? 'O' : 'X';
                        if (dot == 0 || dot == 1) active = false;
                        sc.nextLine();
                    }
                }
            } else {
                sc.nextLine();
            }
        } while (active);
        return false;
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
     * @return в случае прекращения игры (независимо от того, кто выиграл) возвращает false, иначе - true. В месте вызова результат
     * этой функции обрабатывается для продолжения или завершения игры.
     */
    public static boolean checkWin(int x, int y, int turn) {
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
        } else return true;
    }

    public static boolean userTurn() {
        int[] dot = new int[2];
        boolean active = true;
        do {
            do {
                System.out.printf("Ход %d. Введите координаты хода x/y через пробел: ", turnCounter);
                if (sc.hasNextInt()) {
                    dot[1] = sc.nextInt() - 1;
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
        return checkWin(dot[1], dot[0], 1);
    }

    public static boolean aiTurn() {
        int[] dot = new int[2];
        switch (difLevel) {
            case 1 -> dot = levelRandom();
            case 2 -> dot = levelMedium(userDot);
            case 3 -> dot = levelMediumPlus();
        }
        board[dot[0]][dot[1]] = aiDot;
        System.out.printf("Ход %d. Компьютер выбрал точку: %d, %d%n\n", turnCounter, dot[0] + 1, dot[1] + 1);
        turnCounter++;
        showBoard();
        return checkWin(dot[0], dot[1], 0);
    }

    public static int[] levelRandom() {
        int[] dot = new int[2];
        dot[0] = (int) (Math.random() * sizeX);
        dot[1] = (int) (Math.random() * sizeY);
        if (!isEmpty(dot[1], dot[0])) levelRandom();
        return dot;
    }

    /**
     * Проверяем и блокируем каждую "одинокую точку". Если таковой нет, то ищем потенциально выигрышные ходы
     * и блокируем их. Если и таковых нет, то возвращаем рандомную точку.
     * @return массив с координатами точки.
     */
    public static int[] levelMedium(char dotTurn) {
        int[] dot = blockAloneDot(dotTurn);
        if (isValid(dot[1], dot[0])) return dot;
        dot = blockWinLine(dotTurn, 2, winDots, lenDots);
        if (isValid(dot[0], dot[1])) return dot;
        if (difLevel == 3) {
            dot[0] = sizeX * 2;
            dot[1] = sizeY * 2;
            return dot;
        }
        return levelRandom();
    }

    /**
     * <div>
     *     Метод проверки наличия выигрышного хода (с глубиной 2) для компьютера. Ироку теперь нужно думать не только о
     *     своих точках и попытках обойти блок компьютера. Если компьютер увидит возможность выиграть в течение двух
     *     ходов, он обязательно воспользуется ею, не забыв заблокировать игроку выигрышный ход
     * </div>
     *
     * <div>
     *     В приоритете компьютера выигрыш, так что сначала будет проверка на его наличие, а уже потом блок ходов игрока.
     *     Сначала проверяем возможность компьютера выиграть в один ход. Если ее нет, блокируем user выигрышные ходы.
     *     Если нет и таковых, возвращаем рандомную точку
     * </div>
     * <div>
     *     P. S.
     *     Было желание написать MiniMax (уже писал на Python), но тут пришлось бы сильно ограничивать глубину прохода,
     *     т. к. в теории можно сгенерировать безразмерную доску. Даже поле 10x10 серьезно загрузит алгоритм (даже с
     *     альфа/бета отсечением) и появятся ощутимые задержки.
     * </div>
     * @return координаты точки
     */
    public static int[] levelMediumPlus() {
        int[] dot = blockWinLine(aiDot, 0, dotToWin, len);
        if (isValid(dot[1], dot[0])) return dot;
        dot = levelMedium(userDot);
        if (isValid(dot[0], dot[1])) return dot;
        dot = blockWinLine(aiDot, 3, winDots, lenDots);
        if (isValid(dot[1], dot[0])) return dot;
        return levelRandom();
    }

    /**
     * Проверяем наличие выигрышной линии и определяем точку, блокирующую/продолжающую ее. Метод используется для
     * проверки наличия блокирующих потенциально выигрышные ходы игрока и нахождения выигрышных ходов компьютера.
     * @param dotTurn X/Y - маркер компьютера/игрока,
     * @param turn цифровое обозначение хода,
     * @param line количество точек для выигрыша. Нельзя связывать с оригинальным dotToWin, т. к. это не позволит
     *      *             проверять выигрышные ходы компьютера,
     * @param len длина выигрышной линии. Нельзя связывать с оригинальной len по той же причине, что и line выше
     * @return координаты выигрышной/проигрышной точки. Если не найдена - координаты несуществующей точки.
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
                        return dot;
                    }
                    board[i][j] = emptyDot;
                }
            }
        }
        return dot;
    }

    /**
     * Если начать игру не с края поля (особенно при dotToWin == 3), то игрок выиграет за несколько ходов,
     * т. к. от точки можно развить линию в любую сторону и заблокировать ее не удастся.
     * Текущий метод частично (с одной рандомно выбранной стороны) блокирует "одинокую" точку,
     * чтобы в случае развития ее игроком заблокировать потенциально выигрышную линию методом blockWinLine
     * @return в случае обнаружения такой точки возвращаем координаты блокирующей точки, иначе - координаты
     * заведомо несуществующей точки, чтобы не принимать ее в расчет в levelMedium.
     */
    public static int[] blockAloneDot(char dotTurn) {
        int[] randArr = {-1, 1}, randInt = new int[2], dot = {sizeX * 2, sizeY * 2};
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (board[i][j] == dotTurn && checkSquare(i, j)) {
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
     * Проверяем квадрат 3x3 вокруг точки, исключая ее саму.
     * @param x проверяемая координата хода
     * @param y проверяемая координата хода
     * @return true, если точка "одинока", false, если возле нее уже что-либо есть
     * (если уже есть userDot, то это будет точкой отсчета для blockWinLine, а если есть userDot,
     * то опять же это область работы для blockWinLine, т. к. это алгоритм для "одиноких" точек)
     */
    public static boolean checkSquare(int x, int y) {
        int emptyCounter = 0, cellCounter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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
}
