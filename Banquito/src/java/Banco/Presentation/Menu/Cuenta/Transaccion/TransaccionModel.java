/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cuenta.Transaccion;

import Banco.Logic.Account;
import Banco.Logic.Transaction;
import java.util.Date;
import java.util.List;


public class TransaccionModel {
    Account account;
    List<Transaction> trans;
    String F1,F2;
    int min;
    
    public TransaccionModel() {
    }

    public List<Transaction> getTrans() {
        return trans;
    }

    public void setTrans(List<Transaction> trans) {
        this.trans = trans;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getF1() {
        return F1;
    }

    public void setF1(String F1) {
        this.F1 = F1;
    }

    public String getF2() {
        return  F2;
    }

    public void setF2(String F2) {
        this.F2 = F2;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
    
}
