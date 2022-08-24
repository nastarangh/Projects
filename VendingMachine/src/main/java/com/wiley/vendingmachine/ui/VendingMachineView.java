/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.vendingmachine.ui;

import com.wiley.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author nastarangh
 */
public class VendingMachineView {
    
    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }
    
    public void printItems(List<Item> list) {
        //header
        io.print(String.format("%15s%15s%15s", "Name", "Price($)", "Inventory"));
        
        //lambda expression to print item menu
        Consumer<Item> method = (n) -> {io.print(String.format("%15s%15s%15s",
                                        n.getItemName(),
                                        n.getItemCost(),
                                        n.getItemNum()));};
        list.forEach(method);
        
    }
    
    public BigDecimal getMoney(){
        String m = io.readString("Please insert money in Dollars: ");
        BigDecimal money=new BigDecimal("0");
        
        try{
             money = new BigDecimal(m);
            
        } catch(NumberFormatException e){
            io.print("Invalid currency format.");
        }
        return money;
    }
    
    public String getSelection(){
        return io.readString("Select the item you wish to purchase by entering its name from the Menu above or type exit to exit the program");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }
    
    public void displayExitWithoutPurchaseBanner(BigDecimal fund){
        io.print("You receive $" + fund + " back.");
    }

    public void displayNotEnoughFundsBanner(){
        io.print("Your inserted funds are less that this item's price.");
    }
    public void displayNotEnoughItemsBanner(){
        io.print("Item out of stock.");
    }
    public void displayUnknownCommandBanner() {
        io.print("Unknown Item!!!");
    }
    public void displayPurchaseSuccessBanner(){
        io.print("Purchase Successful!!!");
    }
    public void displayChangeBanner(List<Integer> change){
        io.print("Change received: " + change.get(0) + " Quarters, " + change.get(1) + " Dimes, " + change.get(2) + " Nickels, and " + change.get(3) + " Pennies.");
    }
}
