/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.vendingmachine.dao;


import com.wiley.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author nastarangh
 */
public interface VendingMachineDao {
    Item addItem(String name, Item item) throws VendingMachinePersistenceException;

    List<Item> getAllItems() throws VendingMachinePersistenceException;

    Item getItem(String name)throws VendingMachinePersistenceException;

    Item updateItemInventory(String name) throws VendingMachinePersistenceException;
    
    List<Integer> calculateChange(BigDecimal amount);
    
    
}
