package mkn.api.my_registry_api.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import mkn.api.my_registry_api.entities.OrderProduct;
import mkn.api.my_registry_api.repositories.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@Transactional
public class OrderProductService {

    @Autowired
    private
    OrderProductRepository repository;


    public List<OrderProduct> findAll() {
        return repository.findAll();
    }

    public OrderProduct findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderProduct not found with id: " + id)); //!-1
    }

    public OrderProduct insert(OrderProduct orderProduct) {
        return repository.save(orderProduct);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }


}
