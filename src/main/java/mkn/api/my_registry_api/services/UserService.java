package mkn.api.my_registry_api.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import mkn.api.my_registry_api.entities.Order;
import mkn.api.my_registry_api.entities.OrderProduct;
import mkn.api.my_registry_api.entities.Product;
import mkn.api.my_registry_api.entities.User;
import mkn.api.my_registry_api.entities.dtos.UserPatchRequest;
import mkn.api.my_registry_api.entities.enums.OrderStatus;
import mkn.api.my_registry_api.entities.enums.UserRole;
import mkn.api.my_registry_api.repositories.ProductRepository;
import mkn.api.my_registry_api.repositories.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;


    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id)); //!-1
    }

    public User findByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    public User insert(User user) {
        return repository.save(user);
    }

    public void deleteByEmail(String email) {
       User userToDelete = findByEmail(email);
       repository.deleteById(userToDelete.getId());

    }

    @Transactional
    public List<Order> getUserOrders(String userEmail) {
        User user = findByEmail(userEmail);
        return user.getOrder();
    }

    //Add product to user Cart
    @Transactional
    public User addProductToUserCart(User user, OrderProduct orderProduct) {

        if (orderProduct.getProduct() != null && orderProduct.getProduct().getId() != null) {
            Product fullProduct = productRepository.findById(orderProduct.getProduct().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            orderProduct.setProduct(fullProduct);
        }

        Order activeOrder = user.getOrder().stream()
                .filter(o -> o.getOrderStatus() == OrderStatus.CART)
                .findFirst()
                .orElse(null);

        if (activeOrder == null) {
            throw new IllegalStateException("No active cart found");
        }

        orderProduct.setOrder(activeOrder);
        orderProduct.reCalcSubtotal();

        if(user.addOrderProduct(orderProduct)) {
            return repository.save(user);
        } else {
          throw new IllegalStateException("Failed to add product to cart");
        }
    }

    @Transactional
    public Order getUserCart (String userEmail) {
        User user = userRepository.findUserByEmail(userEmail);

        return user.getUserCart();
    }

    public User partialUpdate(String email, UserPatchRequest patchRequest) {
        User existingUser = repository.findUserByEmail(email);
        if(existingUser == null) {
            throw new EntityNotFoundException();
        }

        if (StringUtils.hasText(patchRequest.getName())) {
            existingUser.setName(patchRequest.getName());
        }

        if (StringUtils.hasText(patchRequest.getEmail())) {
            existingUser.setEmail(patchRequest.getEmail());
        }

        if (StringUtils.hasText(patchRequest.getPassword())) {
            existingUser.setPassword(patchRequest.getPassword());
        }

        if (StringUtils.hasText(patchRequest.getCpf())) {
            existingUser.setCpf(patchRequest.getCpf());
        }
        try {
            if (UserRole.valueOf(patchRequest.getRole()) == null)
                throw new BadRequestException();
            else {
                existingUser.setRoleStatus(UserRole.valueOf(patchRequest.getRole()));
            }
        } catch (BadRequestException e) {
            return null;
        }

        return repository.save(existingUser);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }


}
