package com.itgate.demo.dao;

import com.itgate.demo.models.Etat;
import com.itgate.demo.models.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EtatRepository extends JpaRepository<Etat,Long>{

    @Query("select  e from Etat e where e.date_depart >= :date and  e.date_retour <= :date")
    List<Etat> findAllByDate(@Param("date") Date date);
}
