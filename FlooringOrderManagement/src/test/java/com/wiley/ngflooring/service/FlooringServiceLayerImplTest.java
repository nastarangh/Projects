/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.ngflooring.service;

import com.wiley.ngflooring.dao.FlooringPersistenceException;
import com.wiley.ngflooring.dto.Order;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author nasta
 */
public class FlooringServiceLayerImplTest extends TestCase {
    
    FlooringServiceLayer testService;
    
    public FlooringServiceLayerImplTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testService = ctx.getBean("serviceLayer", FlooringServiceLayerImpl.class);
    }
    
    @BeforeAll
    public void setUp() throws Exception {
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
    public void testgetAllOrders() {
        LocalDate date = LocalDate.parse("2014-01-01");
        try {
            List<Order> orders = testService.getAllOrders(date);
            fail("The exception for no orders existing was not thrown");
        } catch (FlooringPersistenceException e){
            return;
        }
        
    }
    
    @Test
    public void testGetOrder() throws FlooringPersistenceException {
        LocalDate date = LocalDate.parse("2014-01-01");
       
        Order order = testService.getOrder(date, 10);
       assertNull(order);
    }
    
    @Test
    public void testValidateAddOrderDate(){
        try{
            testService.validateAddOrderDate("2010-12-09");
            fail("The exception for date being in the past was not thrown.");
        } catch (FlooringDataValidationException e){
            return;
        }
    }
    
    @Test
    public void testValidateDate(){
        try{
            testService.validateDate("23-78-09");
            fail("The exception for invalid date format was not thrown");
        } catch (FlooringDataValidationException e){
            return;
        }  
    }
    
    @Test
    public void testValidateCustomerName(){
       try{
            testService.validateName("@Jame67");
            fail("The exception for invalid name format was not thrown");
        } catch (FlooringDataValidationException e){
            return;
        }   
    }
    
    @Test
    public void testValidateMinArea(){
        try{
            testService.validateArea("50");
            fail("The exception for minimum area being 100 was not thrown.");
        } catch (FlooringDataValidationException e){
            return;
        }  
    }
    
    @Test
    public void testValidateArea(){
        try{
            testService.validateArea("not a number");
            fail("The exception for invalid number was not thrown.");
        } catch (FlooringDataValidationException e){
            return;
        }  
    }
    
    @Test
    public void testValidateState(){
        try{
            testService.validateState("not a state");
            fail("The exception for invalid state was not thrown.");
        } catch (FlooringDataValidationException e){
            return;
        }  
    }
    
    @Test
    public void testValidateProduct(){
        try{
            testService.validateProduct("not a product");
            fail("The exception for invalid product was not thrown.");
        } catch (FlooringDataValidationException e){
            return;
        }  
    }
           
    
}
