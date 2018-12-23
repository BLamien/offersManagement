/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.daoImp.dossier;

import java.util.ArrayList;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import pfa.dao.dossier.NatureDao;
import pfa.daoImp.DaoImp;
import pfa.model.dossier.Nature;

/**
 *
 * @author karim
 */
@Repository("natureDao")
public class NatureDaoImp extends DaoImp implements NatureDao{
    private Nature nature;
    private ArrayList<Nature> allNatures;
    private final String CLASS_NAME = "Nature";
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public Nature findById(int id) {
     this.nature = null;
        try {
            this.beginTransaction();
            this.nature = this.session.get(Nature.class,id);
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
     return this.nature;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<Nature> findAll() {
        this.allNatures = null;
          try {
               this.beginTransaction();
               this.query = this.session.createQuery("from "+CLASS_NAME);
               this.allNatures= (ArrayList<Nature>) query.list();
               this.commitTransaction();
               this.closeSession();
           } catch (Exception e) {
               this.transaction.rollback();
           }  
        return this.allNatures;
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
