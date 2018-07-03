package com.ingenico.epayments;

import com.ingenico.epayments.domain.Account;
import com.ingenico.epayments.domain.Transfer;
import com.ingenico.epayments.repo.AccountRepository;
import com.ingenico.epayments.repo.TransferRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTests {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransferRepository transferRepository;


    private void cleanRepo() {
        // start with clean repos
        transferRepository.deleteAll();
        accountRepository.deleteAll();
    }


    @Test
    public void accountRepoTest() {
        cleanRepo();

        // setup 3 accounts
        Account anna = new Account("anna", 100.0);
        Account bob = new Account("bob", 200.0);
        Account carl = new Account("carl", 300.0);

        // save them
        accountRepository.save(anna);
        accountRepository.save(bob);
        accountRepository.save(carl);

        // 3 more accounts
        assertEquals(accountRepository.count(), 3 );


        // show account details
        System.out.println("Accounts count: " + accountRepository.count());
        System.out.println("Anna: " + anna.toString());
        System.out.println("Bob: " + bob.toString());
        System.out.println("Carl: " + carl.toString());

        // drop bobs balance to 100.0
        bob.setBalance(100.0);
        accountRepository.save(bob);
        assertEquals(accountRepository.findByName("bob").getBalance(), 100.0, 1e-10);

        // delete anna
        accountRepository.deleteById(anna.getId());
        assertNull(accountRepository.findByName("anna"));
    }


    @Test
    public void transferRepoTest() {
        cleanRepo();

        // setup 3 accounts
        Account anna = new Account("anna", 100.0);
        Account bob = new Account("bob", 200.0);
        Account carl = new Account("carl", 300.0);

        // save them
        accountRepository.save(anna);
        accountRepository.save(bob);
        accountRepository.save(carl);


        Transfer t1 = new Transfer(anna, bob, 10.0);
        Transfer t2 = new Transfer(bob, carl, 10.0);
        Transfer t3 = new Transfer(carl, anna, 10.0);
        Transfer t4 = new Transfer(anna, bob, 150.0);


        // save them
        transferRepository.save(t1);
        transferRepository.save(t2);
        transferRepository.save(t3);
        transferRepository.save(t4);

        assertEquals(transferRepository.count(), 4);
    }



    @Test
    public void findAccountTest() {
        cleanRepo();

        Account anna = new Account("anna", 100.0);
        accountRepository.save(anna);

        assertNull(accountRepository.findByName("bob"));

        Account sameAnna = accountRepository.findByName("anna");

        assertEquals(anna, sameAnna);

        Account bob = new Account("bob", 200.0);
        accountRepository.save(bob);

        assertNotNull(accountRepository.findByName("bob"));
    }


}
