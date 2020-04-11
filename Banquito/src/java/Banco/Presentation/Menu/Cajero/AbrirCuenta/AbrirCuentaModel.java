/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cajero.AbrirCuenta;

import Banco.Logic.Currency;
import java.util.List;

/**
 *
 * @author gaira
 */
public class AbrirCuentaModel {
    
    
    

    List<Currency> monedas;

    
    
    public AbrirCuentaModel(){
    
        monedas = null;
        
    }

    
    public List<Currency> getMonedas() {
        return monedas;
    }

    public void setMonedas(List<Currency> monedas) {
        this.monedas = monedas;
    }
    
    
    
}
