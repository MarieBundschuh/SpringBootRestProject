package com.ecommerce.microcommerce.web.controller;
import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping(value="/Produits", method= RequestMethod.GET)
    public MappingJacksonValue listeProduits(){
        List<Product> produits = productDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listeFiltre = new SimpleFilterProvider().addFilter("monFiltre", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue((produits));
        produitsFiltres.setFilters(listeFiltre);
        return produitsFiltres;

    }

    //@RequestMapping(value = "/Produits/{id}", method = RequestMethod.GET)
    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id){
        return  productDao.findById(id);
    }

    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> sauvegarderUnProduit(@RequestBody Product produit){
        Product productAdded = productDao.save(produit);

        if (productAdded == null ){
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @DeleteMapping(value = "/Produits/{id}")
    public Product supprimerUnProduit(@PathVariable int id){
        return productDao.delete(id);
    }


}
