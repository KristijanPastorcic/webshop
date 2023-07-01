package hr.kpastor.webshop.dao.model;

import hr.kpastor.webshop.dao.model.validation.ValidatedProductDTO;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Product {

    private String name;
    private Float price;
    private String description;
    private String imagePath;


    public Product copyValidatedDTO(ValidatedProductDTO dto) {
        name = dto.getName();
        price = dto.getPrice();
        description = dto.getDesc();
        imagePath = dto.getImagePath();
        return this;
    }
}
