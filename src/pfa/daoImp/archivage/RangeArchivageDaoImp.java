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
import pfa.dao.archivage.RangeArchivageDao;
import pfa.daoImp.DaoImp;
import pfa.model.archivage.RangeArchivage;

/**
 *
 * @author karim
 */
@Repository("daoRange")
public class RangeArchivageDaoImp extends DaoImp implements RangeArchivageDao{
    //**************************************************************************************//       
    //**************************************************************************************//
    private RangeArchivage rangeArchivage;
    private ArrayList<RangeArchivage> allRangeArchivages;
    private final String CLASS_NAME="RangeArchivage"; 
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean add(RangeArchivage rangeArchivage) {
      boolean b;
        try {
            this.beginTransaction();
            this.session.save(rangeArchivage);
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
    public RangeArchivage findById(int id) {
        this.rangeArchivage  = null;
        try {
            this.beginTransaction();
            this.rangeArchivage = this.session.get(RangeArchivage.class, id);
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
        
       return this.rangeArchivage;
    }
    //**************************************************************************************//       
    //**************************************************************************************//   
    @Override
    public ArrayList<RangeArchivage> findAll(){
        this.allRangeArchivages = null;
         try {
               this.beginTransaction();
               this.query = this.session.createQuery("from "+CLASS_NAME);
               this.allRangeArchivages= (ArrayList<RangeArchivage>) query.list();
               this.commitTransaction();
               this.closeSession();
           } catch (Exception e) {
               this.transaction.rollback();
           }  
        return this.allRangeArchivages;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public boolean update(RangeArchivage rangeArchivage){
        boolean b = false;
        if( rangeArchivage!= null)
        try {
            this.beginTransaction();
            this.session.update(rangeArchivage);
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
