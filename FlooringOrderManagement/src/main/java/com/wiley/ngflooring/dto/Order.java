
package com.wiley.ngflooring.dto;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author nasta
 */
public class Order {
    private int orderNum;
    private LocalDate orderDate;
    private String customerName;
    private String stateName;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot; 
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal taxRate;
    private BigDecimal tax;
    private BigDecimal total;
    
    public Order(int orderNum, String customerName, String stateName, String productType, BigDecimal area){
        this.orderNum = orderNum;
        this.customerName = customerName;
        this.stateName = stateName;
        this.productType = productType;
        this.area = area;  
    }

    public Order() {        
    }

   
    public int getOrderNum(){
        return this.orderNum;
    }
    
    public String getName(){
        return this.customerName;
    }
    
    public String getStateName(){
        return this.stateName;
    }
    
    public LocalDate getOrderDate(){
        return this.orderDate;
    }
    
    public String getProductType(){
        return this.productType;
    }
    
    public BigDecimal getArea(){
        return this.area;
    }
    
    public BigDecimal getCostPerSquareFoot(){
        return this.costPerSquareFoot;
    }
    
    public BigDecimal getLaborCostPerSquareFoot(){
        return this.laborCostPerSquareFoot;
    }
    
    public BigDecimal getMaterialCost(){
        return this.materialCost;
    }
    
    public BigDecimal getTaxRate(){
        return this.taxRate;
    }
    
    public BigDecimal getLaborCost(){
        return this.laborCost;
    }
    
    public BigDecimal getTax(){
        return this.tax;
    }
    
    public BigDecimal getTotal(){
        return this.total;
    }
    
    public void setName(String name){
         this.customerName = name;
    }

    public void setOrderDate(LocalDate date){
        this.orderDate = date;
    }
    
    public void setStateName(String s){
        this.stateName = s;
    }
    
    public void setProductType(String p){
        this.productType = p;
    }
    
    public void setArea(BigDecimal a){
        this.area = a;
    }
    public void setCostPerSquareFoot(BigDecimal c){
        this.costPerSquareFoot = c;
    }
    
    public void setLaborCostPerSquareFoot(BigDecimal l){
        this.laborCostPerSquareFoot = l;
    }
    
    public void setTaxRate(BigDecimal r){
        this.taxRate = r;
    }
    
    public void setTax(BigDecimal tax){
        this.tax = tax;
    }
    
    public void setMaterialCost(BigDecimal mCost){
        this.materialCost = mCost;
    }
    
    public void setLaborCost(BigDecimal lCost){
        this.laborCost = lCost;
    }
    
    public void setTotal(BigDecimal total){
        this.total = total;
    }
    
    public void calculateOrder(){
        this.materialCost = this.area.multiply(costPerSquareFoot).setScale(2, RoundingMode.UP);
        this.laborCost = this.area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.UP);
        BigDecimal hundred = new BigDecimal("100");
        this.tax = (this.materialCost.add(this.laborCost)).multiply(taxRate.divide(hundred)).setScale(2, RoundingMode.UP);
        this.total = this.materialCost.add(this.laborCost.add(this.tax)).setScale(2, RoundingMode.UP);
    }
    
    public String toString(){
        String stringOrder = "Order Number: " + this.getOrderNum() + "\n";
        
        stringOrder += "Customer Name: " + this.getName() + "\n";
        stringOrder += "State: " + this.getStateName() + "\n";
        stringOrder += "Tax Rate: " + this.getTaxRate() + "\n";
        stringOrder += "Product Type: " + this.getProductType() + "\n";
        stringOrder += "Area: " + this.getArea() + "\n";
        stringOrder += "Cost Per Square Foot: " + this.getCostPerSquareFoot() + "\n";
        stringOrder += "Labor Cost Per Square Foot: " + this.getLaborCostPerSquareFoot() + "\n";
        stringOrder += "Material Cost: " + this.getMaterialCost() + "\n";
        stringOrder += "Labor Cost: " + this.getLaborCost() + "\n";
        stringOrder += "Tax: " + this.getTax() + "\n";
        stringOrder += "Total: " + this.getTotal() + "\n";
        
        return stringOrder;
    }
    
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.orderNum);
        hash = 89 * hash + Objects.hashCode(this.orderDate);
        hash = 89 * hash + Objects.hashCode(this.customerName);
        hash = 89 * hash + Objects.hashCode(this.stateName); 
        hash = 89 * hash + Objects.hashCode(this.productType);
        hash = 89 * hash + Objects.hashCode(this.area);
        hash = 89 * hash + Objects.hashCode(this.costPerSquareFoot);
        hash = 89 * hash + Objects.hashCode(this.laborCostPerSquareFoot);
        hash = 89 * hash + Objects.hashCode(this.materialCost);
        hash = 89 * hash + Objects.hashCode(this.laborCost);
        hash = 89 * hash + Objects.hashCode(this.taxRate);
        hash = 89 * hash + Objects.hashCode(this.tax);
        hash = 89 * hash + Objects.hashCode(this.total);
        return hash;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (!Objects.equals(this.orderNum, other.orderNum)) {
            return false;
        }
        if (!Objects.equals(this.orderDate, other.orderDate)) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.stateName, other.stateName)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.costPerSquareFoot, other.costPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSquareFoot, other.laborCostPerSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        
        return true;
    }
}
