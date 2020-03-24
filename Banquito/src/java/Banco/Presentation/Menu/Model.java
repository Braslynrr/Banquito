/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu;

import Banco.Logic.Account;
import Banco.Logic.Cashier;
import Banco.Logic.User;

/**
 *
 * @author Brazza
 */
public class Model {
    User usuario;
    Cashier cash;
    Account cuenta;

    public Model() {
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Cashier getCash() {
        return cash;
    }

    public void setCash(Cashier cash) {
        this.cash = cash;
    }

    public Account getCuenta() {
        return cuenta;
    }

    public void setCuenta(Account cuenta) {
        this.cuenta = cuenta;
    }
    
    
    
    
}
