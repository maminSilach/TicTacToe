package TicTacToe;

import java.util.*;

public class GameField {
    // значение под которым будет играть игрок( X or 0)
    char valueOfPlayer = ' ';
    // значение для компьюетра
    char valueOfCPU;
    // Список комбинаций которые выбрал игрок, чтобы определить его победу
    ArrayList<Integer> playerPositions = new ArrayList<>();
    // Список комбинаций компьютера
    ArrayList<Integer> CPUPositions = new ArrayList<>();
    // Все выигрышные комбинации, которые существуют
    List<List<Integer>> winner = listWinner();

    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    // Выбор кто будет ходить первым
    int count = random.nextInt(2);
    // Наше поле
    char [][] gameField = new char[][]{
            {' ','|',' ','|',' '},
            {'-','+','-','+','-'},
            {' ','|',' ','|',' '},
            {'-','+','-','+','-'},
            {' ','|',' ','|',' '}};
    // Конструктор класса откуда все и начинается
        public GameField(){
            while (valueOfPlayer ==  ' '){
                valueOfPlayer = validationOfValue();
            }
            valueOfCPU = (valueOfPlayer == 'X' ? '0' : 'X');
            // Пока не определится победитель, игра будет продолжаться
            while (!checkWinner()) {
                // Проверка на ничью
                if(checkingTheDraw()){
                    System.out.println("Ничья!");
                    return;
                }
               if(count == 0){
                   stepOfPlayer();
                   count = 1;
               } if(count == 1){
                   stepOfCPU();
                   count = 0;
                }
            }
        }

        // печать поля
        public void printField(){
            for(char [] chars : gameField){
                for(char row : chars){
                    System.out.print(row);
                }
                System.out.println();
            }
        }
        // ход игрока
        public void stepOfPlayer(){
           int number = -1;
            while (number == -1){
              number = checkingInputErrors();
            }
            System.out.println("Ваш ход: ");
            playerPositions.add(number);
            applyToField(number,valueOfPlayer);
        }
    // Ход компьютера
        public void stepOfCPU(){
            int number = random.nextInt(10);
           while (!checkingOccupancyOfPlace(number)) {
               number =  random.nextInt(10);
           }
            System.out.println("Ход компьютера : ");
           CPUPositions.add(number);
           applyToField(number,valueOfCPU);


        }

        // проверка на корректность значения
        public char validationOfValue(){
            try{
                System.out.println("Укажите значение которое будете наносить на поле (X или 0)");
                String line = scanner.nextLine();
                if(line.equals("X") || line.equals("Х")) {
                    return 'X';
                } else if(line.equals("0")) {
                    return '0';
                }
            } catch (Exception e) {
                System.err.println("Укажите точное значение,X или 0");
            }
            System.err.println("Вы указали неверное значение");
            return ' ';
        }
        // проверка на корректность числа
        public int checkingInputErrors(){
            try {
                System.out.println("Укажите место на поле (1 - 9)");
                String number = scanner.nextLine();
                while (!validationOfNumb(Integer.parseInt(number))){
                    number = scanner.nextLine();
                }
                return Integer.parseInt(number);
            } catch (Exception e) {
                System.err.println("Неверное значение");
            }
            return -1;
        }

        // нанесение крестика или нолика на поле
        public void applyToField(int number, char value){
            switch (number) {
                case 1 -> gameField[0][0] = value;
                case 2 -> gameField[0][2] = value;
                case 3 -> gameField[0][4] = value;
                case 4 -> gameField[2][0] = value;
                case 5 -> gameField[2][2] = value;
                case 6 -> gameField[2][4] = value;
                case 7 -> gameField[4][0] = value;
                case 8 -> gameField[4][2] = value;
                case 9 -> gameField[4][4] = value;
            }
            printField();
        }
        // проверка на то, что число подходит под условие
        public boolean validationOfNumb(int number){
            if(number > 0 && number < 10) {
                if(checkingOccupancyOfPlace(number)) {
                    return true;
                }
                System.err.println("На этом месте уже стоит значение");
                return false;
            }
            System.err.println("Значение может быть не меньше 1 и не больше 9!");
            return false;
        }

        // проверка что место свободно под крестик или нолик
        public boolean checkingOccupancyOfPlace(int number){
            return switch (number) {
                case 1 -> gameField[0][0] == ' ';
                case 2 -> gameField[0][2] == ' ';
                case 3 -> gameField[0][4] == ' ';
                case 4 -> gameField[2][0] == ' ';
                case 5 -> gameField[2][2] == ' ';
                case 6 -> gameField[2][4] == ' ';
                case 7 -> gameField[4][0] == ' ';
                case 8 -> gameField[4][2] == ' ';
                case 9 -> gameField[4][4] == ' ';
                default -> false;
            };
        }
        // Все возможные выигрышные комбинации
        public List<List<Integer>> listWinner(){
          List<Integer> list1 = Arrays.asList(1,2,3);
          List<Integer> list2 = Arrays.asList(4,5,6);
          List<Integer> list3 = Arrays.asList(7,8,9);
          List<Integer> list4 = Arrays.asList(1,5,9);
          List<Integer> list5 = Arrays.asList(3,5,7);
          List<Integer> list6 = Arrays.asList(1,4,7);
          List<Integer> list7= Arrays.asList(2,5,8);
          List<Integer> list8 = Arrays.asList(3,6,9);
          List<List<Integer>> winner = new ArrayList<>();
          winner.add(list1);
          winner.add(list2);
          winner.add(list3);
          winner.add(list4);
          winner.add(list5);
          winner.add(list6);
          winner.add(list7);
          winner.add(list8);
          return winner;
        }
        // Алгоритм для поиска победителя
        public boolean checkWinner(){
            for (List<Integer> integers : winner) {
                if (playerPositions.containsAll(integers)) {
                    System.out.println("Вы победили!");
                    return true;
                } else if (CPUPositions.containsAll(integers)) {
                    System.out.println("Компьютер победил!");
                    return true;
                }
            }
                return false;
        }

        // Алгоритм для ничьи
        public boolean checkingTheDraw(){
            for(char [] chars : gameField) {
                for (char word : chars){
                    if(word == ' '){
                        return false;
                    }
                }
            }
            return true;
        }
}
