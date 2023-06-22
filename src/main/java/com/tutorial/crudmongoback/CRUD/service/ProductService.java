package com.tutorial.crudmongoback.CRUD.service;

import com.tutorial.crudmongoback.CRUD.dto.ProductDto;
import com.tutorial.crudmongoback.CRUD.entity.Product;
import com.tutorial.crudmongoback.CRUD.repository.ProductRepository;
import com.tutorial.crudmongoback.global.Entity.EntityId;
import com.tutorial.crudmongoback.global.exceptions.AttributeException;
import com.tutorial.crudmongoback.global.exceptions.ResourceNotFoundExceptions;
import com.tutorial.crudmongoback.global.utils.Operations;
import com.tutorial.crudmongoback.security.dto.UserDto;
import com.tutorial.crudmongoback.security.entity.UserEntity;
import com.tutorial.crudmongoback.security.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }
    public Product getOne(int id) throws ResourceNotFoundExceptions {

        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("No se encontró el producto con ID: " + id));
    }
    public Product save(ProductDto productDto) throws AttributeException {
        if(productRepository.existsByName(productDto.getName()))
            throw new AttributeException("El nombre ya existe y no pueden haber duplicados");


       return productRepository.save(mapProductFromDto(productDto));
    }
    public Product update(int id, ProductDto productDto) throws ResourceNotFoundExceptions, AttributeException {
        Product product=productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("No se encontró el producto con el id:" + id));
        if(productRepository.existsByName(productDto.getName()) && productRepository.findByName(productDto.getName()).get().getId()!=id)
            throw new AttributeException("el nombre ya existe ");
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        return productRepository.save(product);
    }
    public Product delete(int id) throws ResourceNotFoundExceptions {
        Product product=productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundExceptions("No se encontró el producto con el id:" + id));
        productRepository.delete(product);
        return product;
    }
    public void deleteAll() throws ResourceNotFoundExceptions{
        if(productRepository.count()<=0) {
            throw new ResourceNotFoundExceptions("no se encontró ningun elemento para eliminar");
        }
        productRepository.deleteAll();
    }
    private Product mapProductFromDto(ProductDto productDto){
        int id= Operations.autoIncrement(productRepository.findAll());
        return new Product(id,productDto.getName(),productDto.getPrice());

    }


}
