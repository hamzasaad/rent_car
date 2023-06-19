package com.itgate.demo.Controlleur;

import com.itgate.demo.dao.ClientRepository;
import com.itgate.demo.dao.CommandeRepository;
import com.itgate.demo.dao.ProduitRepository;
import com.itgate.demo.models.Client;
import com.itgate.demo.models.Commande;
import com.itgate.demo.models.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/users/commande")
public class CommandeController {
    @Autowired
    CommandeRepository comandeRepository ;
    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/get")
    public List<Commande> getDemande(){return comandeRepository.findAll();}



    @PostMapping("/post")
    public Commande postDemande(@RequestBody Commande commande){return comandeRepository.save(commande);}

    @PostMapping("/submit/{id_client}/{ids}")
    public Commande submit(@RequestBody Commande commande, @PathVariable Long id_client, @PathVariable List<Long> ids) {
        Client client = clientRepository.findOne(id_client);

        if (client == null) {
            System.out.println("Client Not Found");
            // Handle client not found error, e.g., return an appropriate error response
            return null; // Return null or throw an exception indicating client not found
        } else {
            List<Produit> produits = new ArrayList<>();

            for (Long productId : ids) {
                Produit produit = produitRepository.findOne(productId);
                if (produit != null) {
                    produits.add(produit);
                } else {
                    System.out.println("Product with ID " + productId + " not found");
                    // Handle product not found error, e.g., return an appropriate error response
                    return null; // Return null or throw an exception indicating product not found
                }
            }

            commande.setClient(client);
            commande.setProduits(produits);
            Commande savedCommande = comandeRepository.save(commande);
            for (Produit produit : produits) {
                System.out.println("Product ID: " + produit.getId());
                produit.getCommandes().add(commande);
                produit.getCommande().add(savedCommande);
                produitRepository.save(produit);
                commande.setProduits(produits);

            }

            return savedCommande;
        }
    }


    @GetMapping ("/getone/{id}")
    public Commande getCommande(@PathVariable Long id){
        return comandeRepository.findOne(id);
    }


}
