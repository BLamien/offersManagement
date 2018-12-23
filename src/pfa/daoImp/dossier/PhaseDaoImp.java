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
import pfa.dao.dossier.PhaseDao;
import pfa.daoImp.DaoImp;
import pfa.model.dossier.Phase;

/**
 *
 * @author karim
 */
@Repository("phaseDao")
public class PhaseDaoImp extends DaoImp implements PhaseDao{
    private Phase phase;
    private ArrayList<Phase> allPhases;
    private final String CLASS_NAME = "Phase";
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public Phase findById(int id) {
     this.phase = null;
        try {
            this.beginTransaction();
            this.phase = this.session.get(Phase.class,id);
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
     return this.phase;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<Phase> findAll() {
    this.allPhases = null;
           try {
               this.beginTransaction();
               this.query = this.session.createQuery("from "+CLASS_NAME);
               this.allPhases= (ArrayList<Phase>) query.list();
               this.commitTransaction();
               this.closeSession();
           } catch (Exception e) {
               this.transaction.rollback();
           } 
    return this.allPhases;
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
