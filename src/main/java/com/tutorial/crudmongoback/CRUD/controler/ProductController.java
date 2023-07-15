package com.tutorial.crudmongoback.CRUD.controler;

import com.tutorial.crudmongoback.CRUD.dto.ProductDto;
import com.tutorial.crudmongoback.CRUD.entity.Product;
import com.tutorial.crudmongoback.CRUD.service.ProductService;
import com.tutorial.crudmongoback.global.dto.MessageDto;
import com.tutorial.crudmongoback.global.exceptions.AttributeException;
import com.tutorial.crudmongoback.global.exceptions.ResourceNotFoundExceptions;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin //para ver en angular

public class ProductController {
    @Autowired
    ProductService productService;
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
    return ResponseEntity.ok(productService.getAll());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable("id") int id) throws ResourceNotFoundExceptions {
      return ResponseEntity.ok(productService.getOne(id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MessageDto> save(@Valid @RequestBody ProductDto productDto) throws AttributeException {
        Product product=productService.save(productDto);
        String message="product "+product.getName()+" has been saved";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK,message));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> update(@PathVariable("id") int id, @Valid @RequestBody ProductDto productDto) throws ResourceNotFoundExceptions, AttributeException {
        Product product=productService.update(id, productDto);
        String message="product "+product.getName()+" has been modified";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK,message));
    }

    @DeleteMapping
    public ResponseEntity<MessageDto> deleteAll() throws ResourceNotFoundExceptions {
        productService.deleteAll();
        String message="Los productos se han eliminado";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK,message));}

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> delete(@PathVariable("id") int id) throws ResourceNotFoundExceptions {
        Product product=productService.delete(id);
        String message="product "+product.getName()+" has been deleted";
        return ResponseEntity.ok(new MessageDto(HttpStatus.OK,message));
    }
}
