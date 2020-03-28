/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Data;



import static Banco.Data.AccountDao.toAccount;
import Banco.Logic.Currency;
import Banco.Logic.Account;
import Banco.Logic.Transaction;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author gaira
 */
public class TransactionDao {
   
    RelDatabase db;
    
    public TransactionDao(){
        db= new RelDatabase();
    }
    
    public void addTransaction(Transaction t)throws Exception{
        
        String sql = "insert into Transaction (number,type,amount,date,Account_number,currencyCode)"
                + "values('%s','%s','%s','%s','%s','%s')";
        sql = String.format(sql,t.getNumber(),t.getType(),t.getAmount(),t.getDate());
        int count=db.executeUpdate(sql);
        if (count==0){
            throw new Exception("La transaccion ya existe");
        }
    }
    
    public Transaction transactionGet(String num) throws Exception{
    
        String sql = "select *" +
                 "from Transaction t inner join Account a on t.Account_number = c.number"
                + "where t.number = '%s'";
        sql = String.format(sql,num);
        ResultSet rs = db.executeQuery(sql);
         if (rs.next()) {
            return toTransaction(rs);
        }
        else{
            throw new Exception ("La transaccion no existe");
        }
    
    }
    
    public static Transaction toTransaction(ResultSet rs) throws SQLException, ParseException{
    
        try{
        
            Transaction t = new Transaction();
            DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
            Date date = format.parse(rs.getString("date"));
            t.setNumber(Integer.parseInt(rs.getString("number")));
            t.setType(rs.getString("type"));
            t.setAmount(Float.parseFloat(rs.getString("amount")));
            t.setDate(date);
            t.setAccount(toAccount(rs));
            t.getAccount().setNumber(Integer.parseInt(rs.getString("number")));
            t.setCurrencyCode(t.getAccount().getCurrency().getCurrencyCode());
            return t;
           
        
        }
        catch(SQLException ex){
            return null;
        }
    
    
    
    }

    public List<Transaction> getlista(Integer num,String cod)throws Exception{
        List<Transaction> lista = new ArrayList<Transaction>();
        String sql="select * from transaction t inner join account a inner join currency inner join client c inner join user u on t.Account_number= a.number and c.cod=a.Client_client_cod and c.User_id=u.id where t.Account_number= '%s'";
        String sql2="and a.Client_client_cod= '%s'";
        sql = String.format(sql,num);
        sql2 = String.format(sql2,cod);
        ResultSet rs = db.executeQuery(sql+sql2);
        while(rs.next()){
            lista.add(toTransaction(rs));
        }
        return lista;
    }
    
    
    
}
