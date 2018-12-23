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
import pfa.dao.dossier.DossierLiaisonDao;
import pfa.daoImp.DaoImp;
import pfa.model.dossier.DossierCarrefour;
import pfa.model.dossier.DossierLiaison;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
@Repository("liaisonDao")
public class DossierLiaisonDaoImp extends DaoImp implements DossierLiaisonDao{
    //**************************************************************************************//       
    //**************************************************************************************//
    private DossierLiaison dossierLiaison;
    private ArrayList<DossierLiaison> allDossierLiaisons;
    private final String CLASS_NAME = "DossierLiaison";
    //**************************************************************************************//       
    //**************************************************************************************//

    public DossierLiaisonDaoImp() {
    }

    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public DossierLiaison findById(int id) {
        this.dossierLiaison = null;
        try {
            this.beginTransaction();
            this.dossierLiaison = this.session.get(DossierLiaison.class,id);
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
        return this.dossierLiaison;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<DossierLiaison> findAll() {
        this.allDossierLiaisons = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM " + CLASS_NAME + " as d WHERE d.idDossier NOT IN (select distinct a.dossier.idDossier FROM Archive as a)");
            this.allDossierLiaisons = (ArrayList<DossierLiaison>) this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
       return this.allDossierLiaisons;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<DossierLiaison> findAll(Utilisateur user) {
        this.allDossierLiaisons = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM " + CLASS_NAME + " as d,Utilisateur as u WHERE d.idDossier NOT IN (select distinct a.dossier.idDossier FROM Archive as a)"
                    + " AND u.matricule = :mat AND u in elements(d.responsables)");
            this.query.setParameter("mat", user.getMatricule());
            this.allDossierLiaisons = (ArrayList<DossierLiaison>) this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
            this.allDossierLiaisons = null;
        }
       return this.allDossierLiaisons;
    } 
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean add(DossierLiaison dossier) {
        boolean r = false;
        try {
            this.beginTransaction();
            this.session.save(dossier);
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
    public boolean update(DossierLiaison dossier) {
        boolean r = false;
        try {
            this.beginTransaction();
            this.session.saveOrUpdate(dossier);
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
