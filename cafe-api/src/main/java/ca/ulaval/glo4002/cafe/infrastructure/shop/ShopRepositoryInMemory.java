package ca.ulaval.glo4002.cafe.infrastructure.shop;

import ca.ulaval.glo4002.cafe.domain.shop.Shop;
import ca.ulaval.glo4002.cafe.domain.shop.ShopRepository;

public class ShopRepositoryInMemory implements ShopRepository {

    private Shop shop;

    @Override
    public Shop getShop() {
        return shop;
    }

    @Override
    public void save(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void reset() {
        shop.freeAllCubes();
        save(shop);
    }
}
