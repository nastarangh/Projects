

package com.wiley.ngflooring.dao;

import com.wiley.ngflooring.dto.Order;
import com.wiley.ngflooring.dto.Product;
import com.wiley.ngflooring.dto.State;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ng
 */
public class FlooringDaoFileImpl implements FlooringDao{
    
    private Map<Integer, Order> orders = new HashMap<>();
    private Map<String, Product> products = new HashMap<>();
    private Map<String, State> states = new HashMap<>();
    public final String PRODUCT_FILE ;
    public final String TAX_FILE ;
    public final String ID_FILE; //file to keep track of the last order number added.
    public final String EXPORT_FILE;
    public final String ORDERS_PATH;
    public final String DELIMITER = ",";
    
    public FlooringDaoFileImpl() {
        PRODUCT_FILE = "Data/Products.txt";
        TAX_FILE = "Data/Taxes.txt";
        ID_FILE = "LastOrderNum.txt";
        EXPORT_FILE = "Backup/DataExport.txt";
        ORDERS_PATH = "Orders/";
        try {
            loadProducts();
            loadStates();
        } catch (FlooringPersistenceException ex) {
            Logger.getLogger(FlooringDaoFileImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public FlooringDaoFileImpl(String ordersPath, String productFile, String taxFile, String idFile, String exportFile){
        PRODUCT_FILE = productFile;
        TAX_FILE = taxFile;
        ID_FILE = idFile;
        EXPORT_FILE = exportFile;  
        ORDERS_PATH = ordersPath;
        try {
            loadProducts();
            loadStates();
        } catch (FlooringPersistenceException ex) {
            Logger.getLogger(FlooringDaoFileImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public Order addOrder(LocalDate date, int orderNum, Order order) throws FlooringPersistenceException {
        try {
            writeOrder(date, order);
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not add order to the order file.");
        }
        return order;
    }
    
    @Override //this method calculates the order fields based on product type and state
    public Order calculateOrder(int ordernum, String date, String customerName, String pType, String stateAbr, String area) throws FlooringPersistenceException {
        states = getStates();
        products = getProducts();
        Product product = products.get(pType);
        State state = states.get(stateAbr);
        LocalDate d = LocalDate.parse(date);
        BigDecimal a = new BigDecimal(area);
        Order temp = new Order(ordernum, customerName, stateAbr, pType, a);
        temp.setOrderDate(d);
        temp.setCostPerSquareFoot(product.getCostPerSqrFoot());
        temp.setLaborCostPerSquareFoot(product.getLaborCostPerSqrFoot());
        temp.setTaxRate(state.getTaxRate());
        temp.calculateOrder();
        return temp;
    }
    
    @Override 
    public List<Order> getAllOrders(LocalDate date) throws FlooringPersistenceException {
        try {
            loadOrders(date);
            return new ArrayList(orders.values());
        } catch(FlooringPersistenceException e){
            throw new FlooringPersistenceException("File not found.");
        }
    }

    @Override
    public Order getOrder(LocalDate date, int orderNum) throws FlooringPersistenceException {
        try {
            loadOrders(date);
            return orders.get(orderNum);
        } catch (FlooringPersistenceException e){
            throw new FlooringPersistenceException("Order does not exist");
        }
    }
    
    @Override //loading orders to hashmap, editing the order, then writing everything back to the order file
    public void editOrder(int orderNum, Order order, String name, String product, String state, String area) throws FlooringPersistenceException{
        loadOrders(order.getOrderDate());
        Order orderToEdit = orders.get(orderNum);
        BigDecimal a = new BigDecimal(area);
        orderToEdit.setName(name);
        orderToEdit.setStateName(state);
        orderToEdit.setProductType(product);
        orderToEdit.setArea(a);
        Product p = products.get(product);
        State s = states.get(state);
        orderToEdit.setCostPerSquareFoot(p.getCostPerSqrFoot());
        orderToEdit.setLaborCostPerSquareFoot(p.getLaborCostPerSqrFoot());
        orderToEdit.setTaxRate(s.getTaxRate());
        orderToEdit.calculateOrder();
        
        try {
            writeAllOrderstoFile(orderToEdit.getOrderDate());
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not write orders to the file.");
        }
    }
    
    @Override
    public void removeOrder(Order order, int orderNum) throws FlooringPersistenceException {
        LocalDate date = order.getOrderDate();
        loadOrders(date);
        orders.remove(orderNum, order);    
        try {
            if (!orders.isEmpty()) {
                writeAllOrderstoFile(date);
            }
            else {
                deleteOrderFile(date);
            }
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not write orders to the file.");
        }
    }
    
    @Override
    public Map<String, Product> getProducts()throws FlooringPersistenceException{
        try {
            loadProducts();
        } catch (FlooringPersistenceException e) {
            throw new FlooringPersistenceException("Could not load Products.");
        }
        
        return this.products;
    }
    
    @Override
    public Map<String, State> getStates() throws FlooringPersistenceException{
        try {
            loadStates();
        } catch (FlooringPersistenceException e) {
            throw new FlooringPersistenceException("Could not load States.");
        }
        return this.states;
    }
    
    private void deleteOrderFile(LocalDate date){ //method for deleting the order file if no order exists for that date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String stringDate = date.format(formatter);
        String fileName = ORDERS_PATH + "Orders_" + stringDate + ".txt";
        File myFile = new File(fileName);
        myFile.delete();
    }
    
    private String marshallOrder(Order order){
        
        String orderAsText = order.getOrderNum() + DELIMITER;

        orderAsText += order.getName() + DELIMITER;
        orderAsText += order.getStateName() + DELIMITER;
        orderAsText += order.getTaxRate() + DELIMITER;
        orderAsText += order.getProductType() + DELIMITER;
        orderAsText += order.getArea() + DELIMITER;
        orderAsText += order.getCostPerSquareFoot() + DELIMITER;
        orderAsText += order.getLaborCostPerSquareFoot() + DELIMITER;
        orderAsText += order.getMaterialCost() + DELIMITER;
        orderAsText += order.getLaborCost() + DELIMITER;
        orderAsText += order.getTax() + DELIMITER;
        orderAsText += order.getTotal();
        
        return orderAsText;
    }
    
    private Order unmarshallOrder(String orderAsText){
    
        String[] orderTokens = orderAsText.split(DELIMITER);
        int orderNum = Integer.parseInt(orderTokens[0]);
        String customerName = orderTokens[1];
        String stateAbr = orderTokens[2];
        BigDecimal taxRate = new BigDecimal(orderTokens[3]);
        String productType = orderTokens[4];
        BigDecimal area = new BigDecimal(orderTokens[5]);
        BigDecimal costPerSqrFoot = new BigDecimal(orderTokens[6]);
        BigDecimal laborCostPerSqrFoot = new BigDecimal(orderTokens[7]);
        BigDecimal materialCost = new BigDecimal(orderTokens[8]);
        BigDecimal laborCost = new BigDecimal(orderTokens[9]);
        BigDecimal tax = new BigDecimal(orderTokens[10]);
        BigDecimal total = new BigDecimal(orderTokens[11]);    
        Order orderFromFile = new Order(orderNum, customerName, stateAbr, productType, area);  
        orderFromFile.setTaxRate(taxRate);
        orderFromFile.setCostPerSquareFoot(costPerSqrFoot);
        orderFromFile.setLaborCostPerSquareFoot(laborCostPerSqrFoot);
        orderFromFile.setMaterialCost(materialCost);
        orderFromFile.setTax(tax);
        orderFromFile.setLaborCost(laborCost);
        orderFromFile.setTotal(total);
        
        return orderFromFile;
    }
    
    
    private Product unmarshallProduct(String productAsText){
        String[] productTokens = productAsText.split(DELIMITER);
        String productType = productTokens[0];
        BigDecimal costPerSqrFoot = new BigDecimal(productTokens[1]);
        BigDecimal laborCostPerSqrFoot = new BigDecimal(productTokens[2]);
    
        Product productFromFile = new Product(productType,costPerSqrFoot,laborCostPerSqrFoot);
        return productFromFile;
    }
  
    private State unmarshallState(String stateAsText){
        String[] stateTokens = stateAsText.split(DELIMITER);
        String stateAbr = stateTokens[0];
        String stateName = stateTokens[1];
        BigDecimal taxRate = new BigDecimal(stateTokens[2]);
        State stateFromFile = new State(stateAbr, stateName, taxRate);
        return stateFromFile;
    }
    
    @Override
    public int getLastOrderNum() throws FlooringPersistenceException{//method to read the last order number from the file
        Scanner scanner;
        String currentLine;
        try {        
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ID_FILE)));
            currentLine = scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "Unable to read last order number.", e);
        }         
        try {
            return Integer.parseInt(currentLine);
        } catch (NumberFormatException e){
            throw new FlooringPersistenceException("Unable to parse integer in the last order file.");
        }
    }
    
    @Override
    public void setLastOrderNum(int num) throws FlooringPersistenceException{//writing the last order number added to the file 
        try {
            PrintWriter out = new PrintWriter(new FileWriter(ID_FILE, false));
            out.write(String.valueOf(num));
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }  
    }
    
    private void loadOrders(LocalDate date) throws FlooringPersistenceException {//loading orders into the orders hashmap
        orders.clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String stringDate = date.format(formatter);
        String fileName = ORDERS_PATH + "Orders_" + stringDate + ".txt";
        Scanner scanner;
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "Could not load order data into memory.", e);
        }        
        String currentLine;        
        Order currentOrder;
        currentLine = scanner.nextLine();
        while (scanner.hasNextLine()) {            
            currentLine = scanner.nextLine();   
            currentOrder = unmarshallOrder(currentLine);
            currentOrder.setOrderDate(date);
            orders.put(currentOrder.getOrderNum(), currentOrder);
        }        
        scanner.close();
    }
    
    
    private void writeOrder(LocalDate date, Order order) throws FlooringPersistenceException, IOException {//append one order to the file
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String stringDate = date.format(formatter);
        String fileName = ORDERS_PATH + "Orders_" + stringDate + ".txt";
        String header = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
        PrintWriter out = null;
        String orderAsText = marshallOrder(order);
        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                out = new PrintWriter(new FileWriter(fileName));
                out.println(header);
            } else { 
                out = new PrintWriter(new FileWriter(fileName, true));
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        out.append(orderAsText);
        out.append(System.lineSeparator());
        out.flush();
        out.close();
    }
    
