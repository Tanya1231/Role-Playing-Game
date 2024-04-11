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
    private static boolean isContinuingShopping;
    private static final Trader trader = new Trader();

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
                    string, 100, 20, 20, 20, 0
            );
            System.out.println(String.format("Спасти наш мир от драконов вызвался %s! Ура-а-а! В бой!",
                    player.getName()));
            //Метод для вывода меню
            Navigation();
        }
        //Варианты для команд
        switch (string) {
            case "1": {
                // Trader trader = new Trader();
                // String result = trader.sell(Trader.Goods.POTION);
                // System.out.println(result);
                // // Обработка результата торговли, если нужно
                // command(br.readLine());
                // Thread traderThread = new Thread(() -> {
                //     Trader trader = new Trader();
                //     String result = trader.sell(Trader.Goods.POTION);
                //     System.out.println(result);
                // });
                String result = trader.sell(Trader.Goods.POTION);
                System.out.println(result);
                //break;
            }
            break;

            case "2": {
                // commitFight();
                if (isContinuingShopping) {
                    String result = trader.sell(Trader.Goods.POTION);
                    System.out.println(result);
                    String userInput = br.readLine();
                    if (userInput.equalsIgnoreCase("да")) {
                        command("2");
                    } else {
                        System.out.println("Слишком мало денег. Хотите выйти? (да/нет)");
                        String exitInput = br.readLine();
                        if (exitInput.equalsIgnoreCase("да")) {
                            Navigation();
                            command(br.readLine());
                        } else {
                            isContinuingShopping = false;
                            commitFight();
                        }
                    }
                }
                // break;
            }
            break;

            // case "да":
            //     String result = trader.sell(Trader.Goods.POTION);
            //     System.out.println(result);
            //     if (result.contains("Желаете продолжить покупки?")) {
            //         command("2");
            //     } else {
            //         isContinuingShopping = false;
            //         commitFight();
            //     }
            //     break;
            // }
            // }
            //  break;

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
        }
        //Снова ждем команды от пользователя
        command(br.readLine());
    }

    //после ввода имени, нам открывается меню, вот метод для меню:
    private static void Navigation() {
        System.out.println("Куда вы хотите пойти?");
        System.out.println("1. К Торговцу");
        System.out.println("2. В темный лес");
        System.out.println("3. Выход");
    }

    //То есть пользователю нужно ввести номер пункта,
    // который мы также обрабатываем в методе command, а именно в операторе switch:
    private static void commitFight() {
        battleScene.fight(player, createMonster(), new FightCallback() {
            @Override
            public void fightWin() {
                System.out.println(String.format("%s победил! Теперь у вас %d опыта и %d золота, а также осталось %d едениц здоровья.",
                        player.getName(), player.getExperience(), player.getGold(), player.getHealth()));
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