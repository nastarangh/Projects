/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wiley.vendingmachine;

import com.wiley.vendingmachine.controller.VendingMachineController;
import com.wiley.vendingmachine.dao.InsufficientFundsException;
import com.wiley.vendingmachine.dao.NoItemInventoryException;
import com.wiley.vendingmachine.dao.VendingMachineAuditDao;
import com.wiley.vendingmachine.dao.VendingMachineAuditDaoFileImpl;
import com.wiley.vendingmachine.dao.VendingMachineDao;
import com.wiley.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.wiley.vendingmachine.service.VendingMachineServiceLayer;
import com.wiley.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.wiley.vendingmachine.ui.UserIO;
import com.wiley.vendingmachine.ui.UserIOConsoleImpl;
import com.wiley.vendingmachine.ui.VendingMachineView;

/**
 *
 * @author nastarangh
 */
public class App {
    
    public static void main(String[] args) throws NoItemInventoryException, InsufficientFundsException {
        UserIO myIo = new UserIOConsoleImpl();
        VendingMachineView myView = new VendingMachineView(myIo);
        VendingMachineDao myDao = new VendingMachineDaoFileImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoFileImpl();
        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, auditDao);
        VendingMachineController controller = new VendingMachineController(myView, myService);
        controller.run();
    }   
}

