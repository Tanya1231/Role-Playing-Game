public interface Seller {
    //Метод для продажи
    String sell(Merchant.Goods goods);

    String sell(Trader.Goods goods);
}