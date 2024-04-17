import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    //Класс для чтения введенных строк из консоли
    private static BufferedReader br;
    //Игрок должен храниться на протяжении всей игры
    private static FantasyCharacter player = null;
    //Класс для битвы можно не создавать каждый раз, а переиспользовать
    private static BattleScene battleScene = null;


    public static void main(String[] args) {
        //Инициализируем Reader
        br = new BufferedReader(new InputStreamReader(System.in));
        //Инициализируем класс для боя
        battleScene = new BattleScene();
        //Первое, что нужно сделать при запуске игры, это создать персонажа, поэтому мы предлагаем ввести его имя
        System.out.println("Введите имя персонажа:");
        //Далее ждем ввод от пользователя
        try {
            command(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //метод command, в параметры которого мы передаем считанное значение из консоли:
    private static void command(String string) throws IOException {
        //Если это первый запуск, то мы должны создать игрока,
        // именем будет служить первая введенная строка из консоли
        if (player == null) {
            player = new Hero(
                    string, 100, 20, 20, 30, 10
            );
            System.out.printf("Спасти наш мир от драконов вызвался %s! Ура-а-а! В бой!%n",
                    player.getName());

            //Метод для вывода меню
            Navigation();
            try {
                command(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Варианты для команд
        switch (string) {
            case "1": {
                commitSell();

            }
            break;

            case "2": {
                commitFight();
            }
            break;

            case "3":
                System.exit(1);
                break;
            case "да":
                command("2");
                break;
            case "нет": {
                Navigation();
                command(br.readLine());
            }

            //Снова ждем команды от пользователя
            command(br.readLine());
        }
    }

    private static void commitSell() throws IOException {

        if (player != null) {

            // Логика торговли с постепенным уменьшением золота у героя и увеличением здоровья
            int price = 10; // Количество золота, которое требуется заплатить за товар
            int potion = 50; // Количество единиц здоровья на которое увеличивается здоровье героя,
// после покупки зелья
            if (player.getGold() >= price) {
                player.decreaseGold(price, potion);
                System.out.println("Торговля прошла успешно! Желаете продолжить? да/нет");

                String userResponse = br.readLine();
                if (userResponse.equalsIgnoreCase("да")) {
                    command("1");
                } else if (userResponse.equalsIgnoreCase("нет")) {
                } else {
                    System.out.println("Некорректный ввод. Пожалуйста, введите 'да' или 'нет'.");
                }

                Navigation();
            }
            if (player.getGold() < price) {
                player.decreaseGold(price, potion);

                System.out.println("Торговля не состоялась." +
                        "Желаете продолжить? да/нет");

                String userResponse = br.readLine();
                if (userResponse.equalsIgnoreCase("да")) {
                    command("1");
                } else if (userResponse.equalsIgnoreCase("нет")) {
                    Navigation();
                } else {
                    System.out.println("Некорректный ввод. Пожалуйста, введите 'да' или 'нет'.");
                }
            }
            try {
                command(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // навигация
    private static void Navigation() {
        System.out.println("Куда вы хотите пойти?");
        System.out.println("1. К Торговцу");
        System.out.println("2. В темный лес");
        System.out.println("3. Выход");
    }

    private static void commitFight() {

        battleScene.fight(player, createMonster(), new FightCallback() {

            @Override
            public void fightWin() {
                System.out.printf("%s победил! Теперь у вас %d опыта и %d золота, а также осталось %d здоровья.%n",
                        player.getName(), player.getExperience(), player.getGold(), player.getHealth());
                player.printIfLeveledUp();
                System.out.println("Желаете продолжить поход в темный лес или вернуться в город? (да/нет)");
                try {
                    command(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fightLost() {
            }
        });
    }

    interface FightCallback {
        void fightWin();

        void fightLost();
    }

    private static FantasyCharacter createMonster() {
        //Случайность
        int random = (int) (Math.random() * 10);
        //С вероятностью 50% создается или скелет, или гоблин
        if (random % 2 == 0) return new Goblin(
                "Гоблин", 50, 10, 10, 100, 20);
        else return new Skeleton(
                "Скелет", 25, 20, 20, 100, 10);
    }

}