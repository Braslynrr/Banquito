/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Data;

import static Banco.Data.AccountDao.toAccount;
import Banco.Logic.Account;
import Banco.Logic.Client;
import Banco.Logic.Favorites;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brazza
 */
public class FavoritesDao {
    RelDatabase db;
    
    public FavoritesDao(){
        db= new RelDatabase();
    }
    
    
   
    public void EliminarFavorito(Client cl,Account other) throws Exception{
        String sql = "delete from favorites f where f.Client_cod='%s' and f.favorite_account= %s";
       sql = String.format(sql,cl.getCod(),other.getNumber());
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("Favorito no existe");
        }
    }
    
    public void AñadirFavorito(Client cl,Account other) throws Exception{
        String sql = "insert into favorites values(0,%s,'%s')";
        sql = String.format(sql,other.getNumber(),cl.getCod());
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("Favorito ya existe");
        }
    }
    
    public boolean VerificarFavorito(Client cl,Account other)throws Exception{
        String sql="select * from favorites f where f.Client_cod='%s' and f.favorite_account= %s";
        sql = String.format(sql,cl.getCod(),other.getNumber());
        ResultSet rs = db.executeQuery(sql);
        if (rs.next()) {
            return true;
         }
        return false;
    }
    
    public List<Account> ListaFavoritos(String cod)throws Exception{
        List<Account> lista= new ArrayList<Account>();
        String sql="select *  from favorites f inner join account a inner join currency c inner "
                + "join client cl inner join user u on a.Client_client_cod= cl.cod and u.id = cl.User_id and f.favorite_account=a.number "
                + "and a.Currency_currencyCode= c.currencyCode where f.Client_cod ='%s'";
        sql = String.format(sql,cod);
        ResultSet rs = db.executeQuery(sql);
        while(rs.next()){
            lista.add(toAccount(rs));
        }    
        return lista;
    }
}
