package hr.kpastor.webshop.dao.model;

import hr.kpastor.webshop.dao.model.validation.ValidatedCategoryDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Category {
    @Id
    private String id;
    private String name;
    private String description;
    private List<Product> products;


    public Category copyValidatedDTO(ValidatedCategoryDTO dto, ArrayList<Product> products) {
        name = dto.getName().trim();
        description = dto.getDesc().trim();
        this.products = products;
        return this;
    }
}
