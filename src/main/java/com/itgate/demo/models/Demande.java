package com.itgate.demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date_depart;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date_retour;
    private float prix_totale;

    private String heur_depart;

    private String heur_retour;
    private String type_payement;
    private String status;

    @OneToMany(mappedBy = "demande")
    private List<Contrat> contrats;

    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL)
    private List<ResultContrat> resultContrats;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_voiture")
    private Voiture voiture;

    public String getType_payement() {
        return type_payement;
    }

    public void setType_payement(String type_payement) {
        this.type_payement = type_payement;
    }

    public List<Contrat> getContrats() {
        return contrats;
    }

    public void setContrats(List<Contrat> contrats) {
        this.contrats = contrats;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @JsonIgnore
    public List<ResultContrat> getResultContrats() {
        return resultContrats;
    }

    public void setResultContrats(List<ResultContrat> resultContrats) {
        this.resultContrats = resultContrats;
    }

    public float getPrix_totale() {
        return prix_totale;
    }

    public void setPrix_totale(float prix_totale) {
        this.prix_totale = prix_totale;
    }

    public String getHeur_depart() {
        return heur_depart;
    }

    public void setHeur_depart(String heur_depart) {
        this.heur_depart = heur_depart;
    }

    public String getHeur_retour() {
        return heur_retour;
    }

    public void setHeur_retour(String heur_retour) {
        this.heur_retour = heur_retour;
    }
}
