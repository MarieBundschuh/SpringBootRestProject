package com.ecommerce.microcommerce.web.controller;
import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exception.ProduitGratuitException;
import com.ecommerce.microcommerce.web.exception.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Api ( description =  "Api pour CRUD sur les produits")
@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @ApiOperation(value = "Récupère la liste de tous les produits")
    @RequestMapping(value="/Produits", method= RequestMethod.GET)
    public MappingJacksonValue listeProduits(){
        List<Product> produits = productDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listeFiltre = new SimpleFilterProvider().addFilter("monFiltre", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue((produits));
        produitsFiltres.setFilters(listeFiltre);
        return produitsFiltres;

    }

    @GetMapping(value = "Test/Produits/PrixMax/{prixLimit}")
    public List<Product> cherchePrixMax(@PathVariable int prixLimit){
        return productDao.findByPrixGreaterThan(prixLimit);
    }

    @GetMapping(value = "/Produits/PrixCher/{prixCher}")
    public List<Product> cherchePrixCher(@PathVariable int prixCher){
        return productDao.chercherUnProduitCher(prixCher);
    }

    @ApiOperation(value = "Recherche un produit avec une partie de son nom")
    @GetMapping(value = "/Produits/Find/{recherche}")
    public List<Product> rechercheParNom(@PathVariable String recherche){
        return productDao.findByNomLike("%" + recherche + "%");
    }


    //@RequestMapping(value = "/Produits/{id}", method = RequestMethod.GET)
    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id){

        Product product = productDao.findById(id);

        if (product==null){
            throw  new ProduitIntrouvableException("Le produit avec l'id " + id + " est introuvanle.");
        }
        return product;
    }

    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> sauvegarderUnProduit(@Valid @RequestBody Product produit){

        if (produit.getPrix() == 0){
            throw new ProduitGratuitException("Le prix doit être supperieur à 0");
        }
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
    public void supprimerUnProduit(@PathVariable int id){
        productDao.deleteById(id);
    }

    @PutMapping(value = "/Produits")
    public void majUnProduit(@RequestBody Product product){
        productDao.save(product);
    }

    @GetMapping(value = "/AdminProduits")
    public HashMap<String, String> calculerMargeProduit(){
        HashMap<String, String> resultats = new HashMap<String, String>();

        for (Product product : productDao.findAll()) {
            int marge = product.getPrix() - product.getPrixAchat();
            resultats.put(product.toString(), String.valueOf(marge));
        }
        return resultats;
    }

    @GetMapping(value = "/Produits/trier/")
    public List<Product> trierProduitsParOrdreAlphabetique(){
        //return productDao.findAll(Sort.by("nom").ascending());
        return productDao.findAllByOrderByNom();
    }

}
