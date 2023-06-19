package com.itgate.demo.dao;

import com.itgate.demo.models.ResultContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<ResultContrat, Long> {
}
