/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Logic;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gaira
 */
@Entity
@Table(name = "favorites")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Favorites.findAll", query = "SELECT f FROM Favorites f")
    , @NamedQuery(name = "Favorites.findByOwn", query = "SELECT f FROM Favorites f WHERE f.own = :own")})
public class Favorites implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Own")
    private Integer own;
    @JoinColumn(name = "Own", referencedColumnName = "number", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Account account;
    @JoinColumn(name = "favorite_account", referencedColumnName = "number")
    @ManyToOne(optional = false)
    private Account favoriteAccount;

    public Favorites() {
    }

    public Favorites(Integer own) {
        this.own = own;
    }

    public Integer getOwn() {
        return own;
    }

    public void setOwn(Integer own) {
        this.own = own;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getFavoriteAccount() {
        return favoriteAccount;
    }

    public void setFavoriteAccount(Account favoriteAccount) {
        this.favoriteAccount = favoriteAccount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (own != null ? own.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Favorites)) {
            return false;
        }
        Favorites other = (Favorites) object;
        if ((this.own == null && other.own != null) || (this.own != null && !this.own.equals(other.own))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Banco.Logic.Favorites[ own=" + own + " ]";
    }
    
}
