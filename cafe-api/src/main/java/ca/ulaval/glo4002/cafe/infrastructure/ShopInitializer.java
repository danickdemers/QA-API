package ca.ulaval.glo4002.cafe.infrastructure;

import ca.ulaval.glo4002.cafe.config.DefaultCoffees;
import ca.ulaval.glo4002.cafe.config.DefaultIngredients;
import ca.ulaval.glo4002.cafe.domain.customer.Menu;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.ingredients.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.ingredients.IngredientId;
import ca.ulaval.glo4002.cafe.domain.shop.Shop;
import ca.ulaval.glo4002.cafe.domain.shop.ShopFactory;
import ca.ulaval.glo4002.cafe.domain.shop.ShopRepository;

import static ca.ulaval.glo4002.cafe.config.ShopConfiguration.DEFAULT_INGREDIENT_QUANTITY;

public class ShopInitializer {
    private final ShopRepository shopRepository;
    private final ShopFactory shopFactory;

    private final IngredientInventoryRepository ingredientInventoryRepository;

    public ShopInitializer(ShopRepository shopRepository,
                           ShopFactory shopFactory,
                           IngredientInventoryRepository ingredientInventoryRepository) {
        this.shopRepository = shopRepository;
        this.shopFactory = shopFactory;
        this.ingredientInventoryRepository = ingredientInventoryRepository;
    }

    public void initializeCoffeeShop() {
        Shop shop = shopFactory.create();
        ingredientInventoryRepository.reset();
        createIngredient();
        shop.setMenu(createMenu());
        shopRepository.save(shop);
    }

    public void createIngredient() {
        for (DefaultIngredients defaultIngredients : DefaultIngredients.values()) {
            IngredientId ingredientId = new IngredientId(defaultIngredients.getName());
            Ingredient ingredient = new Ingredient(defaultIngredients.getName(),
                                                   ingredientId, DEFAULT_INGREDIENT_QUANTITY);
            ingredientInventoryRepository.add(ingredient);
        }
    }

    public Menu createMenu() {
        Menu menu = new Menu();
        for (DefaultCoffees defaultCoffees : DefaultCoffees.values()) {
            menu.addCoffee(defaultCoffees.getName(), defaultCoffees.getPrice(), defaultCoffees.getIngredients());
        }
        return menu;
    }
}
