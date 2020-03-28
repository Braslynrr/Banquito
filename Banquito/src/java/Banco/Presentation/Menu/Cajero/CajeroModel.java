/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero;

/**
 *
 * @author gaira
 */


import Banco.Logic.Account;
import Banco.Logic.Cashier;
import java.util.ArrayList;
import java.util.List;

public class CajeroModel {
List<Account> cuentas;
Cashier cashier;
    public CajeroModel() {
        cuentas=null;
        cashier=null;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public List<Account> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Account> cuentas) {
        this.cuentas = cuentas;
    }
}
