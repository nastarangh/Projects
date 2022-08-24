
package com.wiley.ngflooring.ui;

import com.wiley.ngflooring.dto.Order;
import com.wiley.ngflooring.dto.Product;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author nasta
 */
public class FlooringView {
    
    private UserIO io;

    public FlooringView(UserIO io){
        this.io = io;
    }
    
    public int printMenuAndGetSelection() {
        io.print("********************************");
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");
        io.print("********************************");

        return io.readInt("Please select from the above choices.", 1, 6);
    }
    
    public Order getNewOrderInfo() { 
        String name = io.readString("Please enter Customer Name");
        String stateName = io.readString("Please enter State");
        String productType = io.readString("Please enter Product Type");
        String area = io.readString("Please enter Area (Minimum order size is 100)");
        BigDecimal a = new BigDecimal(area);
        Order currentOrder = new Order();
        currentOrder.setName(name);
        currentOrder.setStateName(stateName);
        currentOrder.setProductType(productType);
        currentOrder.setArea(a);
        return currentOrder;
}
    public String getNameInfo(){
        String name = io.readString("Please enter Customer Name");       
        return name;
    }
    
    public String getDateInfo(){
        String date = io.readString("Please enter Order Date (YYYY-MM-DD)");
        return date;
    }
    
    public String getProductInfo(List<Product> p){
        io.print("-----------------------------------------------------------");
        io.print("ProductType   CostPerSqrFoot     LaborCostPerSqrFoot");
        io.print("-----------------------------------------------------------");
        for (Product currentProduct : p) {
            String pInfo = String.format("*%8s     %10s         %10s",
                  currentProduct.getProductType(),
                  currentProduct.getCostPerSqrFoot(),
                  currentProduct.getCostPerSqrFoot());
            io.print(pInfo);
        }
        io.print("-----------------------------------------------------------");
        String pType = io.readString("Please enter Product Type from the list");       
        return pType;
    }
    
    public String getStateInfo(){
        String state = io.readString("Please enter your State Abreviation");
        return state;
    }
    
    public String getAreaInfo(){
        String area = io.readString("Please enter the Area (Minimum order is 100 square feet)");
        return area;
    }
    
    public String editAreaInfo(BigDecimal a){
        String area = io.readString("Please enter the Area (Minimum order is 100 square feet) (" + a + "):");
        return area;
    }
    
    public String editNameInfo(String name){
        String newname = io.readString("Enter customer name (" + name + "):");
        return newname;
    }
    
    public String editStateInfo(String state){
        String newstate = io.readString("Please enter the state abreviation (" + state + "):");
        return newstate;
    }
    
    public String editProductInfo(String p){
        String product = io.readString("Please enter the product type (" + p + "):");
        return product;
    }
    
    public int getOrderNumInfo(){
        int orderNum = io.readInt("Please enter the Order Number");
        return orderNum;
    }
    
    public String printOrderAndGetConfirmation(Order order){
        String stringOrder = order.toString();
        io.print("--------------------------");
        io.print(stringOrder);
        io.print("--------------------------");
        String confirm = "";
        while (!confirm.equals("Y") && !confirm.equals("y") && !confirm.equals("N") && !confirm.equals("n")){
            confirm = io.readString("Please confirm (Y/N)");
        }
        return confirm;
    }
    
    public void displayOrderList(List<Order> orderList) {
 
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%15s %30s %15s %15s %15s %15s %15s %15s %15s %15s %15s %15s", "OrderNumber","CustomerName","State","TaxRate","ProductType","Area","CostPerSquareFoot",
                "LaborCostPerSquareFoot","MaterialCost","LaborCost","Tax","Total");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        
        for (Order currentOrder : orderList) {
            String orderInfo = String.format("%15s %30s %15s %15s %15s %15s %15s %15s %20s %20s %15s %15s",
                  currentOrder.getOrderNum(),
                  currentOrder.getName(),
                  currentOrder.getStateName(),
                  currentOrder.getTaxRate(),
                  currentOrder.getProductType(),
                  currentOrder.getArea(),
                  currentOrder.getCostPerSquareFoot(),
                  currentOrder.getLaborCostPerSquareFoot(),
                  currentOrder.getMaterialCost(),
                  currentOrder.getLaborCost(),
                  currentOrder.getTax(),
                  currentOrder.getTotal());

            io.print(orderInfo);
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        io.readString("Please hit enter to continue.");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
    public void displayExitMessage() {
        io.print("=== GOODBYE ===");    
    }
    
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
    
    public void displaySuccessfulAddBanner(){
        io.print("=== ORDER WAS SUCCESSFULLY CREATED ===");
    }
    
    public void displaySuccessfulEditBanner(){
        io.print("=== ORDER WAS SUCCESSFULLY EDITED ===");
    }
    
    public void displaySuccessfulRemoveBanner(){
        io.print("=== ORDER WAS SUCCESSFULLY REMOVED ===");
    }
    
    public void displaySuccessfulExportBanner(){
        io.print("=== DATA EXPORT SUCCESSFULLY COMPLETED ===");
    }
}
