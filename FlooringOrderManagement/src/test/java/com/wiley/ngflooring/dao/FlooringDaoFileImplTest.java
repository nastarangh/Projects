/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.ngflooring.dao;

import com.wiley.ngflooring.dto.Order;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 *
 * @author nasta
 */
public class FlooringDaoFileImplTest {
    
    FlooringDao testDao;
    
        
    public FlooringDaoFileImplTest() {
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("flooringDao", FlooringDaoFileImpl.class);    
    }
    
    @BeforeAll
    public void setUp() throws Exception{
    //this test requires the files in testData/Orders/ to be deleted before running.
    //this test requires the lastId.txt file in the testData folder to be set to 0 before running.
        File ordersDir = new File("testData/Orders"); 
        File filesList[] = ordersDir.listFiles();
        for(File file : filesList){
            if(file.isFile()){
                file.delete();
            }
        }    
        PrintWriter out = new PrintWriter(new FileWriter("testData/lastId.txt", false));
        out.println("0");
    }
    
    @Test
    public void testAddGetOrder()throws Exception {
        
        int ordernum1 = testDao.getLastOrderNum() + 1;
        LocalDate date1 = LocalDate.parse("2023-09-09");
        Order newOrder1 = testDao.calculateOrder(ordernum1, "2023-09-09", "James Web","Wood", "TX", "200");
        testDao.addOrder(date1, ordernum1, newOrder1);
        testDao.setLastOrderNum(ordernum1);
        
        Order retrievedOrder = testDao.getOrder(date1, ordernum1);
        assertEquals(newOrder1.getOrderNum(),
                retrievedOrder.getOrderNum(),
                "Checking order number.");
        assertEquals(newOrder1.getProductType(),
                retrievedOrder.getProductType(),
                "Checking product type.");
        assertEquals(newOrder1.getName(), 
                retrievedOrder.getName(),
                "Checking customer name.");
        assertEquals(newOrder1.getOrderDate(), 
                retrievedOrder.getOrderDate(),
                "Checking order date.");
        assertEquals(newOrder1.getTotal(), 
                retrievedOrder.getTotal(),
                "Checking order total.");
    }
    
    @Test
    public void testAddGetALLOrders()throws Exception {    
        
        int ordernum2 = testDao.getLastOrderNum() + 1;
        LocalDate date2 = LocalDate.parse("2023-06-09");
        Order newOrder2 = testDao.calculateOrder(ordernum2, "2023-06-09", "Software Guild", "Laminate", "CA", "400");
        testDao.addOrder(date2, ordernum2, newOrder2);
        testDao.setLastOrderNum(ordernum2);
        
        
        int ordernum3 = testDao.getLastOrderNum() + 1;
        Order newOrder3 = testDao.calculateOrder(ordernum3, "2023-06-09", "General Inc.", "Wood", "TX", "160");
        testDao.addOrder(date2, ordernum3, newOrder3);
        testDao.setLastOrderNum(ordernum3);
        
        
        int ordernum4 = testDao.getLastOrderNum() + 1; 
        Order newOrder4 = testDao.calculateOrder(ordernum4, "2023-06-09", "James Web","Wood", "TX", "200");
        testDao.addOrder(date2, ordernum4, newOrder4);
        testDao.setLastOrderNum(ordernum4);
        
        List<Order> allOrders = testDao.getAllOrders(date2);
        
        assertNotNull(allOrders, "The list of orders must not be null");
        

    
        assertTrue(allOrders.contains(newOrder2),
                "The list of orders should include fist order.");
        assertTrue(allOrders.contains(newOrder4),
            "The list of orders should include third order.");
    }
    
    @Test
    public void testAddExportAllOrders()throws Exception {
        
        int ordernum5 = testDao.getLastOrderNum() + 1;
        LocalDate date5 = LocalDate.parse("2023-07-09");
        Order newOrder5 = testDao.calculateOrder(ordernum5, "2023-07-09", "Dylan Brown", "Tile", "TX", "300");
        testDao.addOrder(date5, ordernum5, newOrder5);
        testDao.setLastOrderNum(ordernum5);
        
        
        int ordernum6 = testDao.getLastOrderNum() + 1;
        Order newOrder6 = testDao.calculateOrder(ordernum6, "2023-07-09", "James Web","Laminate", "TX", "267");
        testDao.addOrder(date5, ordernum6, newOrder6);
        testDao.setLastOrderNum(ordernum6);
        
        
        int ordernum7 = testDao.getLastOrderNum() + 1; 
        Order newOrder7 = testDao.calculateOrder(ordernum7, "2023-07-09", "Simin Web","Wood", "TX", "139");
        testDao.addOrder(date5, ordernum7, newOrder7);
        testDao.setLastOrderNum(ordernum7);
        
        testDao.exportAllData();
        return;
    }
    
    @Test
    public void testAddEditOrder()throws Exception { 
       
       int ordernum8 = testDao.getLastOrderNum() + 1;
       LocalDate date8 = LocalDate.parse("2024-01-01");
       Order newOrder8 = testDao.calculateOrder(ordernum8, "2024-01-01", "Natalie Web","Wood", "TX", "120");
       testDao.addOrder(date8, ordernum8, newOrder8);
       testDao.setLastOrderNum(ordernum8);
       
       BigDecimal newArea = new BigDecimal("180");
       
       testDao.editOrder(ordernum8, newOrder8, "James Byers", "Carpet", "TX", "180");
       assertEquals(newArea, newOrder8.getArea(), "the order area should be 180 after the edit.");
       assertEquals("James Byers", newOrder8.getName(), "the order name should be James Byesr after the edit.");
       assertEquals("Carpet", newOrder8.getProductType(), "the order product type should be Carpet after the edit.");
    }
    
    @Test
    public void testAddRemoveOrder()throws Exception { 
       
       int ordernum9 = testDao.getLastOrderNum() + 1;
       LocalDate date9 = LocalDate.parse("2024-02-01");
       Order newOrder9 = testDao.calculateOrder(ordernum9, "2024-02-01", "John Doe", "Carpet", "TX", "120");
       testDao.addOrder(date9, ordernum9, newOrder9);
       testDao.setLastOrderNum(ordernum9);

       testDao.removeOrder(newOrder9, ordernum9);
       
       try{
            Order removedOrder = testDao.getOrder(date9, ordernum9);
       } catch (Exception e) {
           return;
       }
          
    }
    
    
}
