/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.daoImp.dossier;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import pfa.dao.dossier.DpetlDao;
import pfa.daoImp.DaoImp;
import pfa.model.dossier.Dpetl;

/**
 *
 * @author karim
 */
@Repository("dpetlDao")
public class DpetlDaoImp extends DaoImp implements DpetlDao{
    private Dpetl dpetl;
    private ArrayList<Dpetl> allDpetls;
    private final String CLASS_NAME = "Dpetl";
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public Dpetl findById(int id) {
        this.dpetl = null;
        try {
            this.beginTransaction();
            this.dpetl = this.session.get(Dpetl.class,id);
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
        return this.dpetl;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<Dpetl> findAll() {
        this.allDpetls = null;
        try {
            this.beginTransaction();  
            this.query = this.session.createQuery("FROM "+CLASS_NAME);
            this.allDpetls =  (ArrayList<Dpetl>)this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
            System.out.println("Erreur");
        }
       return this.allDpetls;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean add(Dpetl dpetl) {
        boolean r = false;
        try {
            this.beginTransaction();
            this.session.save(dpetl);
            this.commitTransaction();
            this.closeSession();
            r = true;
        } catch (Exception e) {
            this.transaction.rollback();
        }
        return r;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean update(Dpetl dpetl) {
        boolean r = false;
        try {
            this.beginTransaction();
            this.session.update(dpetl);
            this.commitTransaction();
            this.closeSession();
            r = true;
        } catch (Exception e) {
            this.transaction.rollback();
        }
        return r;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean delete(Dpetl dpetl) {
        boolean r = false;
        try {
            this.beginTransaction();
            this.session.delete(dpetl);
            this.commitTransaction();
            this.closeSession();
            r = true;
        } catch (Exception e) {
            this.transaction.rollback();
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
