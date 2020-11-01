/** Not used anymore with JPA

package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDaoImpl implements ProductDao {

    public static List<Product> products = new ArrayList<>();
    static {
        products.add(new Product(1, new String("Laptop"), 350, 120));
        products.add(new Product(2, new String("Aspirateur Robot"), 500, 200));
        products.add(new Product(3, new String("Table de Ping Pong"), 750, 400));
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product findById(int id) {
        return products.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Product save(Product product) {
        products.add(product);
        return product;
    }

    @Override
    public Product delete(int id) {
        Product productToRemove = products.stream().filter(product -> product.getId() == id).findFirst().get();
        products.remove(productToRemove);
        return productToRemove;
    }
}
 */
