
public class FantasyCharacter implements Fighter {
    private final String name;  // имя
    private final int agility;  // ловкость
    private int health;  // здоровье
    private int experience;  //опыт
    private int gold; // золото
    private final int power;  // сила

    // Конструктор
    public FantasyCharacter(String name, int agility, int health, int experience, int gold, int power) {
        this.name = name;
        this.agility = agility;
        this.health = health;
        this.experience = experience;
        this.gold = gold;
        this.power = power;
    }

    @Override
    public int attack() { // если наша ловкость, умноженная на 3, больше, чем случайное значение, то мы атакуем в размере нашей силы, если нет, то возвращаем 0
        if (agility * 3 > getRandomValue()) return power;
        else return 0;
    }

    public void decreaseGold(int price, int potion) { // покупка зелья
        if (gold >= price) {
            gold -= price;
            health += potion;
            System.out.println("Герой " + this.name + " потратил " + price + " золотых." +
                    "Зелье приобретено и здоровье увеличилось на " + potion + " единиц." +
                    " Остаток золота: " + gold + " золотых. " + " Показатель здоровья: " + (health + potion));
        } else {
            System.out.println("У героя " + this.name + " недостаточно средств для покупки зелья" +
                    " за " + price + " золотых.");
        }
    }

    // уровни
    protected int next_lvl = 1;

    public int LVL_UP(int experience) {
        if (experience >= 40 && experience <= 60) {
            return 2;
        } else if (experience >= 60 && experience <= 80) {
            return 3;
        } else if (experience >= 80 && experience <= 100) {
            return 4;
        }
        return 1;
    }

    void printIfLeveledUp() {
        int new_lvl = LVL_UP(experience);
        if (new_lvl > next_lvl) {
            next_lvl = new_lvl;
            System.out.println("Ваш уровень " + next_lvl);
        }
    }

    // Геттеры и сеттеры

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getExperience() {
        return experience;
    }

    public int getGold() {
        return gold;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    //Переопределяем вывод в консоль, чтобы выводилось имя и очки здоровья
    private int getRandomValue() {
        return (int) (Math.random() * 100);
    }

    //Переопределяем вывод в консоль, чтобы выводилось имя и очки здоровья
    @Override
    public String toString() {
        return String.format("%s здоровье:%d", name, health);
    }
}