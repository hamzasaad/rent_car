package com.itgate.demo.models;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="client")
public class Client extends User {

    @OneToMany(mappedBy = "client")
    private List<Reclamation> reclamations;

    @OneToMany(mappedBy = "client")
    private List<Demande> demande;

    @OneToMany(mappedBy = "client")
    private List<Comentaire> comentaires;



    @JsonIgnore
    public List<Demande> getDemande() {
        return demande;
    }

    public void setDemande(List<Demande> demande) {
        this.demande = demande;
    }

    @JsonIgnore
    public List<Reclamation> getReclamations() {
        return reclamations;
    }

    public void setReclamations(List<Reclamation> reclamations) {
        this.reclamations = reclamations;
    }



    private String tel;
    private String cin_client;
    private String adress;
    private String email;
    private String stat;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCin_client() {
        return cin_client;
    }

    public void setCin_client(String cin_client) {
        this.cin_client = cin_client;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @JsonIgnore

    public List<Comentaire> getComentaires() {
        return comentaires;
    }

    public void setComentaires(List<Comentaire> comentaires) {
        this.comentaires = comentaires;
    }
}
