package com.itgate.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itgate.demo.utils.EtatSerializer;

import javax.persistence.*;
import java.util.List;

@Entity
public class Voiture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serie;

    private String model;
    private String name;
    private Double klm;
    private float prix;
    private String couleur;
    private Integer nbr_place;
    private String carburant;
    private String transmition;
    private String description;
    private String img;


    @ManyToOne
    @JoinColumn(name = "id_city")
    private City city;
    @ManyToOne
    @JoinColumn(name = "id_categorie")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_marque")
    private Marque marque;

    @OneToMany(mappedBy = "voiture")
    @JsonSerialize(using = EtatSerializer.class)
    private List<Etat> etats;

    @OneToMany(mappedBy = "voiture")
    private List<Demande> demandes;

    @JsonIgnore
    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }

    @JsonIgnore
    public List<Etat> getEtats() {
        return etats;
    }

    public void setEtats(List<Etat> etats) {
        this.etats = etats;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getKlm() {
        return klm;
    }

    public void setKlm(Double klm) {
        this.klm = klm;
    }


    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Integer getNbr_place() {
        return nbr_place;
    }

    public void setNbr_place(Integer nbr_place) {
        this.nbr_place = nbr_place;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public String getTransmition() {
        return transmition;
    }

    public void setTransmition(String transmition) {
        this.transmition = transmition;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
}
