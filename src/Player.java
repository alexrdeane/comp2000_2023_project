import java.util.ArrayList;

public class Player implements Observer {
    private String name;
    private Inventory inventory;
    private double carryWeightCapacity;
    private Inventory storageView;

    public Player(String playerName, double carryCapacity, Inventory sInventory) {
        name = playerName;
        carryWeightCapacity = carryCapacity;
        inventory = sInventory;
    }

    public void setStorageView(Inventory storageInventory) {
        storageView = storageInventory;
    }

    public Inventory getStorageView() {
        return storageView;
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public double getCarryCapacity() {
        return carryWeightCapacity;
    }

    public double getCurrentWeight() {
        double carrying = 0;
        for (ItemInterface item : getInventory().searchItems("")) {
            carrying += item.getWeight();
        }
        return carrying;
    }

    public void store(ItemInterface item, Storage storage) throws ItemNotAvailableException {
        // Do we have the item we are trying to store
        if (!inventory.searchItems("").contains(item)) {
            throw new ItemNotAvailableException(item.getDefinition());
        }
        storage.store(inventory.remove(item));
    }

    public void retrieve(ItemInterface item, Storage storage) throws ItemNotAvailableException, ExceedWeightCapacity {
        if (!storageView.searchItems("").contains(item)) {
            throw new ItemNotAvailableException(item.getDefinition());
        }
        addItem(storage.retrieve(item));
    }

    @Override
    public void update(Inventory storageInventory) {
        this.storageView = new Inventory(storageInventory.searchItems(""));
    }
    public void SetStorageView(Storage storage) {
        // sets the current storageview as this current storage
        this.storageView = storage.getInventory();
        // subscribes the player to the current storage
        storage.Subscribe(this);
    }


    //added so that crafting can check if play has the material
    public boolean hasMaterial(ItemInterface item) {
        return inventory.searchItems("").contains(item);
    }

    //if the material is used in crafting remove it
    public void removeMaterial(ItemInterface item) throws ItemNotAvailableException {
        if (!hasMaterial(item)) {
            throw new ItemNotAvailableException(item.getDefinition());
        }
        inventory.remove(item);
    }


    public void addItem(ItemInterface item) throws ExceedWeightCapacity {
        if (getCurrentWeight() + item.getWeight() > getCarryCapacity()) {
            throw new ExceedWeightCapacity(this, item);
        }
        inventory.addOne(item);
    }

    public void craftItem(CraftableItem craftableItem) throws ItemNotAvailableException, ExceedWeightCapacity {
        for (ItemInterface component : craftableItem.getComponents()) {
            if (!hasMaterial(component)) {
                System.out.println("Missing material: " + component.getName()); // log missing material
                throw new ItemNotAvailableException(component.getDefinition());
            }
            removeMaterial(component);
        }
        addItem(craftableItem);
    }
    

}