    private void writeAllOrderstoFile(LocalDate date) throws FlooringPersistenceException, IOException {//overwriting all orders to a file    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String stringDate = date.format(formatter);
        String fileName = ORDERS_PATH + "Orders_" + stringDate + ".txt";
        String header = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
        PrintWriter out = null;

        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                out = new PrintWriter(new FileWriter(fileName, false));
                out.println(header);
            } else {
                out = new PrintWriter(new FileWriter(fileName, false));
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        out.println(header);
        for (Order order : orders.values()) {
            String orderAsText = marshallOrder(order);
            out.println(orderAsText);
            out.flush();
        }
        
        out.close();
    }
    
    
    private void loadProducts() throws FlooringPersistenceException { //loading products into the hashmap
        Scanner scanner;
        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "Could not load product data into memory.", e);
        }        
        String currentLine;        
        Product currentProduct;
        currentLine = scanner.nextLine();
        while (scanner.hasNextLine()) {            
            currentLine = scanner.nextLine();   
            currentProduct = unmarshallProduct(currentLine);
            products.put(currentProduct.getProductType(), currentProduct);
        }        
        scanner.close();
    }
    
   
    private void loadStates() throws FlooringPersistenceException {//loading the states and tax info into the hashmap
        Scanner scanner;
        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "Could not load product data into memory.", e);
        }       
        String currentLine;
        State currentState;
        //skip the header
        currentLine = scanner.nextLine(); 
        while (scanner.hasNextLine()) {            
            currentLine = scanner.nextLine();            
            currentState = unmarshallState(currentLine); 
            states.put(currentState.getStateAbr(), currentState);
        }
        // close scanner
        scanner.close();
    }
    
    @Override
    public void exportAllData() throws FlooringPersistenceException { //going through all the order files and write them to the export file
        Scanner scanner;
        File folder = new File(ORDERS_PATH);
        File[] listOfFiles = folder.listFiles();
        String name = "";
        String date = "";
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(EXPORT_FILE, false));
        } catch (IOException e) {
            throw new FlooringPersistenceException(
                    "Could not find the export file.", e);
        }
        
        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate");
        for (File file : listOfFiles) {
            if (file.isFile()) {
                name = file.getName();
                String[] filename = name.split("\\."); //get rid of .txt
                String[] parts = filename[0].split("_");
                date = parts[1];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
                LocalDate dateObj = LocalDate.parse(date, formatter);
                String formatted_date = dateObj.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
                
                try {
                    scanner = new Scanner(new BufferedReader(new FileReader(ORDERS_PATH+name)));
                } catch (FileNotFoundException e) {
                    throw new FlooringPersistenceException(
                    "Could not load data into memory.", e);
                }
                String currentLine;
                //skip the header
                currentLine = scanner.nextLine(); 
                while (scanner.hasNextLine()) {            
                    currentLine = scanner.nextLine();
                    out.println(currentLine +","+ formatted_date);
                    out.flush();
                }
                scanner.close();   
            }
        }
    }
}

