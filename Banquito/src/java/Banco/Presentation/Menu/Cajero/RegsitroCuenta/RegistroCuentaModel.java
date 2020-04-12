/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero.RegsitroCuenta;

import Banco.Logic.Client;
import Banco.Logic.Currency;
import java.util.List;

/**
 *
 * @author gaira
 */
public class RegistroCuentaModel {
    
    List<Currency> monedas;


    
    
    public RegistroCuentaModel(){
    
        monedas = null;
        client = null;
        
    }

    
    public List<Currency> getMonedas() {
        return monedas;
    }

    public void setMonedas(List<Currency> monedas) {
        this.monedas = monedas;
    }
    
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    Client client;
    
    
}
