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
import pfa.dao.dossier.DossierOaDao;
import pfa.daoImp.DaoImp;
import pfa.model.dossier.DossierOa;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
@Repository("oaDao")
public class DossierOaDaoImp extends DaoImp implements DossierOaDao{
    private DossierOa dossierOa;
    private ArrayList<DossierOa> allDossierOas;
    private final String CLASS_NAME = "DossierOa";
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public DossierOa findById(int id) {
        this.dossierOa = null;
        try {
            this.beginTransaction();
            this.dossierOa = this.session.get(DossierOa.class,id);
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
        return this.dossierOa;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<DossierOa> findAll() {
        this.allDossierOas = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM " + CLASS_NAME+ " as d WHERE d.idDossier NOT IN (select distinct a.dossier.idDossier FROM Archive as a)");
            this.allDossierOas = (ArrayList<DossierOa>) this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
       return this.allDossierOas;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<DossierOa> findAll(Utilisateur user) {
        this.allDossierOas = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM " + CLASS_NAME + " as d,Utilisateur as u WHERE d.idDossier NOT IN (select distinct a.dossier.idDossier FROM Archive as a)"
                    + " AND u.matricule = :mat AND u in elements(d.responsables)");
            this.query.setParameter("mat", user.getMatricule());
            this.allDossierOas = (ArrayList<DossierOa>) this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
       return this.allDossierOas;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean add(DossierOa dossier) {
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
    public boolean update(DossierOa dossier) {
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
