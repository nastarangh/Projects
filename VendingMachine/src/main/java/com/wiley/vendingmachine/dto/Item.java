/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author nastarangh
 */
public class Item {
    
    private String itemName;
    private BigDecimal itemCost;
    private int itemNum;
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.itemName);
        hash = 53 * hash + Objects.hashCode(this.itemCost);
        hash = 53 * hash + this.itemNum;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.itemNum != other.itemNum) {
            return false;
        }
        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }
        if (!Objects.equals(this.itemCost, other.itemCost)) {
            return false;
        }
        return true;
    }
    
    
    public Item(String name, BigDecimal cost, int num){
        this.itemName = name;
        this.itemCost = cost;
        this.itemNum = num;
    }
    
    public String getItemName(){
        return this.itemName;
    }
    
    public BigDecimal getItemCost(){
        return this.itemCost;
    }
    
    public int getItemNum(){
        return this.itemNum;
    }
    
    public void setItemNum(int num){
        this.itemNum = num;
    }
    
    public void updateNum(){
        this.itemNum--;
    }
    
    @Override
    public String toString() {
        return "Item{" + "Name=" + itemName + ", Price=" + itemCost + ", Inventory=" + itemNum + '}';
    }
}
