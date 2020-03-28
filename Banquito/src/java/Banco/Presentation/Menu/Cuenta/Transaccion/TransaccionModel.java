/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cuenta.Transaccion;

import Banco.Logic.Account;
import Banco.Logic.Transaction;
import java.util.List;


public class TransaccionModel {
    Account account;
    List<Transaction> trans;
    
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
    
}
