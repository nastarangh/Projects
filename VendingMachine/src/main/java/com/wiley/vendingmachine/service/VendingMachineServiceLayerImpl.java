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
import com.wiley.vendingmachine.dao.VendingMachinePersistenceException;
import com.wiley.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author nastarangh
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {
    
    VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;
    
    public VendingMachineServiceLayerImpl(VendingMachineDao aDao, VendingMachineAuditDao auditDao){
        this.dao = aDao;
        this.auditDao = auditDao;
    }    

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return dao.getAllItems();
    }

    @Override
    public Item getItem(String name)throws VendingMachinePersistenceException{
        return dao.getItem(name);
    }

    @Override
    public boolean updateItemInventory(String name) throws VendingMachinePersistenceException, NoItemInventoryException {
        Item i = getItem(name);
        if (i.getItemNum() > 0){
            dao.updateItemInventory(name);
            auditDao.writeAuditEntry("Item " + name + " Purchased.");
            return true;
        }
        else{
            throw new NoItemInventoryException("Item out of stock");
        }
    }
    
    @Override
    public boolean checkFunds(BigDecimal fund, String itemName) throws VendingMachinePersistenceException, InsufficientFundsException{
        Item i = getItem(itemName);
        BigDecimal price = i.getItemCost();
        
        if(fund.compareTo(price)<0){
            throw new InsufficientFundsException("Insufficient funds");
        }
        else{
            return true;
        }
        
    }

    @Override
    public List<Integer> calculateChange(BigDecimal amount) throws VendingMachinePersistenceException {
        auditDao.writeAuditEntry("The amount of change due to the user was calculated and returned.");
        return dao.calculateChange(amount);
    }
    
    
}
