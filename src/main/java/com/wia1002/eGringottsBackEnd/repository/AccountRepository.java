package com.wia1002.eGringottsBackEnd.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.wia1002.eGringottsBackEnd.model.Account;
import com.wia1002.eGringottsBackEnd.model.ContactList;
import com.wia1002.eGringottsBackEnd.model.Card;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
    @Query("SELECT t FROM Account t WHERE t.account_number=:account_number AND t.mobile=:mobile")
    List<Account> getPeopleByAccountNumberAndMobile(@Param("account_number") String account_number, @Param("mobile") String mobile);
    
    @Query("SELECT u FROM Account u WHERE u.email = :email AND u.password = :password")
    List<Account> findByEmailAndPassword(String email, String password);

    @Query("SELECT u FROM Account u WHERE u.account_number = :account_number AND u.mobile = :mobile")
    List<Account> findByAccountNumberAndMobile(String account_number, String mobile);

    Account findByEmailIgnoreCase(String email);


    Boolean existsByEmail(String email);
}
