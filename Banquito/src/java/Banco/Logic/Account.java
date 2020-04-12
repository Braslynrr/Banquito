/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Logic;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gaira
 */
@Entity
@Table(name = "account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
    , @NamedQuery(name = "Account.findByNumber", query = "SELECT a FROM Account a WHERE a.number = :number")
    , @NamedQuery(name = "Account.findByBalance", query = "SELECT a FROM Account a WHERE a.balance = :balance")})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "number")
    private Integer number;
    @Column(name = "balance")
    private Float balance;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    private Favorites favorites;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "favoriteAccount")
    private Collection<Favorites> favoritesCollection;
    @JoinColumns({
        @JoinColumn(name = "Client_client_id", referencedColumnName = "cod")
        , @JoinColumn(name = "Client_client_id", referencedColumnName = "cod")})
    @ManyToOne(optional = false)
    private Client client;
    @JoinColumns({
        @JoinColumn(name = "Currency_currencyCode", referencedColumnName = "currencyCode")
        , @JoinColumn(name = "Currency_currencyCode", referencedColumnName = "currencyCode")})
    @ManyToOne(optional = false)
    private Currency currency;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
    private Collection<Transaction> transactionCollection;

    public Account() {
    }

    public Account(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorites favorites) {
        this.favorites = favorites;
    }

    @XmlTransient
    public Collection<Favorites> getFavoritesCollection() {
        return favoritesCollection;
    }

    public void setFavoritesCollection(Collection<Favorites> favoritesCollection) {
        this.favoritesCollection = favoritesCollection;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @XmlTransient
    public Collection<Transaction> getTransactionCollection() {
        return transactionCollection;
    }

    public void setTransactionCollection(Collection<Transaction> transactionCollection) {
        this.transactionCollection = transactionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (number != null ? number.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.number == null && other.number != null) || (this.number != null && !this.number.equals(other.number))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Banco.Logic.Account[ number=" + number + " ]";
    }

    public void setBalance(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
