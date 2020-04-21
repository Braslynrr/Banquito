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
import Banco.Data.FavoritesDao;
import Banco.Data.TransactionDao;
import Banco.Data.UserDao;
import java.util.ArrayList;
import java.util.Date;
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
   FavoritesDao favorites;
   
   public Model () {
       
       account = new AccountDao();
       cashier = new CashierDao();
       client = new ClientDao();
       currency = new CurrencyDao();
       transaction = new TransactionDao();
       user = new UserDao();
       favorites=new FavoritesDao();
   }
   
   
   public String clientCode () throws Exception{
   
       
       String code = "CL"+Integer.toString(client.GeneratorNclient());
       
   
       return code;
   }
   
     public String cashierCode () throws Exception{
   
       
       String code = "Cs"+Integer.toString(cashier.GeneratorNCashier());
       
   
       return code;
   }
   
     public Integer accountNumber () throws Exception{
   
       
       Integer code = account.GeneratorNAccount();
       
   
       return code;
   }
     
    public Integer TransactionNumber () throws Exception{
   
       
       Integer code = transaction.GeneratorNTransaction();
       
   
       return code;
   }  
     
   public User consultUser(String id,String pass) throws Exception{
       
       User result = user.Login(id,pass);
       if (result == null){
           throw new Exception ("Usuario no existe");
       }
       return result;
   
   }
   
      public User getUser(String id) throws Exception{
       
      return  user.getUser(id);
   
   }
     public Client getByCod(String cod) throws Exception{
       
      return  client.clientCod(cod);
   
   } 
   
   public List<Account> ConsultarCuentas(String cod)throws Exception{
       List<Account> lista= account.getList(cod);
       return lista;
   }
   
   public Account getCuenta(String cod)throws Exception{
       Account cuenta=account.getAccount(cod);
       return cuenta;
   }
   
    
   public Currency getCurrency(String cod)throws Exception{
       Currency moneda = currency.getCurrency(cod);
       return moneda;
   }
   
   public List<Account> getfavoritos(String cod)throws Exception{
       List<Account> lista = favorites.ListaFavoritos(cod);
       return lista;
   }
   
   public boolean clientExist (String id) throws Exception {
    
          
      return client.getClient(id).getUser().getId().equals(id);
       
   }
   
   public Client compClient (String id) throws Exception {
   
       
       return client.compClient(id);
   
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
      public void addCashier(Cashier obj) throws Exception{
       
       cashier.AddCashier(obj);
       
   }
    
         public void addTransaction(Transaction obj) throws Exception{
       
       transaction.addTransaction(obj);
       
   }
    
   public void addAccount(Account obj) throws Exception{
       
       account.addAccount(obj);
       
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
   
   public float ConversorMonedas(String cod1 ,String cod2,float cant) throws Exception{
       return currency.ConversorMonedas(cod1, cod2, cant);
   }
   
  public Currency getCurrencyCode(String cod)throws Exception{
      return currency.getCurrencyCode(cod);
  }
  
  public void newTransfer(Account acc,Account trans,Float cantLocal,Float cant,String detail) throws Exception{
      transaction.addTransaction(new Transaction(transaction.GeneratorNTransaction(),"Transferencia-Envio",cantLocal,acc,new Date(),acc.getCurrency().getCurrencyCode(),detail));
      transaction.addTransaction(new Transaction(transaction.GeneratorNTransaction(),"Transferencia-Recibo",cant,trans,new Date(),trans.getCurrency().getCurrencyCode(),detail));
      account.Update(acc);
      account.Update(trans);
  }
  
  public void updateAccount (Account a) throws Exception{
      
      account.Update(a);
      
  }
  
  public void EliminarFavorito(Client cl,Account acc) throws Exception{
      if(favorites.VerificarFavorito(cl, acc)){
          favorites.EliminarFavorito(cl, acc);
      }else{
           throw new Exception("Favorito no existe");
      }
  
  }
  
  public void AñadirFavorito(Client cl,Account acc) throws Exception{
       if(!favorites.VerificarFavorito(cl, acc)){
           favorites.AñadirFavorito(cl, acc);
      }else{
           throw new Exception("Favorito ya existe");
      }
      
  }
  
  public Date FechaInteres()throws Exception{
      return account.getFechaProxima();
  }

    public void setServerDate(Date date) throws Exception {
        if(account.isfecha()){
            account.setServerDate(date);
        }else{
            account.includeDate(date);
        }
    }

    public void InteresMasivo() throws Exception {
        List<Account> cuentas= account.getAll();
        for(Account a:cuentas){
            float cant=(a.getBalance()*a.getCurrency().getTax());
            a.setBalance(a.getBalance()+cant);
            account.Update(a);
            transaction.addTransaction(new Transaction(transaction.GeneratorNTransaction(),"Intereses",cant,a,new Date(),a.getCurrency().getCurrencyCode(),"Interes Pasivo de su cuenta bancaria"));
        }
    }
    
    public float Dinerotransferencias(Account ac) throws Exception{
        return transaction.CantActual(ac, new Date());
    }
  
}