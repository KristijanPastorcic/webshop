package hr.kpastor.webshop.dao.repository;

import hr.kpastor.webshop.dao.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {


}
