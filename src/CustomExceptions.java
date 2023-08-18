public class CustomExceptions extends Exception {
    public CustomExceptions(String itemName) {
        super("Item '" + itemName + "' not found");
    }

     public CustomExceptions(double requiredAmount) {
        super("Insufficient funds: " + requiredAmount + " needed");
    }
}
