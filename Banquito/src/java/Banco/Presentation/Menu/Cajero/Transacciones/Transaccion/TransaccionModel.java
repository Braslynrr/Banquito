/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero.Transacciones.Transaccion;

import Banco.Logic.Account;
import java.util.List;

/**
 *
 * @author gaira
 */
public class TransaccionModel {
    
    List<Account> cuentas;

 
    public TransaccionModel(){
        
        cuentas = null;
    
    }
    public List<Account> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Account> cuentas) {
        this.cuentas = cuentas;
    }
    
}
