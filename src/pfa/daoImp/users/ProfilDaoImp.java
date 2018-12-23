/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.daoImp.users;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import pfa.dao.users.ProfilDao;
import pfa.daoImp.DaoImp;
import pfa.model.users.Profil;

/**
 *
 * @author karim
 */
@Repository("daoProfil")
public class ProfilDaoImp extends DaoImp implements ProfilDao {
    private Profil profil;
    private List<Profil> allProfils;
    private final String CLASS_NAME = "Profil";
    //**************************************************************************************//       
    //**************************************************************************************//
    public ProfilDaoImp() {
        super();
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public Profil findById(int id) {
     this.profil = null;
        try {
            beginTransaction();
            this.profil = this.session.get(Profil.class,id);
            commitTransaction();
            closeSession();
        } catch (Exception e) {
        }
     return this.profil;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public List<Profil> findAll() {
        this.allProfils = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("from "+CLASS_NAME);
            allProfils = (ArrayList<Profil>) query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
        return this.allProfils;
    }
    //**************************************************************************************//       
    //**************************************************************************************//

    @Override
    public boolean add(Profil profil) {
        boolean r;
        try {
            System.err.println("DKHLNA");
            this.beginTransaction();
            this.session.save(profil);
            System.err.println("T9AD");
            this.commitTransaction();
            this.closeSession();
            r = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            this.transaction.rollback();
            r = false;
        }
        return r;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean update(Profil profil) {
        boolean r;
        try {
            this.beginTransaction();
            this.session.update(profil);
            this.commitTransaction();
            this.closeSession();
            r = true;
        } catch (Exception e) {
            this.transaction.rollback();
            r = false;
        }
        return r;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean delete(Profil profil) {
        boolean r;
        try {
            this.beginTransaction();
            this.session.delete(profil);
            this.commitTransaction();
            this.closeSession();
            r = true;
        } catch (Exception e) {
            this.transaction.rollback();
            r = false;
        }
        return r;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    @Autowired(required = true)
    public void setSessionFactory(@Qualifier("sessionFactory")SessionFactory sessionFactory) {
       this.sessionFactory  = sessionFactory;
    }

}
