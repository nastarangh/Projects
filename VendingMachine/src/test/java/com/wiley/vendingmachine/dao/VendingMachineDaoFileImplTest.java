/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.vendingmachine.dao;

import com.wiley.vendingmachine.dto.Item;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author nastarangh
 */

public class VendingMachineDaoFileImplTest {
    
    VendingMachineDao testDao;
    
    public VendingMachineDaoFileImplTest() {
    }
   
    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testItems.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new VendingMachineDaoFileImpl(testFile);  
    }
    
  
    @Test
    public void testAddGetItem()throws Exception {
        BigDecimal cost = new BigDecimal("2.75");
        Item item = new Item("chips", cost, 65);
        
        testDao.addItem("chips", item);
        
        Item retrievedItem = testDao.getItem("chips");
        
        assertEquals(item.getItemName(),
                retrievedItem.getItemName(),
                "Checking student id.");
        assertEquals(item.getItemCost(),
                retrievedItem.getItemCost(),
                "Checking student first name.");
        assertEquals(item.getItemNum(), 
                retrievedItem.getItemNum(),
                "Checking student last name.");
    }
    
    @Test
    public void testAddGetALLItems()throws Exception {
        BigDecimal cost1 = new BigDecimal("2.75");
        Item item1 = new Item("chocolate", cost1, 65);
        
        BigDecimal cost2 = new BigDecimal("1.25");
        Item item2 = new Item("candy", cost2, 65);
        
        BigDecimal cost3 = new BigDecimal("1.75");
        Item item3 = new Item("chips", cost3, 65);
        
        BigDecimal cost4 = new BigDecimal("0.75");
        Item item4 = new Item("jelly", cost4, 65);
        
        BigDecimal cost5 = new BigDecimal("3.75");
        Item item5 = new Item("m&m", cost5, 65);
        
        testDao.addItem("chocolate", item1);
        testDao.addItem("candy", item2);
        testDao.addItem("chips", item3);
        testDao.addItem("jelly", item4);
        testDao.addItem("m&m", item5);
        
        List<Item> allItems = testDao.getAllItems();
        
        assertNotNull(allItems, "The list of items must not null");
        assertEquals(5, allItems.size(),"List of students should have 5 items.");

    
        assertTrue(testDao.getAllItems().contains(item2),
                "The list of items should include candy.");
        assertTrue(testDao.getAllItems().contains(item5),
            "The list of items should include m&m.");
    }
    
    @Test
    public void testCalculateChange()throws Exception {
        BigDecimal money1 = new BigDecimal("1.75");
        BigDecimal money2 = new BigDecimal("0.10");
        BigDecimal money3 = new BigDecimal("2.15");
        
        List<Integer> change1 = testDao.calculateChange(money1);
        List<Integer> change2 = testDao.calculateChange(money2);
        List<Integer> change3 = testDao.calculateChange(money3);
        
        assertEquals(7, change1.get(0),"First member of the change list should have the number of quarters which in this case should be 7.");
        assertEquals(1, change2.get(1),"Second member of the change list should have the number of dimes which in this case should be 1.");
        assertEquals(1, change3.get(2),"Third member of the change list should have the number of nickels which in this case should be 1.");
    }
    
    @Test
    public void testUpdateInventory()throws Exception {
        
        BigDecimal cost1 = new BigDecimal("2.75");
        Item item1 = new Item("chocolate", cost1, 20);
        
        BigDecimal cost2 = new BigDecimal("1.25");
        Item item2 = new Item("candy", cost2, 12);
        
        BigDecimal cost3 = new BigDecimal("1.75");
        Item item3 = new Item("chips", cost3, 65);
        
        testDao.addItem("chocolate", item1);
        testDao.addItem("candy", item2);
        testDao.addItem("chips", item3);
        
        //lets say customer has bought an item and now we want to update its inventory
        testDao.updateItemInventory("candy");
        
        List<Item> allItems = testDao.getAllItems();
        
        assertEquals(11, allItems.get(1).getItemNum(),"the item candy should have 11 inventory after purchase.");
    }
}
