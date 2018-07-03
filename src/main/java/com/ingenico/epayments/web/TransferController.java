package com.ingenico.epayments.web;


import com.ingenico.epayments.domain.Account;
import com.ingenico.epayments.domain.Transfer;
import com.ingenico.epayments.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * TransferController
 *
 */


@RestController
@RequestMapping("/do")
public class TransferController {

    private TransferService transferService;

    protected TransferController() {
    }

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    /**
     * Create a monetary account
     *
     * @param accountDTO the account(String name, Double balance)
     */
    @RequestMapping(method = RequestMethod.POST, path = "/account")
    @ResponseStatus(HttpStatus.CREATED)
    public void  createMonetaryAccount(@RequestBody @Validated AccountDTO accountDTO) {
        Account account = transferService.createMonetaryAccount(accountDTO.getName(), accountDTO.getBalance());
        if (account == null)
            throw new RuntimeException("invalid account " + accountDTO);

        System.out.println("Account created:" + account);
    }

    /**
     * Transfer a certain monetary amount from one account to another
     *
     * @param transferDTO the transfer(String FromAccount, String toAccount, Double Amount)
     */

    @RequestMapping(method = RequestMethod.POST, path="/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public void doTransfer(@RequestBody @Validated TransferDTO transferDTO) {

        Transfer transfer =transferService.doTransfer(transferDTO.getFromAccount(), transferDTO.getToAccount(), transferDTO.getAmount());

        if (transfer == null)
            throw new RuntimeException("invalid transfer " + transferDTO);

        System.out.println("Transfer processed:" + transfer);
        transferService.allMonetaryAccounts().forEach(a -> System.out.println(a));
    }

    /**
     * Exception handler for all remaining exceptions thrown in this controller
     *
     * @param ex
     * @return error message string
     */

    // handle all Exceptions
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(Exception.class)
    public String returnFromAll(Exception ex) {
        return ex.getMessage();
    }



}
