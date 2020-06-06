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
        
        String sql = "insert into Transaction "
                + "values(%s,'%s',%s,'%s',%s,'%s','%s')";
        SimpleDateFormat form= new SimpleDateFormat("YYYY-MM-dd");
        String fecha= form.format(t.getDate());
        sql = String.format(sql,t.getNumber(),t.getType(),t.getAmount(),fecha,t.getAccount().getNumber(),t.getCurrencyCode(), t.getDetail());
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
            t.setDetail(rs.getString("detail"));
            return t;
           
        
        }
        catch(SQLException ex){
            return null;
        }
    
    
    
    }
    
    public List<Transaction> getlistaRang(String cod,String FechaI,String FechaF, String dmin)throws Exception{
     List<Transaction> lista = new ArrayList<Transaction>();
        String sql="select * from transaction t inner join account a inner join currency inner join client c inner join user u on t.Account_number= a.number and c.cod=a.Client_client_cod and c.User_id=u.id where a.Client_client_cod='%s' ";
        sql = String.format(sql,cod);
        if(!FechaI.isEmpty()){
            sql=sql+"and  t.date>='%s' ";
            sql = String.format(sql,FechaI);
        }
        if(!FechaF.isEmpty()){
            sql=sql+"and t.date<='%s'" ;
            sql = String.format(sql,FechaF);
        }
        if(!dmin.isEmpty()){
             sql=sql+"and t.amount>='%s'";
             sql = String.format(sql,dmin);
        }
        ResultSet rs = db.executeQuery(sql);
        while(rs.next()){
            lista.add(toTransaction(rs));
        }
        return lista;
    }
    

    public List<Transaction> getlista(Integer num,String cod)throws Exception{
        int max=30;
        List<Transaction> lista = new ArrayList<Transaction>();
        String sql="select * from transaction t inner join account a inner join currency cu "
                + "inner join client c inner join user u on t.Account_number= a.number and c.cod=a.Client_client_cod and"
                + " t.currencyCode= cu.currencyCode and c.User_id=u.id where t.Account_number= '%s' and a.Client_client_cod= '%s'";
        sql = String.format(sql,num,cod);
        ResultSet rs = db.executeQuery(sql);
        while(rs.next()&& max>0){
            lista.add(toTransaction(rs));
            max=max-1;
        }
        return lista;
    }
    
        
    public Integer GeneratorNTransaction()throws Exception{
        String sql="select count(number) from transaction";
        ResultSet rs = db.executeQuery(sql);
         if (rs.next()) {
            return Integer.parseInt(rs.getString("count(number)"))+1;
         }else{
            return 1;
         }
    } 
    
    public float CantActual(Account ac,Date da) throws Exception{
        SimpleDateFormat form= new SimpleDateFormat("YYYY-MM-dd");
        String fecha= form.format(da);
        String sql= "select sum(amount) from transaction t where t.date='%s' and t.Account_number=%s and t.type='Transferencia-Envio'";
        sql = String.format(sql,fecha,ac.getNumber());
        ResultSet rs = db.executeQuery(sql);
         if (rs.next()) {
             String num=rs.getString("sum(amount)");
             if(num==null)
                return 0;
             return Float.parseFloat(num);        
        }
        return 0; 
    }
    
}
