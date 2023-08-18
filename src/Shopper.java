public abstract class Shopper {
    String shopperName;
    Inventory shopperInventory;
    double shopperMoney;

    public Shopper(String name, double money, Inventory inventory) {
        shopperName = name;
        shopperMoney = money;
        shopperInventory = inventory;
    }

    public void buy(ItemInterface item) {
        if (Double.valueOf(item.getInventoryTableRow().getColumnThree().trim()) > shopperMoney) {
            return;
        }
        shopperInventory.addOne(item);
        shopperMoney -= getPrice(item);
    }

    public ItemInterface sell(String itemName) throws CustomExceptions {
        ItemInterface item = RemoveItem(itemName);
        if (item != null) {
            shopperMoney += Double.valueOf(getPrice(item));
            return item;
        } else {
            throw new CustomExceptions(itemName);
        }
    }

    public void AddItem(ItemInterface item) {
        shopperInventory.addOne(item);
    }

    public ItemInterface RemoveItem(String itemName) throws CustomExceptions {
        return shopperInventory.removeOne(itemName);
    }

    public String getName() {
        return shopperName;
    }

    public Inventory getInventory() {
        return shopperInventory;
    }

    public double getMoney() {
        return shopperMoney;
    }

    public double getPrice(ItemInterface item) {
        return Double.valueOf(item.getInventoryTableRow().getColumnThree().trim());
    }

}
