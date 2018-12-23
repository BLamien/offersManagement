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
import pfa.dao.dossier.DossierCpsTravauxDao;
import pfa.daoImp.DaoImp;
import pfa.model.dossier.DossierCpsEtude;
import pfa.model.dossier.DossierCpsTravaux;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
@Repository("cpsTravauxDao")
public class DossierCpsTravauxDaoImp extends DaoImp implements DossierCpsTravauxDao{
    //**************************************************************************************//       
    //**************************************************************************************//
    private DossierCpsTravaux dossierCpsTravaux;
    private ArrayList<DossierCpsTravaux> allDossierCpsTravauxs;
    private final String CLASS_NAME = "DossierCpsTravaux";
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public DossierCpsTravaux findById(int id) {
      this.dossierCpsTravaux = null;
        try {
            this.beginTransaction();
            this.dossierCpsTravaux = this.session.get(DossierCpsTravaux.class,id);
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
      
     return this.dossierCpsTravaux;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<DossierCpsTravaux> findAll() {
        this.allDossierCpsTravauxs = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM " + CLASS_NAME+ " as d WHERE d.idDossier NOT IN (select distinct a.dossier.idDossier FROM Archive as a)");
            this.allDossierCpsTravauxs = (ArrayList<DossierCpsTravaux>)this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
        return this.allDossierCpsTravauxs;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<DossierCpsTravaux> findAll(Utilisateur user) {
        this.allDossierCpsTravauxs = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM " + CLASS_NAME + " as d,Utilisateur as u WHERE d.idDossier NOT IN (select distinct a.dossier.idDossier FROM Archive as a)"
                    + " AND u.matricule = :mat AND u in elements(d.responsables)");
            this.query.setParameter("mat", user.getMatricule());
            this.allDossierCpsTravauxs = (ArrayList<DossierCpsTravaux>)this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
        return this.allDossierCpsTravauxs;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean add(DossierCpsTravaux dossier) {
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
    public boolean update(DossierCpsTravaux dossier) {
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
