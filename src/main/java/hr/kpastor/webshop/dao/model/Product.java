package hr.kpastor.webshop.dao.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Product {

    @Id
    private String id;
    private String name;
    private String img;
    private Long price;


}
