package com.ingenico.epayments;

import com.ingenico.epayments.domain.Transfer;
import com.ingenico.epayments.service.TransferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TransferServiceTests {

    @Autowired
    private TransferService transferService;

    @Test
    public void simpleTransferTest() {

        transferService.cleanRepo();

        // transfer 50.0 from dave to elmo
        transferService.createMonetaryAccount("dave", 100.0);
        assertNotNull(transferService.getMonetaryAccount("dave"));
        transferService.createMonetaryAccount("elmo", 5.0);
        assertNotNull(transferService.getMonetaryAccount("elmo"));

        transferService.doTransfer("dave", "elmo", 50.0);

        double roundingError = 1e-10;
        assertEquals(transferService.getMonetaryAccount("dave").getBalance(), 50.0, roundingError);
        assertEquals(transferService.getMonetaryAccount("elmo").getBalance(), 55.0, roundingError);
    }


    @Test
    public void simpleOverdrawTransferTest() {
        transferService.cleanRepo();

        // transfer 150.0 from dave to elmo
        transferService.createMonetaryAccount("dave", 100.0);
        assertNotNull(transferService.getMonetaryAccount("dave"));
        transferService.createMonetaryAccount("elmo", 5.0);
        assertNotNull(transferService.getMonetaryAccount("elmo"));

        long transferCountBefore = transferService.countTransfers();

        // dave's account get's overdrawn
        assertNull(transferService.doTransfer("dave", "elmo", 150.0));

        double roundingError = 1e-10;
        assertEquals(transferService.getMonetaryAccount("dave").getBalance(), 100.0, roundingError);
        assertEquals(transferService.getMonetaryAccount("elmo").getBalance(), 5.0, roundingError);

        long transferCountAfter = transferService.countTransfers();

        // no recorded transfers
        assertEquals(transferCountBefore, transferCountAfter);
    }

    @Test
    public void simpleConcurrencyTest() {
        transferService.cleanRepo();

        transferService.createMonetaryAccount("dave", 100.0);
        transferService.createMonetaryAccount("elmo", 5.0);

        Transfer t1 = transferService.doTransfer("elmo", "dave", 3.0, 1000);
        Transfer t2 = transferService.doTransfer("elmo", "dave", 3.0, 1000);

        assertNull(t2);
        assertEquals(transferService.getMonetaryAccount("elmo").getBalance(), 2.0, 1e-10);

    }

}
