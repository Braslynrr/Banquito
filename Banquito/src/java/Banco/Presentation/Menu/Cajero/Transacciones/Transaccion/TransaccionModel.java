/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero.Transacciones.Transaccion;

import Banco.Logic.Account;
import Banco.Logic.Client;
import java.util.List;

/**
 *
 * @author gaira
 */
public class TransaccionModel {
    
    List<Account> cuentas;
    Client cliente;
    Account cuenta;

    public Account getCuenta() {
        return cuenta;
    }

    public void setCuenta(Account cuenta) {
        this.cuenta = cuenta;
    }

    public Client getCliente() {
        return cliente;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }
 
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
