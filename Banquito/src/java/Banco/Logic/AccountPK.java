/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Brazza
 */
@Embeddable
public class AccountPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "number")
    private int number;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "Currency_currencyCode")
    private String currencycurrencyCode;

    public AccountPK() {
    }

    public AccountPK(int number, String currencycurrencyCode) {
        this.number = number;
        this.currencycurrencyCode = currencycurrencyCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCurrencycurrencyCode() {
        return currencycurrencyCode;
    }

    public void setCurrencycurrencyCode(String currencycurrencyCode) {
        this.currencycurrencyCode = currencycurrencyCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) number;
        hash += (currencycurrencyCode != null ? currencycurrencyCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountPK)) {
            return false;
        }
        AccountPK other = (AccountPK) object;
        if (this.number != other.number) {
            return false;
        }
        if ((this.currencycurrencyCode == null && other.currencycurrencyCode != null) || (this.currencycurrencyCode != null && !this.currencycurrencyCode.equals(other.currencycurrencyCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logic.AccountPK[ number=" + number + ", currencycurrencyCode=" + currencycurrencyCode + " ]";
    }
    
}
