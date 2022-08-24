/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.vendingmachine.dto;

/**
 *
 * @author nastarangh
 */

//Takes the amount of change due to the user (in pennies) and then calculates the number of quarters, 
//dimes, nickels, and pennies due back to the user.
public class Change {
    
    public enum Coin
    {
        PENNY(1),
        NICKEL(5),   
        DIME(10),
        QUARTER(25);
        private final int pennies;
        
        Coin(int pennies){
            this.pennies = pennies;
        }
    }
    
    private int total;
    private int pennies;
    private int nickels;
    private int dimes;
    private int quarters;
    
    public Change(int amount){
        this.total = amount;
        int i = amount;
        
        this.quarters = i/Coin.QUARTER.pennies;
        i = i - (this.quarters*Coin.QUARTER.pennies);
        
        this.dimes = i/Coin.DIME.pennies;
        i = i - (this.dimes*Coin.DIME.pennies);

        this.nickels = i/Coin.NICKEL.pennies;
        i = i - (this.nickels*Coin.NICKEL.pennies);
        
        this.pennies = i;
        
    }
    
    public int getQuarters(){
        return this.quarters;
    }
    
    public int getDimes(){
        return this.dimes;
    }
    
    public int getNickels(){
        return this.nickels;
    }
    
    public int getPennies(){
        return this.pennies;
    }
    
}
