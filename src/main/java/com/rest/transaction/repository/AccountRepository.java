package com.rest.transaction.repository;

import com.rest.transaction.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Modifying()
    @Transactional
    @Query("UPDATE Account a SET a.money = :money WHERE a.id = :id")
    int updateAccount(@Param("money") int money, @Param("id") int id);

    @Query("SELECT a.money FROM Account a WHERE a.id = :id")
    int getBalance(@Param("id") int id);

}
