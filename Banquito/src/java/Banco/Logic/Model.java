/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Logic;

import Data.AccountDao;
import Data.CashierDao;
import Data.ClientDao;
import Data.CurrencyDao;
import Data.TransactionDao;
import Data.UserDao;

/**
 *
 * @author gaira
 */
public class Model {
    
        
   AccountDao account;
   CashierDao cashier;
   ClientDao client;
   CurrencyDao currency;
   TransactionDao transaction;
   UserDao user;
   
   public Model () {
       
       account = new AccountDao();
       cashier = new CashierDao();
       client = new ClientDao();
       currency = new CurrencyDao();
       transaction = new TransactionDao();
       user = new UserDao();
   
   }
   
   public User consultUser(String id) throws Exception{
   
       User result = user.getUser(id);
       if (result == null){
           throw new Exception ("Usuario no existe");
       }
       return result;
   
   }
    
    
    
    
    
}
