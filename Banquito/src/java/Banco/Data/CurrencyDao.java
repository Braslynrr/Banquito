/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Data;


import Banco.Logic.Currency;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author gaira
 */
public class CurrencyDao {
    
        RelDatabase db;

    public CurrencyDao() {
        db = new RelDatabase();
    }
    
    public void addCurrency(Currency c) throws Exception{
    
        String sql = "insert into Currency(currencyCode,exchange_rate,description)"
                + "values('%s','%s','%s')";
        sql = String.format(sql,c.getCurrencyCode(),c.getExchangeRate(),c.getDescription());
         int count = db.executeUpdate(sql);
        if(count == 0){
            throw new Exception("La moneda ya existe");
        }   
    }
    
    public Currency getCurrency(String code)  throws Exception{
    
        String sql  = "select * from Currency where description = '%s'";
        sql = String.format(sql, code);
           ResultSet rs = db.executeQuery(sql);
        if(rs.next()){
            return this.toCurrency(rs);
        }
        else{
            throw new Exception ("El tipo de moneda no existe");
        }
        
    }    
    
    public List<Currency> Listacurrency()throws Exception{
        String sql="Select * from Currency";
        List<Currency> lista= new ArrayList<Currency>();
        ResultSet rs = db.executeQuery(sql);
        while(rs.next()){
            Currency curren= this.toCurrency(rs);
            if(curren.getDescription()!="colones"){
                lista.add(curren);
            }
        }
        return lista;
    }
    
    public static Currency toCurrency(ResultSet rs)throws SQLException{
    
        try{
            Currency c = new Currency();
            c.setCurrencyCode(rs.getString("currencyCode"));
            c.setExchangeRate(Float.parseFloat(rs.getString("exchange_rate")));
            c.setDescription(rs.getString("description"));
            c.setTax(Float.parseFloat(rs.getString("tax")));
            return c;
        }
          catch(SQLException ex){
        return null;
    }
    }
}
