package com.wia1002.eGringottsBackEnd.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wia1002.eGringottsBackEnd.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
    @Query("SELECT u FROM Account u WHERE u.username = :username AND u.password = :password")
    List<Account> findByUsernameAndPassword(String username, String password);
}
