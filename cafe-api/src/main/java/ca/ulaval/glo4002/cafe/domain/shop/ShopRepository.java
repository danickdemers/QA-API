package ca.ulaval.glo4002.cafe.domain.shop;

public interface ShopRepository {
    Shop getShop();

    void save(Shop shop);

    void reset();
}
