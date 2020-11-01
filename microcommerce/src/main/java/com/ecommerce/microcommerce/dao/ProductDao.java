package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.Product;

import java.util.List;

public interface ProductDao {

    public List<Product> findAll();
    public Product findById(int id);
    public Product save(Product product);
    public Product delete(int id);

}
