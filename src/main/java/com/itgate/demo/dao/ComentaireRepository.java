package com.itgate.demo.dao;

import com.itgate.demo.models.Comentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentaireRepository extends JpaRepository<Comentaire,Long>{
}
