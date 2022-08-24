
package com.wiley.ngflooring.dao;

import com.wiley.ngflooring.dto.Order;
import com.wiley.ngflooring.dto.Product;
import com.wiley.ngflooring.dto.State;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nasta
 */
public interface FlooringDao {
    Order addOrder(LocalDate date, int orderNum, Order order) throws FlooringPersistenceException;
    
    Order calculateOrder(int ordernum, String date, String customerName, String pType, String stateAbr, String area) throws FlooringPersistenceException;
    
    List<Order> getAllOrders(LocalDate date) throws FlooringPersistenceException;
    
    Order getOrder(LocalDate date, int orderNum) throws FlooringPersistenceException;
    
    void editOrder(int ordernum, Order order, String name, String product, String state, String area) throws FlooringPersistenceException;
    
    void removeOrder(Order order, int orderNum) throws FlooringPersistenceException;
    
    Map<String, Product> getProducts()throws FlooringPersistenceException;
    
    Map<String, State> getStates()throws FlooringPersistenceException;
    
    int getLastOrderNum() throws FlooringPersistenceException;
    
    void setLastOrderNum(int num) throws FlooringPersistenceException;
    
    void exportAllData() throws FlooringPersistenceException;
}

