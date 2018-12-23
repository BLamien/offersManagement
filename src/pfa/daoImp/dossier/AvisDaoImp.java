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
import pfa.dao.dossier.AvisDao;
import pfa.daoImp.DaoImp;
import pfa.model.dossier.Avis;

/**
 *
 * @author karim
 */
@Repository("avisDao")
public class AvisDaoImp extends DaoImp  implements AvisDao{
    public static final int EN_COURS = 1;
    public static final int APPROUVE = 2;
    public static final int NON_APPROUVE = 3;
    //**************************************************************************************//       
    //**************************************************************************************//
    private static final String CLASS_NAME="Avis";
    private Avis avis;
    private ArrayList<Avis> allAvis;
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public Avis findById(int id) {
     this.avis = null;
        try {
            this.beginTransaction();
            this.avis = this.session.get(Avis.class, id);
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
        return this.avis;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<Avis> findAll() {
       this.allAvis = null;
            try {
               this.beginTransaction();
               this.query = this.session.createQuery("from "+CLASS_NAME);
               this.allAvis= (ArrayList<Avis>) query.list();
               this.commitTransaction();
               this.closeSession();
           } catch (Exception e) {
               this.transaction.rollback();
           }
       return this.allAvis;
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
