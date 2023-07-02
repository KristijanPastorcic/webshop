package hr.kpastor.webshop.dao.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Basket {

    private List<Item> itemList = new ArrayList<>();
    private Integer itemsInBasket = 0;

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
}
