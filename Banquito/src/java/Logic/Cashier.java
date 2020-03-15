/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Brazza
 */
@Entity
@Table(name = "cashier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cashier.findAll", query = "SELECT c FROM Cashier c")
    , @NamedQuery(name = "Cashier.findByCashierId", query = "SELECT c FROM Cashier c WHERE c.cashierId = :cashierId")
    , @NamedQuery(name = "Cashier.findByName", query = "SELECT c FROM Cashier c WHERE c.name = :name")})
public class Cashier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cashier_id")
    private Integer cashierId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "cashier_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;

    public Cashier() {
    }

    public Cashier(Integer cashierId) {
        this.cashierId = cashierId;
    }

    public Cashier(Integer cashierId, String name) {
        this.cashierId = cashierId;
        this.name = name;
    }

    public Integer getCashierId() {
        return cashierId;
    }

    public void setCashierId(Integer cashierId) {
        this.cashierId = cashierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        hash += (cashierId != null ? cashierId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cashier)) {
            return false;
        }
        Cashier other = (Cashier) object;
        if ((this.cashierId == null && other.cashierId != null) || (this.cashierId != null && !this.cashierId.equals(other.cashierId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Logic.Cashier[ cashierId=" + cashierId + " ]";
    }
    
}
