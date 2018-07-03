package com.ingenico.epayments;

import com.ingenico.epayments.domain.Account;
import com.ingenico.epayments.domain.Transfer;
import com.ingenico.epayments.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EpaymentsApplication implements CommandLineRunner {

    @Autowired
    TransferService transferService;


    private void uglyBanner() {
        System.out.println("**************************************************************************");
    }



    public static void main(String[] args) {
		SpringApplication.run(EpaymentsApplication.class, args);
	}


    /**
     * Initialise Database at startup
     *
     * @param strings
     * @throws Exception
     */

    @Override
	public void run(String... strings) throws Exception {
        initDB();
    }


    private void initDB() {

        Account anna = transferService.createMonetaryAccount("anna", 100.0);
        Account bob = transferService.createMonetaryAccount("bob", 200.0);
        Account carl = transferService.createMonetaryAccount("carl", 300.0);

        uglyBanner();
        uglyBanner();
        System.out.println();
        transferService.allMonetaryAccounts().forEach(a -> System.out.println(a));
        System.out.println("exposing rest-api on localhost:8080");
        System.out.println();
        System.out.println("      /do/account    -> create a monetary account {name: <name>, balance: <balance>}");
        System.out.println("      /do/transfer   -> transfer an amount between accounts {fromAccount: <name>, toAccount: <name>, amount: <amount> ");
    }


}
