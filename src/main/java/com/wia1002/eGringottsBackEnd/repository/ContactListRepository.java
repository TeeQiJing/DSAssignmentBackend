package com.wia1002.eGringottsBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wia1002.eGringottsBackEnd.model.ContactDTO;
import com.wia1002.eGringottsBackEnd.model.ContactList;

import jakarta.persistence.criteria.From;
import java.util.List;
public interface ContactListRepository extends JpaRepository<ContactList, Long> {
    @Query("SELECT t FROM ContactList t WHERE t.username = :username ORDER BY t.user_created_name ASC")
    List<ContactList> getContactLists(@Param("username") String username);

    @Query("SELECT t FROM ContactList t WHERE t.username=:username AND (t.account_number=:account_number OR t.contact_mobile=:mobile)")
    List<ContactList> getPeopleFromContact(@Param("username") String username,@Param("account_number") String account_number, @Param("mobile") String mobile);

    // @Query("SELECT t FROM ContactList t WHERE t.username=:username AND t.contact_mobile LIKE CONCAT(:contact_mobile, '%') ORDER BY t.user_created_name ASC")
    // List<ContactList> getContactListsByMobile(@Param("username") String username,@Param("contact_mobile") String contact_mobile);

    // @Query("SELECT t FROM ContactList t WHERE t.username=:username AND t.user_created_name LIKE CONCAT(:user_created_name, '%') ORDER BY t.user_created_name ASC")
    // List<ContactList> getContactListsByUserCreatedName(@Param("username") String username,@Param("user_created_name") String user_created_name);

    // @Query("SELECT t FROM ContactList t WHERE t.username=:username AND t.account_number LIKE CONCAT(:account_number, '%') ORDER BY t.user_created_name ASC")
    // List<ContactList> getContactListsByAccountNumber(@Param("username") String username,@Param("account_number") String account_number);

    @Query("SELECT t FROM ContactList t WHERE t.username=:username AND ( t.contact_mobile LIKE CONCAT(:category, '%') OR t.user_created_name LIKE CONCAT('%', :category, '%') OR t.account_number LIKE CONCAT(:category, '%')) ORDER BY t.user_created_name ASC")
    List<ContactList> getContactListsByCategory(@Param("username") String username,@Param("category") String contact_mobile);
    
}
