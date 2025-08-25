package mkn.api.my_registry_api.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import mkn.api.my_registry_api.entities.Order;
import mkn.api.my_registry_api.repositories.OrderRepository;
import mkn.api.my_registry_api.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository repository;


    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id)); //!-1
    }

    public Order insert(Order item) {
        return repository.save(item);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }


}
