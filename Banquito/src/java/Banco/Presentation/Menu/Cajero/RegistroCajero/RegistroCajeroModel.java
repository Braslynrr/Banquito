/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero.RegistroCajero;

import Banco.Logic.Client;
import Banco.Logic.User;

/**
 *
 * @author gaira
 */
public class RegistroCajeroModel {

  
    
    Client cliente;
    User usuario;
    
    public RegistroCajeroModel(){
        
        usuario = new User();
        cliente = null;
    
    
    }
    
    
    public Client getCliente() {
        return cliente;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
    
    
}
