package com.ingenico.epayments.repo;

import com.ingenico.epayments.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// @RepositoryRestResource(exported =  false)
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findByName(@Param("name")String name);
}
