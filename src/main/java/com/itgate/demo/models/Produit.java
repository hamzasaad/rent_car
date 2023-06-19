package com.itgate.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.List;

@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigInteger prix;
    private String name;
    private String description;
    private String image;
    private int currentQuatity;
    private int quantityStock;
    @ManyToOne
    @JoinColumn(name = "id_categoryproduct")
    private Categoryproduct categoryproduct;

    @ManyToMany
    @JoinTable(name = "product_commande",
            joinColumns =
            @JoinColumn(name = "id_produit"),
            inverseJoinColumns =
            @JoinColumn(name = "id_commande"))
    private List<Commande> commande;

    public Categoryproduct getCategoryproduct() {
        return categoryproduct;
    }

    public void setCategoryproduct(Categoryproduct categoryproduct) {
        this.categoryproduct = categoryproduct;
    }

    public int getCurrentQuatity() {
        return currentQuatity;
    }

    public void setCurrentQuatity(int currentQuatity) {
        this.currentQuatity = currentQuatity;
    }

    public int getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(int quantityStock) {
        this.quantityStock = quantityStock;
    }

    @JsonIgnore
    public List<Commande> getCommande() {
        return commande;
    }

    public void setCommande(List<Commande> commande) {
        this.commande = commande;
    }

    @JsonIgnore
    public List<Commande> getCommandes() {
        return commande;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commande = commandes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getPrix() {
        return prix;
    }

    public void setPrix(BigInteger prix) {
        this.prix = prix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
