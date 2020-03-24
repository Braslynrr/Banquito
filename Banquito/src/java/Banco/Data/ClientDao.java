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
        
        String sql = "insert into client (id,name,tel_number,User_id)"
                + "values ('%s','%s','%s','%s')";
        
        sql = String.format(sql, c.getId(),c.getName(),c.getTelNumber(),c.getUserid());
        int count = db.executeUpdate(sql);
        if(count == 0){
            throw new Exception("El usuario ya existe");
        }
        
        
    }
    
    public Client ConsultClient(String id)throws Exception{
         String sql = "select * from cliente where User_id='%s'";
         sql = String.format(sql,id);
         ResultSet rs = db.executeQuery(sql);
         if(rs.next()){
            return this.toClient(rs);
        }
         else{
            throw new Exception ("El cliente no existe");
        }
    }
    
    public Client getClient(String id)throws Exception{
        
        String sql = "select * from cliente c inner join user u on c.User=u id where c.user_id like '%s'";
        sql = String.format(sql,id);
        ResultSet rs = db.executeQuery(sql);
        
        if(rs.next()){
            return this.toClient(rs);
        }
         else{
            throw new Exception ("El cliente no existe");
        }
        
    }
    
    public static Client toClient(ResultSet rs) throws SQLException{
    
        try{
            
            Client c = new Client();
            c.setId(rs.getString("id"));
            c.setName(rs.getString("name"));
            c.setTelNumber(rs.getString("tel_number"));
            c.setUserid(toUser(rs));
            return c;
            
        
        }
        catch(SQLException ex){
            return null;
        }
    }
    
    
}
