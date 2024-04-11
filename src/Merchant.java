public class Merchant implements Seller {
    //Метод для продажи
    @Override
    public String sell(Goods goods) {
        String result = "";
        if (goods == Goods.POTION) {
            result = "potion";
        }
        return result;
    }

    @Override
    public String sell(Trader.Goods goods) {
        return null;
    }

    //Энам для товаров
    public enum Goods {
        POTION
    }
}