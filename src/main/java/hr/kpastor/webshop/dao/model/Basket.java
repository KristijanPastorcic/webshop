package hr.kpastor.webshop.dao.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Basket {

    private List<Item> itemList = new ArrayList<>();
    private int itemsInBasket;

    private float total;

    public void add(Item item) {
        if (itemList.contains(item)) {
            Item original = itemList.get(itemList.indexOf(item));
            original.setQuantity(original.getQuantity() + item.getQuantity());
            original.setTotal(original.getTotal() + item.getTotal());
            this.itemsInBasket += item.getQuantity();
            this.total += item.getTotal();
        } else {
            itemsInBasket += item.getQuantity();
            total += item.getTotal();
            itemList.add(item);
        }

    }

    /**
     * Remove the product and update the baskets total and itemsInBasket.
     *
     * @param index of product in the list
     */
    public void removeProduct(int index) {
        Item item = getItemList().get(index);
        setTotal(getTotal() - item.getTotal());
        setItemsInBasket(getItemsInBasket() - item.getQuantity());
        itemList.remove(index);
    }

    public float updateItemQuantity(String action, int index) {
        Item item = itemList.get(index);
        if (item.getQuantity() < 1)
            return item.getTotal();
        if (action.equals("-")) {
            item.setQuantity(item.getQuantity() - 1);
            item.setTotal(item.getTotal() - item.getPrice());
            setTotal(getTotal() - item.getPrice());
        } else {
            item.setQuantity(item.getQuantity() + 1);
            item.setTotal(item.getTotal() + item.getPrice());
            setTotal(getTotal() + item.getPrice());
        }
        return item.getTotal();
    }
}
