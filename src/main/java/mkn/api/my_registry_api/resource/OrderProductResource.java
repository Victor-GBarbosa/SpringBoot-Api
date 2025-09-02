package mkn.api.my_registry_api.resource;

import mkn.api.my_registry_api.entities.OrderProduct;
import mkn.api.my_registry_api.entities.User;
import mkn.api.my_registry_api.entities.enums.OrderStatus;
import mkn.api.my_registry_api.services.OrderProductService;
import mkn.api.my_registry_api.services.ProductService;
import mkn.api.my_registry_api.services.TokenService;
import mkn.api.my_registry_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orderProduct")
public class OrderProductResource {

    @Autowired
    private OrderProductService service;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<OrderProduct>> findAll() {
        List<OrderProduct> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderProduct> findById(@PathVariable Long id) {
        OrderProduct obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

//    @PostMapping("/{email}/add")
//    public ResponseEntity<?> insert(@RequestBody OrderProduct orderProduct, @PathVariable String email) {
//        User user =  userService.findByEmail(email);
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        if(user.getOrder().getOrderStatus() != OrderStatus.CART) {
//            return ResponseEntity.badRequest().body("Invalid request: order status is not CART");
//        }
//        orderProduct.setProduct(productService.findById(orderProduct.getProduct().getId()));
//        orderProduct.reCalcSubtotal();
//        orderProduct.addToOrder(user.getOrder());
//
//        OrderProduct obj = service.insert(orderProduct);
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
//        return ResponseEntity.created(uri).body(obj);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        if(service.findById(id) != null) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
