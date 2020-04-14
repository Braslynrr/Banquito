/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Data;

import static Banco.Data.UserDao.toUser;
import Banco.Logic.Client;
import Banco.Logic.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author gaira
 */
public class ClientDao {
    
     RelDatabase db;

    public ClientDao() {
        db = new RelDatabase();
    }
    
    public void AddClient(Client c) throws Exception{
        
        String sql = "insert into client (cod,name,tel_number,User_id,`limit`)"
                + "values ('%s','%s','%s','%s','%s')";
        
        sql = String.format(sql, c.getCod(),c.getName(),c.getTelNumber(),c.getUser().getId(),c.getLimit());
        int count = db.executeUpdate(sql);
        if(count == 0){
            throw new Exception("El usuario ya existe");
        }
        
        
    }
    
    
    public Client getClient(String id)throws Exception{
        
        String sql = "select * from client c inner join user u on c.User_id = u.id where c.User_id = '%s'";
        sql = String.format(sql,id);
        ResultSet rs = db.executeQuery(sql);
        
        if(rs.next()){
            return this.toClient(rs);
        }
         else{
            throw new Exception ("El cliente no existe");
        }
        
    }
    
    public Client compClient(String id)throws Exception{
        
        String sql = "select * from client c inner join user u on c.User_id = u.id where c.User_id = '%s'";
        sql = String.format(sql,id);
        ResultSet rs = db.executeQuery(sql);
        
        if(rs.next()){
            return this.toClient(rs);
        }
         else{
            return null;
        }
        
    }
    
    public Integer GeneratorNclient()throws Exception{
        String sql="select count(cod) from client";
        ResultSet rs = db.executeQuery(sql);
         if (rs.next()) {
            return Integer.parseInt(rs.getString("count(cod)"))+1;
         }else{
            return 1;
         }
    } 
    
    
    public static Client toClient(ResultSet rs) throws SQLException{
    
        try{
            
            Client c = new Client();
            c.setCod(rs.getString("cod"));
            c.setName(rs.getString("name"));
            c.setTelNumber(rs.getString("tel_number"));
            c.setUser(toUser(rs));
            c.setLimit(0.0);
            //c.setUser(c.getUserid());
            return c;
            
        
        }
        catch(SQLException ex){
            return null;
        }
    }
    
    
}
