package mkn.api.my_registry_api.resource;

import jakarta.validation.Valid;
import mkn.api.my_registry_api.entities.Order;
import mkn.api.my_registry_api.entities.OrderProduct;
import mkn.api.my_registry_api.entities.User;
import mkn.api.my_registry_api.entities.dtos.UserPatchRequest;
import mkn.api.my_registry_api.repositories.UserRepository;
import mkn.api.my_registry_api.services.OrderService;
import mkn.api.my_registry_api.services.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService service;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        User obj = service.findByEmail(email);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping()
    public ResponseEntity<User> insert(@RequestBody User user) {
        User obj = service.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PatchMapping("/{userEmail}")
    public ResponseEntity<User> partialUpdate(
            @PathVariable String userEmail,
            @Valid @RequestBody UserPatchRequest userPatchRequest) {
    if(service.partialUpdate(userEmail, userPatchRequest) != null) {
        User updatedUser = service.partialUpdate(userEmail, userPatchRequest);
        return ResponseEntity.ok(updatedUser);
    } else {
        return ResponseEntity.badRequest().build();
    }
    }

    //Get User Orders
    @GetMapping("/{userEmail}/orders")
    public ResponseEntity<?> getOrders(@PathVariable String userEmail) {
        try {
            List<Order> orders = userService.getUserOrders(userEmail);
            return ResponseEntity.ok().body(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }

    }

    //Add Product to user shopping cart
    @PatchMapping("/{userEmail}/order/addProduct")
    public ResponseEntity<User> addProductToCart(@PathVariable String userEmail,
                                                  @Valid @RequestBody OrderProduct orderProduct) {
        return ResponseEntity.ok().body(userService.addProductToUserCart(
                userRepository.findUserByEmail(userEmail),
                orderProduct
        ));
    }

    //Get user shopping cart
    @GetMapping("/{userEmail}/cart")
    public ResponseEntity<Order> getUserShoppingCart (@PathVariable String userEmail) {
        return ResponseEntity.ok().body(userService.getUserCart(userEmail));
    }

    //Close cart order
    @PostMapping("/{userEmail}/finishBuy")
    public ResponseEntity<Object> finishUserBuy(@PathVariable String userEmail) {
        try {
            if (userService.closeOrder(userEmail)) {
                return ResponseEntity.ok().body(userRepository.findUserByEmail(userEmail).getUserCart());
            }
            return ResponseEntity.badRequest().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body("error: ");
        }
    }

    @DeleteMapping("/{userEmail}")
    public ResponseEntity<Void> delete(@PathVariable String userEmail) {
        if(service.findByEmail(userEmail) != null) {
            service.deleteByEmail(userEmail);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
