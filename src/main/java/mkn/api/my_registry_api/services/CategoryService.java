package mkn.api.my_registry_api.services;

import jakarta.transaction.Transactional;
import mkn.api.my_registry_api.entities.Category;
import mkn.api.my_registry_api.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    public Category insert(Category category) {
        return repository.save(category);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }


}
