
package com.wiley.ngflooring.dto;

import java.math.BigDecimal;

/**
 *
 * @author nasta
 */
public class Product {
    
    private String productType;
    private BigDecimal costPerSqrFoot;
    private BigDecimal laborCostPerSqrFoot;
    
    public Product(String productType, BigDecimal costPerSqrFoot, BigDecimal laborCostPerSqrFoot){
        this.productType = productType;
        this.costPerSqrFoot = costPerSqrFoot;
        this.laborCostPerSqrFoot = laborCostPerSqrFoot;
    }
    
    public String getProductType(){
        return this.productType;
    }
    
    public BigDecimal getCostPerSqrFoot(){
        return this.costPerSqrFoot;
    }
    
    public BigDecimal getLaborCostPerSqrFoot(){
        return this.laborCostPerSqrFoot;
    }
}
