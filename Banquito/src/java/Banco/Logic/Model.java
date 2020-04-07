/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Logic;

import Banco.Data.AccountDao;
import Banco.Data.CashierDao;
import Banco.Data.ClientDao;
import Banco.Data.CurrencyDao;
import Banco.Data.TransactionDao;
import Banco.Data.UserDao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gaira
 */
public class Model {
   private static Model uniqueInstance;
    
   
     public static Model instance(){
        if (uniqueInstance == null){
            uniqueInstance = new Model();
        }
        return uniqueInstance; 
    }
   
   
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
   
   
   public String clientCode () throws Exception{
   
       
       String code = "CL"+Integer.toString(client.GeneratorNclient());
       
   
       return code;
   }
   
   public User consultUser(String id,String pass) throws Exception{
       
       User result = user.Login(id,pass);
       if (result == null){
           throw new Exception ("Usuario no existe");
       }
       return result;
   
   }
    
   
   public List<Account> ConsultarCuentas(String cod,String id)throws Exception{
       List<Account> lista= account.getList(cod,id);
       return lista;
   }
   
   public Account getCuenta(String cod)throws Exception{
       Account cuenta=account.getAccount(cod);
       return cuenta;
   }
   
   public boolean clientExist (String id) throws Exception {
    
          
      return client.getClient(id).getUserid().getId().equals(id);
       
   }
   
   public void addUser(User obj) throws Exception{
       
       user.AddUser(obj);
       
   }
   
   public List<Transaction> consultarTransacionesRange(String Cod, String FechaI,String FechaF,String dmin)throws Exception{
        List<Transaction> lista = transaction.getlistaRang(Cod, FechaI, FechaF, dmin);
        return lista;
   }
   
    public void addClient(Client obj) throws Exception{
       
       client.AddClient(obj);
       
   }
   public Client ConsutClient(String id)throws Exception{
       Client cliente = client.getClient(id);
       return cliente;
   }
   
   public Cashier consultcash(String id)throws Exception{
     Cashier cash=cashier.getCashier(id);
     return cash;
    }
   
   public List<Currency> Consultarcurrency()throws Exception{
     List<Currency> lista=currency.Listacurrency();
     return lista;
   }
   
   public List<Transaction> consultarTransaciones(Integer num,String cod)throws Exception{
       List<Transaction> lista = transaction.getlista(num,cod);
       return lista;
   }
   
   public String getpassword(){
       return account.GenertadorKey();
   }
}