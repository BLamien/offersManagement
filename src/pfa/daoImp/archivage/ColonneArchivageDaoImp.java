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
import pfa.dao.archivage.ColonneArchivageDao;
import pfa.daoImp.DaoImp;
import pfa.model.archivage.ColonneArchivage;

/**
 *
 * @author karim
 */
@Repository("colonneArchivageDao")
public class ColonneArchivageDaoImp extends DaoImp implements ColonneArchivageDao{
   private ColonneArchivage colonneArchivage;
    private ArrayList<ColonneArchivage> allColonneArchivages;
    private  final String CLASS_NAME="ColonneArchivage";
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ColonneArchivage findById(int id) {
        this.colonneArchivage = null;
        try {
            this.beginTransaction();
            this.colonneArchivage = this.session.get(ColonneArchivage.class,id);
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
        return this.colonneArchivage;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<ColonneArchivage> findAll() {
       this.allColonneArchivages = null;
        try {
               this.beginTransaction();
               this.query = this.session.createQuery("from "+CLASS_NAME);
               this.allColonneArchivages= (ArrayList<ColonneArchivage>) query.list();
               this.commitTransaction();
               this.closeSession();
           } catch (Exception e) {
               this.transaction.rollback();
           }  
       return this.allColonneArchivages;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean add(ColonneArchivage colonneArchivage) {
        boolean b;
        try {
            this.beginTransaction();
            this.session.save(colonneArchivage);
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
    public boolean update(ColonneArchivage colonneArchivage) {
        boolean b;
        try {
            this.beginTransaction();
            this.session.update(colonneArchivage);
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
    public boolean delete(ColonneArchivage colonneArchivage) {
        boolean b;
        try {
            this.beginTransaction();
            this.session.delete(colonneArchivage);
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
}
