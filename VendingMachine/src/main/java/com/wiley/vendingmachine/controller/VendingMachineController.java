/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.vendingmachine.controller;


import com.wiley.vendingmachine.dao.InsufficientFundsException;
import com.wiley.vendingmachine.dao.NoItemInventoryException;
import com.wiley.vendingmachine.dao.VendingMachinePersistenceException;
import com.wiley.vendingmachine.dto.Item;
import com.wiley.vendingmachine.service.VendingMachineServiceLayer;
import com.wiley.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nastarangh
 */
public class VendingMachineController {
    
    private VendingMachineView view;
    private VendingMachineServiceLayer service;
    
    public VendingMachineController(VendingMachineView aview, VendingMachineServiceLayer aservice){
        this.view = aview;
        this.service = aservice;
    
    }
    
    private String getMenuSelection() {
        return view.getSelection();
    }

  
    private void listItems() {
        List<Item> itemList;
        try {
            itemList = service.getAllItems();
            view.printItems(itemList);
        } catch (VendingMachinePersistenceException ex) {
            Logger.getLogger(VendingMachineController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    private BigDecimal getMoney(){
        return view.getMoney();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void notEnoughFunds() {
        view.displayNotEnoughFundsBanner();
    }
    
    private void notEnoughItems() {
        view.displayNotEnoughItemsBanner();
    } 
    
    private void exitMessage() {
        view.displayExitBanner();
    }
    
    private void exitWithoutPurchaseMessage(BigDecimal fund){
        view.displayExitWithoutPurchaseBanner(fund);
    }
    private void successMessage(){
        view.displayPurchaseSuccessBanner();
    }
    private void printChange(List<Integer> change){
        view.displayChangeBanner(change);
    }

    //modify to get item name and cash and figure out validation
    public void run() throws NoItemInventoryException,InsufficientFundsException {
        boolean keepGoing = true;
        String menuSelection = "";
        listItems();
        BigDecimal fund = getMoney();
        BigDecimal zero = new BigDecimal(0);
        while(fund.equals(zero)){
            fund = getMoney();
        }
        
        try {
            while (keepGoing) {
                menuSelection = getMenuSelection();
                Item i = service.getItem(menuSelection);
                if ((menuSelection.toLowerCase()).equals("exit")){
                    exitWithoutPurchaseMessage(fund);
                    keepGoing = false;
                }
                else if(i==null){
                    unknownCommand();
                }
                else{
                    try{
                        if(service.checkFunds(fund, menuSelection)){
                                if(service.updateItemInventory(menuSelection)){
                                    fund = fund.subtract(i.getItemCost());
                                    List<Integer> change = service.calculateChange(fund);
                                    successMessage();
                                    printChange(change);
                                    break;
                                }
                        }
                    } catch(NoItemInventoryException e){
                        notEnoughItems();
                    }
                    catch(InsufficientFundsException e){
                        notEnoughFunds();
                    } 
                }
            }
            
            exitMessage();
        }
            
        catch (VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }
}



