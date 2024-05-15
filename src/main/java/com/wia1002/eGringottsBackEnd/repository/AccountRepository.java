package com.wia1002.eGringottsBackEnd.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.wia1002.eGringottsBackEnd.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
 


}
