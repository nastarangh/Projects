/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.ngguessthenumber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author nastarangh
 */
@SpringBootApplication
public class App {
    //
    //IMPORTANT NOTE: the main database and test database sql scripts 
    //must be run BEFORE running the application
    //
    public static void main(String[] args) {
        
        SpringApplication.run(App.class, args);
        
    }
}
