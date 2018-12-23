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
import pfa.dao.dossier.DossierCarrefourDao;
import pfa.dao.dossier.DossierDao;
import pfa.daoImp.DaoImp;
import pfa.model.dossier.Dossier;
import pfa.model.dossier.DossierCarrefour;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
@Repository("carrefourDao")
public class DossierCarrefourDaoImp extends DaoImp implements DossierCarrefourDao{
    //**************************************************************************************//       
    //**************************************************************************************//
    private DossierCarrefour DossierCarrefour;
    private ArrayList<DossierCarrefour> allDossierCarrefours;
    private final String CLASS_NAME = "DossierCarrefour";
    //**************************************************************************************//       
    //**************************************************************************************//
    public DossierCarrefourDaoImp() {
        super();
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public DossierCarrefour findById(int id) {
        this.DossierCarrefour = null;
        try {
            this.beginTransaction();
            this.DossierCarrefour = this.session.get(DossierCarrefour.class,id);
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }

        return  this.DossierCarrefour;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<DossierCarrefour> findAll() {
        this.allDossierCarrefours = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM " + CLASS_NAME+ " as d WHERE d.idDossier NOT IN (select distinct a.dossier.idDossier FROM Archive as a)");
            this.allDossierCarrefours = (ArrayList<DossierCarrefour>) this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
       return this.allDossierCarrefours;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    
    @Override
    public ArrayList<DossierCarrefour> findAll(Utilisateur user) {
        this.allDossierCarrefours = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM " + CLASS_NAME + " as d,Utilisateur as u WHERE d.idDossier NOT IN (select distinct a.dossier.idDossier FROM Archive as a)"
                    + " AND u.matricule = :mat AND u in elements(d.responsables)");
            this.query.setParameter("mat", user.getMatricule());
            this.allDossierCarrefours = (ArrayList<DossierCarrefour>) this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
       return this.allDossierCarrefours;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean add(DossierCarrefour dossier) {
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
    public boolean update(DossierCarrefour dossier) {
        boolean r = false;
        try {
            this.beginTransaction();
            this.session.update(dossier);
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
