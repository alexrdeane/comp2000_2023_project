public class Seller extends Shopper{

    public Seller(String storeName, Inventory startingInventory) {
        super(storeName, 0, startingInventory);
    }

    @Override
    public void buy(ItemInterface item) {
        shopperInventory.addOne(item);
    }

}
