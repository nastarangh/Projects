
package com.wiley.ngflooring.dto;

import java.math.BigDecimal;

/**
 *
 * @author nasta
 */
public class State {
    private String stateAbr;
    private String stateName;
    private BigDecimal taxRate;
    
    public State(String stateAbr, String stateName, BigDecimal taxRate){
        this.stateAbr = stateAbr;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }
    
    public String getStateAbr(){
        return this.stateAbr;
    }
    
    public String getStateName(){
        return this.stateName;
    }
    
    public BigDecimal getTaxRate(){
        return this.taxRate;
    }
}
