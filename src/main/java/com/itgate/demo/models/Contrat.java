package com.itgate.demo.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity

public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date_contract;
    private String cin_client;
    private String nom_client;
    private String prenom_client;
    private BigDecimal Prix_totale;
    private Integer nbr_jour;
    private Date date_depart;
    private Date date_retour;

    @ManyToOne
    @JoinColumn(name = "id_demande")
    private Demande demande;

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate_contract() {
        return date_contract;
    }

    public void setDate_contract(Date date_contract) {
        this.date_contract = date_contract;
    }

    public String getCin_client() {
        return cin_client;
    }

    public void setCin_client(String cin_client) {
        this.cin_client = cin_client;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getPrenom_client() {
        return prenom_client;
    }

    public void setPrenom_client(String prenom_client) {
        this.prenom_client = prenom_client;
    }

    public BigDecimal getPrix_totale() {
        return Prix_totale;
    }

    public void setPrix_totale(BigDecimal prix_totale) {
        Prix_totale = prix_totale;
    }

    public Integer getNbr_jour() {
        return nbr_jour;
    }

    public void setNbr_jour(Integer nbr_jour) {
        this.nbr_jour = nbr_jour;
    }

    public Date getDate_depart() {
        return date_depart;
    }

    public void setDate_depart(Date date_depart) {
        this.date_depart = date_depart;
    }

    public Date getDate_retour() {
        return date_retour;
    }

    public void setDate_retour(Date date_retour) {
        this.date_retour = date_retour;
    }
}
