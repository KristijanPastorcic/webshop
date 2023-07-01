package hr.kpastor.webshop.dao.repository;

import hr.kpastor.webshop.dao.model.Category;
import hr.kpastor.webshop.dao.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    default Optional<Product> findProductByNameAndCategoryId(String name, String id) {
        Optional<Category> categoryOptional = this.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            return category.getProducts()
                    .stream()
                    .filter(product -> product.getName().equals(name))
                    .findFirst();
        } else {
            return Optional.empty();
        }
    }


}
