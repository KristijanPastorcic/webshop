package hr.kpastor.webshop.dao.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            itemList.add(item);
            total += item.getTotal();
            itemsInBasket += item.getQuantity();
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


    public Map<String, Object> getBasketData(Item item) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("total", this.getTotal());
        responseData.put("count", this.getItemsInBasket());
        responseData.put("item", item.getTotal());
        responseData.put("itemCount", item.getQuantity());
        return responseData;
    }
}
