import java.util.ArrayList;
import java.util.List;

public class Storage implements Observible {
    private String storageName;
    private Inventory items;

    // list that will keep track of users watching the storage when multiplayer is added
    private List<Observer> observers;

    public Storage(String name, Inventory startingInventory) {
        storageName = name;
        items = startingInventory;

        observers = new ArrayList<>();
    }

    public Inventory getInventory() {
        return items;
    }

    public String getName() {
        return storageName;
    }

    public void store(ItemInterface item) {
        items.addOne(item);

        //changes the storage items for all users watching the inventory
        UpdateObserversItems();
    }

    public ItemInterface retrieve(ItemInterface item) throws ItemNotAvailableException {
        ItemInterface removed = items.remove(item);

        //changes the storage items for all users watching the inventory
        UpdateObserversItems();
        return removed;
    }

    
    //if observers are not already a part of the list
    @Override
    public void Subscribe(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    //removes the user from observing
    @Override
    public void UnSubscribe(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void UpdateObserversItems() {
        for (Observer observer : observers) {
            observer.update(this.items); 
        }
    }
}
