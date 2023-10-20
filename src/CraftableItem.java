import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CraftableItem extends Item {
    private List<ItemInterface> components;

    public CraftableItem(ItemDefinition def, List<ItemInterface> components) {
        super(def);
        this.components = components;
    }

    @Override
    public double getWeight() {
        double totalWeight = super.getWeight();
        for (ItemInterface item : components) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }

    @Override
    public void craft(Player player) throws ItemNotAvailableException {
        for (ItemInterface item : components) {
            player.getInventory().removeOne(item.getDefinition());
        }
        player.getInventory().addOne(this);
    }

    @Override
    public void dismantle(Player player) {
        for (ItemInterface item : components) {
            player.getInventory().addOne(item);
        }
    }

    @Override
    public List<ItemInterface> getComponents() {
        return components;
    }
}
