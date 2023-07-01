package hr.kpastor.webshop.dao.model.validation;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ValidatedProductDTO {
    private String name;
    private String category_id;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 3, fraction = 2)
    private Float price;
    private String desc;
    private String imagePath;
    private MultipartFile image;

}
