package com.itgate.demo.dao;

import com.itgate.demo.models.Commande;
import com.itgate.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    /*@Query("{'username':?0}")*/
    @Query("select d from  Demande  d where d.date_depart= :username")
    User findByUsername(@Param("username") String username);
}
