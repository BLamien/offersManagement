package pfa.model.users;
// Generated 23 avr. 2016 02:24:37 by Hibernate Tools 4.3.1


import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;

/**
 * DroitAcces generated by hbm2java
 */
@Entity
@Table(name = "droit_acces")
public class DroitAcces  implements java.io.Serializable {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id_droit")
     private Integer idDroit;
    
     private String libele;

    public DroitAcces() {
    }
    //**************************************************************************************//       
    //**************************************************************************************//     
    public DroitAcces(String libele) {
        this.libele = libele;
    }

    public DroitAcces(String libele, List<Profil> profils) {
        this.libele = libele;
    }
    //**************************************************************************************//     
    //**************************************************************************************//      

    public Integer getIdDroit() {
        return this.idDroit;
    }

    public void setIdDroit(Integer idDroit) {
        this.idDroit = idDroit;
    }

    
    public String getLibele() {
        return this.libele;
    }
    
    public void setLibele(String libele) {
        this.libele = libele;
    }
    
    //**************************************************************************************//     
    //**************************************************************************************//  

    
    //**************************************************************************************//     
    //**************************************************************************************//  

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.idDroit != null ? this.idDroit.hashCode() : 0);
        hash = 43 * hash + (this.libele != null ? this.libele.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DroitAcces other = (DroitAcces) obj;
        if (this.idDroit != other.idDroit && (this.idDroit == null || !this.idDroit.equals(other.idDroit))) {
            return false;
        }
        if ((this.libele == null) ? (other.libele != null) : !this.libele.equals(other.libele)) {
            return false;
        }
        return true;
    }
}

