/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Brazza
 */
@Entity
@Table(name = "account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
    , @NamedQuery(name = "Account.findByNumber", query = "SELECT a FROM Account a WHERE a.accountPK.number = :number")
    , @NamedQuery(name = "Account.findByBalance", query = "SELECT a FROM Account a WHERE a.balance = :balance")
    , @NamedQuery(name = "Account.findByCurrencycurrencyCode", query = "SELECT a FROM Account a WHERE a.accountPK.currencycurrencyCode = :currencycurrencyCode")})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AccountPK accountPK;
    @Column(name = "balance")
    private Integer balance;
    @JoinColumn(name = "Client_client_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Client clientclientid;
    @JoinColumn(name = "Currency_currencyCode1", referencedColumnName = "currencyCode")
    @ManyToOne(optional = false)
    private Currency currencycurrencyCode1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private List<Transaction> transactionList;

    public Account() {
    }

    public Account(AccountPK accountPK) {
        this.accountPK = accountPK;
    }

    public Account(int number, String currencycurrencyCode) {
        this.accountPK = new AccountPK(number, currencycurrencyCode);
    }

    public AccountPK getAccountPK() {
        return accountPK;
    }

    public void setAccountPK(AccountPK accountPK) {
        this.accountPK = accountPK;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Client getClientclientid() {
        return clientclientid;
    }

    public void setClientclientid(Client clientclientid) {
        this.clientclientid = clientclientid;
    }

    public Currency getCurrencycurrencyCode1() {
        return currencycurrencyCode1;
    }

    public void setCurrencycurrencyCode1(Currency currencycurrencyCode1) {
        this.currencycurrencyCode1 = currencycurrencyCode1;
    }

    @XmlTransient
    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountPK != null ? accountPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.accountPK == null && other.accountPK != null) || (this.accountPK != null && !this.accountPK.equals(other.accountPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logic.Account[ accountPK=" + accountPK + " ]";
    }
    
}
