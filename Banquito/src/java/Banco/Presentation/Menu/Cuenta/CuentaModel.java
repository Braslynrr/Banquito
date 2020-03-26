/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cuenta;

import Banco.Logic.Account;
import Banco.Logic.Client;
import java.util.ArrayList;
import java.util.List;

public class CuentaModel {
List<Account> cuentas;
Client cliente;
    public CuentaModel() {
        cuentas=null;
        cliente=null;
    }

    public Client getCliente() {
        return cliente;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    public List<Account> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Account> cuentas) {
        this.cuentas = cuentas;
    }
}
