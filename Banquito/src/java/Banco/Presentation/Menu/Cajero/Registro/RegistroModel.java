/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero.Registro;


//Puede ser que al final no todos los imports sean necesarios
import Banco.Logic.Cashier;
import Banco.Logic.Client;
import Banco.Logic.Account;
import Banco.Logic.User;
import Banco.Logic.Currency;

/**
 *
 * @author gaira
 */
public class RegistroModel {
    
    
    //Tengo que cambiar el atributo cliente por una lista de clientes
    Client cliente;
    Account cuenta;
    Cashier cajero;
    Currency moneda;
    User usuario;

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public User getUsuario() {
        return usuario;
    }
    
    
    public RegistroModel(){
    
        cliente = null;
        cuenta = null;
        cajero = null;
        moneda = null;
        usuario = null;
    }
    
    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public void setCuenta(Account cuenta) {
        this.cuenta = cuenta;
    }

    public void setCajero(Cashier cajero) {
        this.cajero = cajero;
    }

    public void setMoneda(Currency moneda) {
        this.moneda = moneda;
    }

    public Client getCliente() {
        return cliente;
    }

    public Account getCuenta() {
        return cuenta;
    }

    public Cashier getCajero() {
        return cajero;
    }

    public Currency getMoneda() {
        return moneda;
    }
    

    
    
    
    
    
    
    
    
    
    
    
    
}
