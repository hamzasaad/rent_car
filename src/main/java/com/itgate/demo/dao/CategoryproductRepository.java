package com.itgate.demo.dao;

import com.itgate.demo.models.Categoryproduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryproductRepository extends JpaRepository<Categoryproduct,Long>{
}
