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
import java.util.List;

/**
 *
 * @author gaira
 */
public class RegistroModel {
    
    
    //Tengo que cambiar el atributo cliente por una lista de clientes
    Client cliente;
    Account cuenta;
    Cashier cajero;


    List<Currency> monedas;
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

  

    public Client getCliente() {
        return cliente;
    }

    public Account getCuenta() {
        return cuenta;
    }

    public Cashier getCajero() {
        return cajero;
    }
    
    public List<Currency> getMonedas() {
        return monedas;
    }

    public void setMonedas(List<Currency> monedas) {
        this.monedas = monedas;
    }


    
    
    
    
    
    
    
    
    
    
    
    
}
