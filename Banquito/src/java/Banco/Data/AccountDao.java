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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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
    
        String sql = "insert into Account (number,balance,Client_client_cod,Currency_currencyCode)"
                + "values('%s','%s','%s','%s')";
        sql = String.format(sql,a.getNumber(),a.getBalance(),a.getClient().getCod(),a.getCurrency().getCurrencyCode());
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("La cuenta ya existe");
        }
    }
    
    public void Update(Account a)throws Exception{
        String sql= "Update account set balance= %s , Client_client_cod= '%s', Currency_currencyCode= '%s' where number= %s";
        sql = String.format(sql,a.getBalance(),a.getClient().getCod(),a.getCurrency().getCurrencyCode(),a.getNumber());
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("No se pudo actualizar");
        }
    }
    public Account getAccount(String num) throws Exception {
    
        String sql = "select * from account a inner join client c inner join user u on "
                + "u.id=c.User_id on a.Client_client_cod = c.cod inner join Currency d "
                + "on a.Currency_currencyCode = d.currencyCode where a.number = %s";
        sql = String.format(sql,num);
        ResultSet rs = db.executeQuery(sql);
         if (rs.next()) {
            return toAccount(rs);
        }
        else{
            throw new Exception ("La cuenta no existe");
        }
        
    
    }
    
    public Integer GeneratorNAccount()throws Exception{
        String sql="select count(number) from account";
        ResultSet rs = db.executeQuery(sql);
         if (rs.next()) {
            return Integer.parseInt(rs.getString("count(number)"))+1;
         }else{
            return 1;
         }
    } 
    
    public static Account toAccount(ResultSet rs) throws SQLException{
        
        try{
        
            Account a = new Account();
            a.setNumber(Integer.parseInt(rs.getString("number")));
            a.setBalance(Float.parseFloat(rs.getString("balance")));
            a.setClient(toClient(rs));
            a.setCurrency(toCurrency(rs));
            return a;    
        }
         catch (SQLException ex) {
            return null;
        }
    }

    public List<Account> getList(String cod)throws Exception {
        List<Account> lista= new ArrayList<Account>();
        String sql = "select * from account a inner join client c inner join user u inner join currency cu on a.Client_client_cod = c.cod and "
                + " c.User_id = u.id and a.Currency_currencyCode=cu.currencyCode where c.cod='%s'";
        sql = String.format(sql,cod);
        ResultSet rs = db.executeQuery(sql);
        while(rs.next()){
            lista.add(toAccount(rs));
        }    
        if(lista.isEmpty()){
            throw new Exception ("Usuario no tiene cuentas");
        }
        return lista;
    }
    
    public String GenertadorKey(){
        String key="";
        char generator;
        for(int i=0;i<8;i=i+1){
            int x = (int) (Math.random()*74 + 47);
            if(x<96 && x>90){
               x=x+8;
            }
            generator=(char) x;
            key=key+generator;
        }
        return key;
    }
    
    
    public Date getFechaProxima() throws Exception{
        String sql="SELECT * FROM server where cod=1";
        ResultSet rs = db.executeQuery(sql);
        if(rs.next()){
            String fecha=rs.getString("Fecha");
            String[] parts = fecha.split("-");
            Date date =new Date(Integer.parseInt(parts[0])-1900,Integer.parseInt(parts[1])-1,Integer.parseInt(parts[2]));
            Date x= new Date(2020-1900,0,1);
            return date;
        }
        return null;
    }
    
    
    public boolean isfecha() throws Exception{
        String sql= "select * from server";
        ResultSet rs = db.executeQuery(sql);
        if(rs.next()){
            return true;
        }
        return false;
    }
    
    public void setServerDate(Date date) throws Exception {
        SimpleDateFormat form= new SimpleDateFormat("YYYY-MM-dd"); 
        String fecha= form.format(date);
        String sql="update server set cod=1 , fecha='%s' where cod=1";
        sql = String.format(sql,fecha);
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("Fecha fallo");
        }
    }
    
    public void includeDate(Date date) throws Exception{
        SimpleDateFormat form= new SimpleDateFormat("YYYY-MM-dd"); 
        String fecha= form.format(date);
        String sql="insert into server value(1 ,'%s')";
        sql = String.format(sql,fecha);
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("Fecha fallo");
        }
    }

    public List<Account> getAll() throws Exception {
        List<Account> lista= new ArrayList<Account>();
        String sql = "select * from account a inner join client c inner join user u inner join currency cu on a.Client_client_cod = c.cod and "
                + " c.User_id = u.id and a.Currency_currencyCode=cu.currencyCode";
        ResultSet rs = db.executeQuery(sql);
        while(rs.next()){
            lista.add(toAccount(rs));
        }    
        if(lista.isEmpty()){
            throw new Exception ("No existen cuentas");
        }
        return lista;
    }
}
