public class Trader implements Seller {
    private int gold = 30; // Пример начальной суммы денег
    private int price = 10;// Стоимость зелья

    @Override
    public String sell(Merchant.Goods goods) {
        return null;
    }

    @Override
    public String sell(Goods goods) {
        String result = "";
        if (goods == Goods.POTION) {
            if (hasEnoughMoney(10)) {
                updateMoney(10);

                if (gold >= 10) {
                    result = "Вы купили зелье за " + price + " золота. У вас осталось " + gold + " золота." +
                            " Желаете продолжить покупки? (да/нет)";
                } else {
                    result = "Слишком мало денег. Хотите выйти? (да/нет)";
                }
            }
        }
        return result;
    }
    private boolean hasEnoughMoney(int price) {
        return gold >= price;
    }

    public void updateMoney(int price) {
        gold -= price;
    }

    public enum Goods {
        POTION
    }
}
