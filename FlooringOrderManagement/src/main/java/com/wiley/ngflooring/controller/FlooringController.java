
package com.wiley.ngflooring.controller;

import com.wiley.ngflooring.dao.FlooringPersistenceException;
import com.wiley.ngflooring.dto.Order;
import com.wiley.ngflooring.dto.Product;
import com.wiley.ngflooring.service.FlooringDataValidationException;
import com.wiley.ngflooring.service.FlooringServiceLayer;
import com.wiley.ngflooring.ui.FlooringView;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author nasta
 */
public class FlooringController {
    private FlooringView view;
    private FlooringServiceLayer service;
    
    
    public FlooringController(FlooringServiceLayer service, FlooringView view) {
        this.service = service;
        this.view = view;
    }
    public void run() { 
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            
            while (keepGoing) {
                menuSelection = getMenuSelection();
                switch (menuSelection) {
                    case 1:    
                        listOrders();
                        break;
                    case 2:
                        createOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4: 
                        removeOrder();
                        break;
                    case 5:
                        exportData();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        view.displayUnknownCommandBanner();
                }
            }
            //quit
            view.displayExitMessage();
        } catch (FlooringDataValidationException|FlooringPersistenceException e){
            view.displayErrorMessage(e.getMessage());
        }  
    }
    
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    
    private void createOrder() throws FlooringPersistenceException, FlooringDataValidationException {
        String date = "";
        String name = "";
        String state = "";
        String product = "";
        String area = "";
        
        boolean hasErrors = true;
        do {
            date = view.getDateInfo();
            try {
                hasErrors = service.validateAddOrderDate(date);
            } catch (FlooringDataValidationException e){
                view.displayErrorMessage(e.getMessage());  
            }
        } while (hasErrors);
        
        hasErrors = true;
        do {
            name = view.getNameInfo();
            try {
                hasErrors = service.validateName(name);
            } catch (FlooringDataValidationException e){
                view.displayErrorMessage(e.getMessage());  
            }
        } while (hasErrors);
      
        hasErrors = true;
        do {
            List<Product> p = service.listProducts();
            product = view.getProductInfo(p);
            try {
                hasErrors = service.validateProduct(product);
            } catch (FlooringDataValidationException e){
                view.displayErrorMessage(e.getMessage());  
            }
        } while (hasErrors);
        
        hasErrors = true;
        do {   
            state = view.getStateInfo();
            try {
                hasErrors = service.validateState(state);
            } catch (FlooringDataValidationException e){
                view.displayErrorMessage(e.getMessage());  
            }
        } while (hasErrors);
        
        hasErrors = true;
        do {
            area = view.getAreaInfo();
            try {
                hasErrors = service.validateArea(area);
            } catch (FlooringDataValidationException e){
                view.displayErrorMessage(e.getMessage());  
            }
        } while (hasErrors);
        
        
        try {
            int ordernum = service.getLastOrderNum() + 1;
            Order order = service.calculateOrder(ordernum, date, name, product, state, area);
            String ans = view.printOrderAndGetConfirmation(order);
            
            if (ans.equals("Y") || ans.equals("y")){
                LocalDate d = LocalDate.parse(date);
                service.createOrder(d, ordernum, order);
                service.setLastOrderNum(ordernum);
                view.displaySuccessfulAddBanner();
            }
            else if (ans.equals("N") || ans.equals("n")){
                order = null;
            }
        } catch (FlooringPersistenceException e) {
                view.displayErrorMessage(e.getMessage());
        }        

    }
    
    private void listOrders() throws FlooringPersistenceException {
        String date = "";
        
        boolean hasErrors = true;
        do {
            date = view.getDateInfo();
            try {
                hasErrors = service.validateDate(date);
            } catch (FlooringDataValidationException e){
                view.displayErrorMessage(e.getMessage());  
            }
        } while (hasErrors);
        
        try{
            List<Order> orderList = service.getAllOrders(LocalDate.parse(date));
            view.displayOrderList(orderList);
        } catch (FlooringPersistenceException e){
            view.displayErrorMessage(e.getMessage());
        }
    }
    
    private void editOrder() throws FlooringPersistenceException{
        String date = "";
        String newName = "";
        String newState = "";
        String newProduct = "";
        String newArea = "";
        
        boolean hasErrors = true;
        do {
            date = view.getDateInfo();
            try {
                hasErrors = service.validateDate(date);
            } catch (FlooringDataValidationException e){
                view.displayErrorMessage(e.getMessage());  
            }
        } while (hasErrors);
        int orderNum = view.getOrderNumInfo();
        Order orderToEdit = service.getOrder(LocalDate.parse(date), orderNum);
        
        if(orderToEdit == null){
            view.displayErrorMessage("Order does not exist.");
        }
        else{
            hasErrors = true;
            do {
                newName = view.editNameInfo(orderToEdit.getName());
                if(newName!=null && !newName.isEmpty()){
                    try {
                        hasErrors = service.validateName(newName);
                    } catch (FlooringDataValidationException e){
                        view.displayErrorMessage(e.getMessage());  
                    }
                }
                else {
                    newName = orderToEdit.getName();
                    hasErrors = false;
                }
            } while (hasErrors);

            hasErrors = true;
            do {
                newProduct = view.editProductInfo(orderToEdit.getProductType());
                if(newProduct!=null && !newProduct.isEmpty()){
                    try {
                       hasErrors = service.validateProduct(newProduct);
                    } catch (FlooringDataValidationException e){
                        view.displayErrorMessage(e.getMessage());  
                    }
                }
                else {
                    newProduct = orderToEdit.getProductType();
                    hasErrors = false;
                }
            } while (hasErrors);
        
            
            hasErrors = true;
            do {   
                newState = view.editStateInfo(orderToEdit.getStateName());
                if(newState!=null && !newState.isEmpty()){
                    try {
                        hasErrors = service.validateState(newState);
                    } catch (FlooringDataValidationException e){
                        view.displayErrorMessage(e.getMessage());  
                    }
                }
                else {
                    newState = orderToEdit.getStateName();
                    hasErrors = false;
                }
            } while (hasErrors);

            hasErrors = true;
            do {
                newArea = view.editAreaInfo(orderToEdit.getArea());
                if(newArea!=null && !newArea.isEmpty() && !newArea.equals("")){
                    try {
                        hasErrors = service.validateArea(newArea);
                    } catch (FlooringDataValidationException e){
                        view.displayErrorMessage(e.getMessage());  
                    }
                }
                else {
                    newArea = orderToEdit.getArea().toString();
                    hasErrors = false;
                }
            } while (hasErrors);
        
            service.editOrder(orderNum, orderToEdit, newName, newProduct, newState, newArea);
            view.displaySuccessfulEditBanner();
        }
    
    }
    private void removeOrder() throws FlooringPersistenceException {
        String date;
        
        boolean hasErrors = true;
        do {
            date = view.getDateInfo();
            try {
                hasErrors = service.validateDate(date);
            } catch (FlooringDataValidationException e){
                view.displayErrorMessage(e.getMessage());  
            }
        } while (hasErrors);
        int orderNum = view.getOrderNumInfo();
        Order orderToRemove = service.getOrder(LocalDate.parse(date), orderNum);
        
        if(orderToRemove == null){
            view.displayErrorMessage("Order does not exist.");
        }
        else {
            String ans = view.printOrderAndGetConfirmation(orderToRemove);
            if (ans.equals("Y") || ans.equals("y")){
                service.removeOrder(orderToRemove,orderNum);
                view.displaySuccessfulRemoveBanner();
            }  
        }  
    }
    
    private void exportData() throws FlooringPersistenceException{
        service.exportAllData();
        view.displaySuccessfulExportBanner();
    }
}

