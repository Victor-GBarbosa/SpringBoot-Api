package mkn.api.my_registry_api.resource;

import mkn.api.my_registry_api.entities.Product;
import mkn.api.my_registry_api.entities.User;
import mkn.api.my_registry_api.services.ProductService;
import mkn.api.my_registry_api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductResource {

    @Autowired
    private ProductService service;


    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id) {
        Product obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Product>> findByEmail(@PathVariable String email) {
        List<Product> list = service.findAllByUserEmail(email);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping()
    public ResponseEntity<Product> insert(@RequestBody Product productToInsert) {
        Product obj = service.insert(productToInsert);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

}
