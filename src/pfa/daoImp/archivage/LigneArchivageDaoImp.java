/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.daoImp.archivage;

import java.util.ArrayList;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import pfa.dao.archivage.LigneArchivageDao;
import pfa.daoImp.DaoImp;
import pfa.model.archivage.LigneArchivage;
import pfa.model.archivage.RangeArchivage;

/**
 *
 * @author karim
 */
@Repository("ligneArchivageDao")
public class LigneArchivageDaoImp extends DaoImp implements LigneArchivageDao{
    //**************************************************************************************//       
    //**************************************************************************************//
    private LigneArchivage ligneArchivage;
    private ArrayList<LigneArchivage> allLigneArchivages;
    private  final String CLASS_NAME="LigneArchivage";
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public LigneArchivage findById(int id) {
        this.ligneArchivage  = null;
        try {
            this.beginTransaction();
            this.ligneArchivage = this.session.get(LigneArchivage.class, id);
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
        
       return this.ligneArchivage;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<LigneArchivage> findAll() {
       this.allLigneArchivages = null;
         try {
               this.beginTransaction();
               this.query = this.session.createQuery("from "+CLASS_NAME);
               this.allLigneArchivages= (ArrayList<LigneArchivage>) query.list();
               this.commitTransaction();
               this.closeSession();
           } catch (Exception e) {
               this.transaction.rollback();
           }  
       return this.allLigneArchivages;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean add(LigneArchivage ligneArchivage) {
        boolean b = false;
        if(ligneArchivage != null)
        try {
            this.beginTransaction();
            this.session.save(ligneArchivage);
            this.commitTransaction();
            this.closeSession();
            b = true;
        } catch (Exception e) {
            this.transaction.rollback();
            b = false;
        }
        return b;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean update(LigneArchivage ligneArchivage) {
        boolean b;
        try {
            this.beginTransaction();
            this.session.update(ligneArchivage);
            this.commitTransaction();
            this.closeSession();
            b = true;
        } catch (Exception e) {
            this.transaction.rollback();
            b = false;
        }
        return b;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean delete(LigneArchivage ligneArchivage) {
        boolean b;
        try {
            this.beginTransaction();
            this.session.delete(ligneArchivage);
            this.commitTransaction();
            this.closeSession();
            b = true;
        } catch (Exception e) {
            this.transaction.rollback();
            b = false;
        }
        return b;
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
