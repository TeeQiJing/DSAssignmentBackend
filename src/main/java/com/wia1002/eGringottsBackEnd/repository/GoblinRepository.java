package com.wia1002.eGringottsBackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.wia1002.eGringottsBackEnd.model.Goblin;

@Repository
public interface GoblinRepository extends JpaRepository<Goblin, String> {
    @Query("SELECT t FROM Goblin t WHERE t.email=:email AND t.password=:password")
    List<Goblin> getGoblinByEmailAndPassword(@Param("email") String email,@Param("password") String password);

}
