import java.util.ArrayList;
import java.util.List;

public class CraftableItem extends Item {
    private List<ItemInterface> components;

    public CraftableItem(ItemDefinition def) {
        super(def);
        components = new ArrayList<>();
    }

    @Override
    public double getWeight() {
        double weight = super.getWeight();
        for (ItemInterface component : components) {
            weight += component.getWeight();
        }
        return weight;
    }

    @Override
    public String getCompositionDescription() {
        //creates a string of component names for
        StringBuilder description = new StringBuilder();
        for (ItemInterface component : components) {
            description.append(component.getName()).append(", ");
        }
        if (description.length() > 0) {
            description.setLength(description.length() - 2); // Remove trailing comma and space
        }
        return description.toString();
    }

    @Override
    public List<ItemInterface> getComponents() {
        return new ArrayList<>(components);
    }

    @Override
    public void craft(Player player) throws ItemNotAvailableException, ExceedWeightCapacity {
        for (ItemInterface component : components) {
            if (!player.hasMaterial(component)) {
                throw new UnsupportedOperationException("Player is missing required materials");
            }
            player.removeMaterial(component);
        }
        player.addItem(this);
    }

}
