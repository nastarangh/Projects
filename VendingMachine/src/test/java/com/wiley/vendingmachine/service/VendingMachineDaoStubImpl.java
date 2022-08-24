/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.vendingmachine.service;

import com.wiley.vendingmachine.dao.VendingMachineDao;
import com.wiley.vendingmachine.dao.VendingMachinePersistenceException;
import com.wiley.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nastarangh
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao{

    
    public Item onlyItem;

    public VendingMachineDaoStubImpl() {
        BigDecimal cost = new BigDecimal("2.50");
        onlyItem = new Item("Special", cost, 1);
        
    }

    public VendingMachineDaoStubImpl(Item testItem){
         this.onlyItem = testItem;
    }
    
    @Override
    public Item addItem(String name, Item item) throws VendingMachinePersistenceException {
        if (name.equals(onlyItem.getItemName())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        List<Item> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        return itemList;
    }

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {
        if (name.equals(onlyItem.getItemName())) {
            return onlyItem;
        } else {
            return null;
        }       
    }

    @Override
    public Item updateItemInventory(String name) throws VendingMachinePersistenceException {
        if (name.equals(onlyItem.getItemName())) {
            onlyItem.updateNum();
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override //No need to test this for service layer as it was already tested for the DAO
    public List<Integer> calculateChange(BigDecimal amount) {
        return null;
    }
    
}
