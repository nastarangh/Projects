/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.vendingmachine.service;

import com.wiley.vendingmachine.dao.InsufficientFundsException;
import com.wiley.vendingmachine.dao.NoItemInventoryException;
import com.wiley.vendingmachine.dao.VendingMachineAuditDao;
import com.wiley.vendingmachine.dao.VendingMachineDao;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author nastarangh
 */
public class VendingMachineServiceLayerImplTest {
    private VendingMachineServiceLayer service;
        
    public VendingMachineServiceLayerImplTest() {
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();

        service = new VendingMachineServiceLayerImpl(dao, auditDao);
    }


    @Test
    public void testupdateItemInventory() throws Exception {
       
        //the initial inventory is one so this call should return true
        boolean result1 = service.updateItemInventory("Special");
        assertTrue(result1, "The initial inventory of Special was 1, hence the method should successfully decrease the inventory by 1");
        
        try {
            service.updateItemInventory("Special");
            fail("Expected ItemOutofStock was not thrown."); 
        } catch (NoItemInventoryException e){
            return;
        }  
    }
    
    public void testcheckFunds() throws Exception {
        
        BigDecimal smallFund = new BigDecimal("1.5");
        BigDecimal enoughFund = new BigDecimal("3");
        
        boolean result = service.checkFunds(enoughFund, "Special");
        assertTrue(result, "The given fund is enough to purchase the item so method returns true");
        
        try {
            service.checkFunds(smallFund, "Special");
            fail("Expected InsufficientFundsException was not thrown."); 
        } catch (InsufficientFundsException e){
            return;
        } 
        
    }
    
}
