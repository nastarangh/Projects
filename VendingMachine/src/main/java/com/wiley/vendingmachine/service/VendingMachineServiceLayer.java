/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.vendingmachine.service;

import com.wiley.vendingmachine.dao.InsufficientFundsException;
import com.wiley.vendingmachine.dao.NoItemInventoryException;
import com.wiley.vendingmachine.dao.VendingMachinePersistenceException;
import com.wiley.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author nastarangh
 */
public interface VendingMachineServiceLayer {

    List<Item> getAllItems() throws VendingMachinePersistenceException;

    Item getItem(String name) throws VendingMachinePersistenceException;

    boolean updateItemInventory(String name)throws VendingMachinePersistenceException, NoItemInventoryException;
    
    boolean checkFunds(BigDecimal fund, String itemName)throws VendingMachinePersistenceException, InsufficientFundsException;
    
    List<Integer> calculateChange(BigDecimal amount)throws VendingMachinePersistenceException;
}
