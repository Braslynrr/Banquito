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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gaira
 */
@Entity
@Table(name = "cashier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cashier.findAll", query = "SELECT c FROM Cashier c")
    , @NamedQuery(name = "Cashier.findByName", query = "SELECT c FROM Cashier c WHERE c.name = :name")
    , @NamedQuery(name = "Cashier.findByCod", query = "SELECT c FROM Cashier c WHERE c.cod = :cod")})
public class Cashier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name")
    private String name;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "cod")
    private String cod;
    @JoinColumns({
        @JoinColumn(name = "User_id", referencedColumnName = "id")
        , @JoinColumn(name = "User_id", referencedColumnName = "id")})
    @ManyToOne(optional = false)
    private User user;

    public Cashier() {
    }

    public Cashier(String cod) {
        this.cod = cod;
    }

    public Cashier(String cod, String name) {
        this.cod = cod;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cod != null ? cod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cashier)) {
            return false;
        }
        Cashier other = (Cashier) object;
        if ((this.cod == null && other.cod != null) || (this.cod != null && !this.cod.equals(other.cod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Banco.Logic.Cashier[ cod=" + cod + " ]";
    }
    
}
