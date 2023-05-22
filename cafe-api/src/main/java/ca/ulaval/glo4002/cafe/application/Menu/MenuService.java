package ca.ulaval.glo4002.cafe.application.Menu;

import java.util.Map;

import ca.ulaval.glo4002.cafe.domain.shop.ShopRepository;

public class MenuService {
    private final ShopRepository shopRepository;

    public MenuService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public void addToMenu(String name, double price, Map<String, Integer> ingredientNameWithQuantity) {
        var shop = shopRepository.getShop();
        var menu = shop.getMenu();
        menu.addCoffee(name, price, ingredientNameWithQuantity);
        shop.setMenu(menu);
        shopRepository.save(shop);
    }
}
