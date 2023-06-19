package hr.kpastor.webshop.dao.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Category {
    @Id
    private String id;
    private String name;
    private List<Product> products;
}
