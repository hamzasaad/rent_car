package com.itgate.demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
public class
Etat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date_depart;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date_retour;
    private String heur_depart;
    private String heur_retour;

    @ManyToOne
    @JoinColumn(name = "id_voiture")
    private Voiture voiture;

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
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
