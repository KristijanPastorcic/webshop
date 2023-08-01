package hr.kpastor.webshop.dao.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Item {
    private Product product;
    private Integer quantity;
    private float price;
    private float total;

    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        price = product.getPrice();
        total = price * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return product.equals(item.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }
}
