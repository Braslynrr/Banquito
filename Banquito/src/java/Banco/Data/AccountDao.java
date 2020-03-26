/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Data;

import static Banco.Data.ClientDao.toClient;
import static Banco.Data.CurrencyDao.toCurrency;
import Banco.Logic.Client;
import Banco.Logic.Currency;
import Banco.Logic.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gaira
 */
public class AccountDao {
    
    //FALATA TERMINAR EL TO Y AGREGAR EL ADD
    
    RelDatabase db;
    
    public AccountDao(){
        db= new RelDatabase();
    }
    
    
    public void addAccount(Account a) throws Exception{
    
        String sql = "insert into Account (number,balance,Client_client_id,Currency_currencyCode)"
                + "values('%s','%s','%s','%s')";
        sql = String.format(sql,a.getNumber(),a.getBalance(),a.getClient(),a.getCurrency());
         int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("La cuenta ya existe");
        }
    
    
    
    
    
    }
    public Account getAccount(String num) throws Exception {
    
        String sql = "select *" +
                 "from Account a inner join Client c on a.Client_client_id = c.id"
                + "inner join Currency d on a.Currency_currencyCode = d.currencyCode"
                + "where a.number = '%s'";
        sql = String.format(sql,num);
        ResultSet rs = db.executeQuery(sql);
         if (rs.next()) {
            return toAccount(rs);
        }
        else{
            throw new Exception ("La cuenta no existe");
        }
        
    
    }
    
    public static Account toAccount(ResultSet rs) throws SQLException{
        
        try{
        
            Account a = new Account();
            a.setNumber(Integer.parseInt(rs.getString("number")));
            a.setBalance(Integer.parseInt(rs.getString("balance")));
            a.setClient(toClient(rs));
            a.setCurrency(toCurrency(rs));
            return a;    
        }
         catch (SQLException ex) {
            return null;
        }
    }

    public ArrayList<Account> getList(String id)throws Exception {
        ArrayList<Account> lista= new ArrayList<Account>();
         String sql = "select * from account a inner join client c inner join user on a.Client_client_cod = '%s'";
        sql = String.format(sql,id);
        ResultSet rs = db.executeQuery(sql);
        while(rs.next()){
            lista.add(toAccount(rs));
        }    
        if(lista.isEmpty()){
            throw new Exception ("Usuario no tiene cuentas");
        }
        return lista;
    }
    
    
}
