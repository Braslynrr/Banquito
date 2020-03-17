/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;
import Logic.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gaira
 */
public class UserDao {
    
    RelDatabase db;
    
    public UserDao(){
        
        db = new RelDatabase();
    
    }
    
    
    
    public void AddUser(User u)  throws Exception{
    
        
        String sql = "insert into user (id,password)"
            + "values ('%s','%s')";
        sql = String.format(sql, u.getId(), u.getPassword());
        int count = db.executeUpdate(sql);
        if(count == 0){
            throw new Exception("El usuario ya existe");
        }
    }
    
    public void DeleteUser (User u){
        //Implementar si se ocupa
    }
    
    public User getUser(String id)throws Exception{
        
        String sql = "select * from user where id = '%s'";
        sql = String.format(sql, id);
        ResultSet rs = db.executeQuery(sql);
         if(rs.next()){
            return this.toUser(rs);
        }
        else{
            throw new Exception ("El usuario no existe");
        }
        
        
    }
    
    
    public static User toUser(ResultSet rs) throws SQLException{
    
        try{
            
            User u = new User();
            u.setId(rs.getString("id"));
            u.setPassword(rs.getString("password"));
            return u;
        
        }
        catch(SQLException ex){
            return null;
        }
    }
}
