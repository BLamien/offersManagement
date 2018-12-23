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
import pfa.dao.users.DroitAccesDao;
import pfa.daoImp.DaoImp;
import pfa.model.users.DroitAcces;
import pfa.model.users.Profil;

/**
 *
 * @author karim
 */
@Repository("daoDroitAcces")
public class DroitAccesDaoImp extends DaoImp implements DroitAccesDao{
    private List<DroitAcces> allDroitAcceses;
    private DroitAcces droitAcces;
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public DroitAcces findById(int id) {
        this.droitAcces = null;
            try {
            beginTransaction();
            this.droitAcces = (DroitAcces)this.session.get(DroitAcces.class,id);
            commitTransaction();
            closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
             System.err.println(e.getMessage());
        }
        return this.droitAcces;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public List<DroitAcces> findAll() {
        this.allDroitAcceses = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM DroitAcces");
            this.allDroitAcceses = (List<DroitAcces>)this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
        return this.allDroitAcceses;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    @Autowired(required = true)
    public void setSessionFactory(@Qualifier("sessionFactory")SessionFactory sessionFactory) {
       this.sessionFactory  = sessionFactory;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
}
