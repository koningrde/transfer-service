package com.ingenico.epayments.repo;

import com.ingenico.epayments.domain.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(exported =  false)
public interface TransferRepository extends CrudRepository<Transfer, Integer> {
}
