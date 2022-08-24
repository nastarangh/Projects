
package com.wiley.ngflooring.service;

import com.wiley.ngflooring.dao.FlooringDao;
import com.wiley.ngflooring.dao.FlooringPersistenceException;
import com.wiley.ngflooring.dto.Order;
import com.wiley.ngflooring.dto.Product;
import com.wiley.ngflooring.dto.State;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author nasta
 */
public class FlooringServiceLayerImpl implements FlooringServiceLayer{

    FlooringDao dao;
    
    public FlooringServiceLayerImpl(FlooringDao dao){
        this.dao = dao;
    }
    
    @Override
    public void createOrder(LocalDate date, int orderNum, Order order) throws FlooringDataValidationException, FlooringPersistenceException {
        dao.addOrder(date, orderNum, order);
    }

    @Override
    public List<Order> getAllOrders(LocalDate date) throws FlooringPersistenceException {
        try{
            return dao.getAllOrders(date);
        } catch (FlooringPersistenceException e){
            throw new FlooringPersistenceException("There are no orders for this date.");
        }
    }

    @Override
    public Order getOrder(LocalDate date, int orderNum) throws FlooringPersistenceException {
        try{
            return dao.getOrder(date, orderNum);
        } catch (FlooringPersistenceException e){
            return null;
        }
    }

    public void editOrder(int orderNum, Order order, String name, String product, String state, String area) throws FlooringPersistenceException{
        dao.editOrder(orderNum, order, name, product, state, area);
    }
    
    @Override
    public void removeOrder(Order order, int orderNum) throws FlooringPersistenceException {
        dao.removeOrder(order, orderNum);
    }
      
    
    public List<Product> listProducts() {
        Map<String, Product> p=null;
        List<Product> productsList = null;
        try {
            p = dao.getProducts();
            productsList = new ArrayList(p.values());
        } catch (FlooringPersistenceException ex) {
            Logger.getLogger(FlooringServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return productsList;
    }
    
    public int getLastOrderNum() throws FlooringPersistenceException{
        return dao.getLastOrderNum();
    }
    
    public void setLastOrderNum(int num) throws FlooringPersistenceException{
        dao.setLastOrderNum(num);
    }
    
    //this is used for adding orders, making sure the date is a future date
    public boolean validateAddOrderDate(String date) throws FlooringDataValidationException {
        try {
            LocalDate today = LocalDate.now();
            LocalDate userDate = LocalDate.parse(date);
            boolean isfuture = today.isBefore(userDate);
            if(!isfuture){     
                throw new FlooringDataValidationException("Date must be in the future.");
            }
            return false;
        } catch (DateTimeParseException e) {
            throw new FlooringDataValidationException("Not a valid date format.");
        }
    }
    
    public boolean validateName(String name) throws FlooringDataValidationException {
        
        if(name==null || name.isEmpty()){
            
            throw new FlooringDataValidationException("Name cannot be blank.");
        }
        if(!name.matches("^[a-zA-Z0-9,. ]*$")){
            throw new FlooringDataValidationException("Name can only contain [a-zA-Z0-9] and Comma and Period.");
        }
        return false;
    }
    
    public boolean validateProduct(String productType) throws FlooringDataValidationException {
    
        Map<String, Product> products=null;
        try {
            products = dao.getProducts();
        } catch (FlooringPersistenceException ex) {
            throw new FlooringDataValidationException("Products could not be retrieved.");
        }
        Product p = products.get(productType);
        if(p==null){
            throw new FlooringDataValidationException("Uknown Product.");
        }
        return false;
    }
    
    public boolean validateState(String s) throws FlooringDataValidationException{
        
        Map<String, State> states=null;
        try {
            states = dao.getStates();
        } catch (FlooringPersistenceException ex) {
            throw new FlooringDataValidationException("States could not be retrieved.");
        }
        State state = states.get(s);
        if(state==null){
            throw new FlooringDataValidationException("Invalid State.");
        }
        return false;
    }
    
    public boolean validateArea(String area) throws FlooringDataValidationException {
        
        BigDecimal hundred = new BigDecimal("100");
        try {
            BigDecimal a = new BigDecimal(area);
            if(a.signum()<0){     
                throw new FlooringDataValidationException("Area must be a positive number.");
            }
            if(a.compareTo(hundred)<0){
                throw new FlooringDataValidationException("Area must be a greater than 100.");
            }
            return false;
        } catch (NumberFormatException e) {
            throw new FlooringDataValidationException("Not a valid value.");
        }
    }

    public Order calculateOrder(int ordernum, String date, String customerName, String pType, String stateAbr, String area) throws FlooringPersistenceException {
        return dao.calculateOrder(ordernum, date, customerName, pType, stateAbr, area);
    }
    
    //this is used for other date inputs to find order files
    public boolean validateDate(String date) throws FlooringDataValidationException {
        try {   
            LocalDate userDate = LocalDate.parse(date);
            return false;
        } catch (DateTimeParseException e) {
            throw new FlooringDataValidationException("Not a valid date format.");
        }
    }
    
    public void exportAllData() throws FlooringPersistenceException{
        dao.exportAllData();
    }
}
