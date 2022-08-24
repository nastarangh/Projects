/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.vendingmachine.dao;
import com.wiley.vendingmachine.dto.Change;
import java.math.BigDecimal;
import com.wiley.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author nastarangh
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao{
    
    private final String ITEM_FILE;
    public static final String DELIMITER = "::";
    private Map<String, Item> items = new HashMap<>();
    
    public VendingMachineDaoFileImpl(){
        ITEM_FILE = "items.txt";;
    }

    public VendingMachineDaoFileImpl(String vendingTextFile){
        ITEM_FILE = vendingTextFile;
    } 

    @Override
    public Item addItem(String name, Item item) throws VendingMachinePersistenceException {
        loadItems();
        Item newItem = items.put(name, item);
        writeItems();
        return newItem;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        loadItems();
        return new ArrayList(items.values());
    }

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {
        loadItems();
        return items.get(name);
    }

    @Override 
    public Item updateItemInventory(String name) throws VendingMachinePersistenceException {
        Item curr = getItem(name);
        curr.updateNum();
        writeItems();
        return curr;
    }
    
    @Override
    public List<Integer> calculateChange(BigDecimal amount){
        BigDecimal hundred = new BigDecimal("100");
        BigDecimal amountRounded = amount.setScale(2, RoundingMode.DOWN);
        int pennies = amountRounded.multiply(hundred).intValue();
        Change change = new Change(pennies);
        List<Integer> coins = new ArrayList<Integer>();
        
        coins.add(change.getQuarters());
        coins.add(change.getDimes());
        coins.add(change.getNickels());
        coins.add(change.getPennies());
        
        return coins;
    }
    
    
    private Item unmarshallItem(String itemAsText){
    // itemAsText is expecting a line read in from our file.
    // File format:
    // name::cost::inventory
    
        String[] itemTokens = itemAsText.split(DELIMITER);
        String itemName = itemTokens[0];
        String itemCost = itemTokens[1];
        String itemNum = itemTokens[2];
        BigDecimal cost = new BigDecimal(itemCost); 
        Item itemFromFile = new Item(itemName, cost , Integer.parseInt(itemNum));

        return itemFromFile;
    }
    
    private void loadItems() throws VendingMachinePersistenceException {
        
        Scanner scanner;

        try {
        // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ITEM_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "Could not load item data into memory.", e);
        }
    // currentLine holds the most recent line read from the file
        String currentLine;
        Item currentItem;
       
        while (scanner.hasNextLine()) { 
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            items.put(currentItem.getItemName(), currentItem);
        }
    
        scanner.close();
    }
    
    private String marshallItem(Item item){
    
        String itemAsText = item.getItemName() + DELIMITER;
        itemAsText += String.valueOf(item.getItemCost()) + DELIMITER;
        itemAsText += String.valueOf(item.getItemNum());

        return itemAsText;
    }
    
    private void writeItems() throws VendingMachinePersistenceException {
    
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ITEM_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save item data.", e);
        }

        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
        
            itemAsText = marshallItem(currentItem);
        
            out.println(itemAsText);
        
            out.flush();
        }
    // Clean up
    out.close();
    }
}
