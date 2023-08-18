import java.util.ArrayList;

public class Basket implements BasketInterface {
    ArrayList<ItemInterface> items;
    ArrayList<Integer> quantities;

    public Basket() {
        items = new ArrayList<>();
        quantities = new ArrayList<>();
    }

    public int itemIndex(String itemName) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getInventoryTableRow().getColumnOne().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<CartTableRow> getRowData() {
        ArrayList<CartTableRow> data = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            data.add(items.get(i).getCartRow(quantities.get(i) + ""));
        }

        return data;
    }

    @Override
    public void setItemQuantity(String itemName, int qty) {
        int index = itemIndex(itemName);
        if (index != -1) {
            quantities.set(index, qty);
        }
    }

    public void add(ItemInterface item) {
        int index = itemIndex(item.getInventoryTableRow().getColumnOne());
        if (index != -1) {
            quantities.set(index, quantities.get(index) + 1);
        } else {
            items.add(item);
            quantities.add(1);
        }
    }

    @Override
    public void remove(String itemName) {
        int index = itemIndex(itemName);

        if (index != -1) {
            items.remove(index);
            quantities.remove(index);
        }
    }

    @Override
    public void processTransaction(Player from, Seller to) throws CustomExceptions {
        ArrayList<ItemInterface> transactionItems = new ArrayList<>();
        boolean rollback = false;
        // Remove/sell items from the `from` parameter
        for (int i = 0; i < items.size() && !rollback; i++) {
            for (int q = 0; q < quantities.get(i); q++) {
                ItemInterface saleItem;
                try {
                    saleItem = from.sell(items.get(i).getInventoryTableRow().getColumnOne());
                    if (saleItem == null) {
                    rollback = true;
                    break;  // Trigger transaction rollback
                }
                transactionItems.add(saleItem);
                } catch (CustomExceptions e) {
                    e.printStackTrace();
                }
            }
        }

        if (rollback) {
            for (ItemInterface item : transactionItems) {
                from.buy(item);  // Return to `from`
            }
        } else {
            for (ItemInterface item : transactionItems) {
                to.buy(item);  // Have `to` buy each of the transaction items
            }
        }
    }

    @Override
    public void processTransaction(Seller from, Player to) throws CustomExceptions {
        ArrayList<ItemInterface> transactionItems = new ArrayList<>();
        boolean rollback = false;
        // Remove/sell items from the `from` parameter
        for (int i = 0; i < items.size() && !rollback; i++) {
            for (int q = 0; q < quantities.get(i); q++) {
                ItemInterface saleItem;
                try {
                    saleItem = from.sell(items.get(i).getInventoryTableRow().getColumnOne());
                     if (saleItem == null) {
                    rollback = true;
                    break;  // Trigger transaction rollback
                }
                transactionItems.add(saleItem);
                } catch (CustomExceptions e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        if (rollback) {
            for (ItemInterface item : transactionItems) {
                from.buy(item);  // Return to `from`
            }
        } else {
            for (ItemInterface item : transactionItems) {
                to.buy(item);  // Have `to` buy each of the transaction items
            }
        }
    }

}
