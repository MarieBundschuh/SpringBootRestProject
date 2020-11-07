package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    public Product findById(int id);
    List<Product> findByPrixGreaterThan(int prixLimit);
    List<Product> findByNomLike(String recherche);
    List<Product> findAllByOrderByNom();

    @Query(value = "SELECT id, nom, prix, prix_achat FROM Product WHERE prix > :prixCher", nativeQuery = true)
    List<Product> chercherUnProduitCher(@Param("prixCher") int prix);
}
