
package com.wiley.ngflooring.service;

import com.wiley.ngflooring.dao.FlooringPersistenceException;
import com.wiley.ngflooring.dto.Order;
import com.wiley.ngflooring.dto.Product;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author nasta
 */
public interface FlooringServiceLayer {
    
    void createOrder(LocalDate date, int orderNum, Order order) throws FlooringDataValidationException,FlooringPersistenceException;
 
    List<Order> getAllOrders(LocalDate date) throws FlooringPersistenceException;
 
    Order getOrder(LocalDate date, int orderNum) throws FlooringPersistenceException;
 
    void removeOrder(Order order, int orderNum) throws FlooringPersistenceException;

    List<Product> listProducts() throws FlooringDataValidationException;
    
    boolean validateAddOrderDate(String date) throws FlooringDataValidationException;
    
    boolean validateDate(String date) throws FlooringDataValidationException;
    
    boolean validateName(String name) throws FlooringDataValidationException;
    
    boolean validateProduct(String productType) throws FlooringDataValidationException;
    
    boolean validateState(String s) throws FlooringDataValidationException;
    
    boolean validateArea(String area) throws FlooringDataValidationException;
    
    Order calculateOrder(int ordernum, String date, String customerName, String pType, String stateAbr, String area)throws FlooringPersistenceException;
    
    void editOrder(int ordernum, Order order, String name, String product, String state, String area) throws FlooringPersistenceException;
    
    int getLastOrderNum() throws FlooringPersistenceException;
    
    void setLastOrderNum(int num) throws FlooringPersistenceException;
    
    void exportAllData() throws FlooringPersistenceException;
}
