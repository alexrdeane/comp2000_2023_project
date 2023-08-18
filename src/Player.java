import java.util.ArrayList;

public class Player extends Shopper{
    private Basket shoppingBasket;
    private Inventory viewOfStoreInventory;

    public Player(String playerName, double startingMoney, Inventory startingInventory) {
        super(playerName, startingMoney, startingInventory);
        shoppingBasket = new Basket();
    }

    public Basket getShoppingBasket() {
        return shoppingBasket;
    }

    public void addToShoppingBasket(ItemInterface itemDataRow) {
        shoppingBasket.add(itemDataRow);
    }

    public void removeFromShoppingBasket(String itemName) {
        shoppingBasket.remove(itemName);
    }

    public ArrayList<ItemInterface> getStoreInventoryView() {
        return viewOfStoreInventory.getStock();
    }

    public Inventory getStoreView() {
        return viewOfStoreInventory;
    }

    public void setStoreView(Inventory storeInventory) {
        viewOfStoreInventory = storeInventory;
    }
}
