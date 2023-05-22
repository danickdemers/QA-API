package ca.ulaval.glo4002.cafe.domain.shop;

import ca.ulaval.glo4002.cafe.config.CubeNames;
import ca.ulaval.glo4002.cafe.config.ShopConfiguration;

public class ShopFactory {
    public Shop create() {
        return create(ShopConfiguration.COFFEE_SHOP_NAME, ShopConfiguration.NB_SEATS_PER_CUBE);
    }

    public Shop create(String shopName, int seatsPerCube) {
        ShopBuilder shopBuilder = new ShopBuilder(seatsPerCube);
        for (var cube : CubeNames.values()) {
            shopBuilder.addCube(cube.getName());
        }
        return shopBuilder.build(shopName);
    }
}
