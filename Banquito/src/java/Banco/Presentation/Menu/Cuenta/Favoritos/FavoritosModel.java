/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Presentation.Menu.Cuenta.Favoritos;

import Banco.Logic.Account;
import Banco.Logic.Transaction;

public class FavoritosModel {
    Account account;
    Account Ownaccount;
    Transaction transaction;
    float propio;
    float nuevo;

    public float getPropio() {
        return propio;
    }

    public void setPropio(float propio) {
        this.propio = propio;
    }

    public float getNuevo() {
        return nuevo;
    }

    public void setNuevo(float nuevo) {
        this.nuevo = nuevo;
    }

    
    public FavoritosModel() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Account getOwnaccount() {
        return Ownaccount;
    }

    public void setOwnaccount(Account Ownaccount) {
        this.Ownaccount = Ownaccount;
    }
    
    @Override
    public String toString(){
        return "Cuenta: "+account.getNumber()+" con Propietario: "+account.getClient().getName();
    }
    
}
