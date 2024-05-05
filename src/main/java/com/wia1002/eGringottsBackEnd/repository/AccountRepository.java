package com.wia1002.eGringottsBackEnd.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.wia1002.eGringottsBackEnd.model.Account;
import com.wia1002.eGringottsBackEnd.model.ContactList;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
    @Query("SELECT t FROM Account t WHERE t.account_number=:test")
    List<Account> getPeopleFromAccount(@Param("test") String test);
    @Query("SELECT u FROM Account u WHERE u.username = :username AND u.password = :password")
    List<Account> findByUsernameAndPassword(String username, String password);
}
