/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Data;


import static Banco.Data.UserDao.toUser;
import Banco.Logic.Cashier;
import Banco.Logic.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author gaira
 */
public class CashierDao {
    
     
     RelDatabase db;

    public CashierDao() {
        db = new RelDatabase();
    }
    
    public void AddCashier(Cashier c) throws Exception{
        
        String sql = "insert into client (id,name,User_id)"
                + "values ('%s','%s','%s','%s')";
        
        sql = String.format(sql, c.getId(),c.getName(),c.getUserid());
        int count = db.executeUpdate(sql);
        if(count == 0){
            throw new Exception("El cajero ya existe");
        }
        
        
    }
    
    public Cashier getCashier(String id)throws Exception{
        
        String sql = "select * from cashier c inner join user u on c.User_id = u.id where c.User_id = '%s'";
        sql = String.format(sql,id);
        ResultSet rs = db.executeQuery(sql);
        
        if(rs.next()){
            return this.toCashier(rs);
        }
         else{
            return null;
        }
        
    }
    
    
    public Integer GeneratorNCashier()throws Exception{
        String sql="select count(cod) from cashier";
        ResultSet rs = db.executeQuery(sql);
         if (rs.next()) {
            return Integer.parseInt(rs.getString("count(cod)"))+1;
         }else{
            return 1;
         }
    } 
    
    public static Cashier toCashier(ResultSet rs) throws SQLException{
    
        try{      
            Cashier c = new Cashier();
            c.setId(rs.getString("cod"));
            c.setName(rs.getString("name"));
            c.setUserid(toUser(rs));
            return c;
        }
        catch(SQLException ex){
            return null;
        }
    }
    
    
    
}
