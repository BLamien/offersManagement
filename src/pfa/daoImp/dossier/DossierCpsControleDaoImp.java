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
import pfa.dao.dossier.DossierCpsControleDao;
import pfa.daoImp.DaoImp;
import pfa.model.dossier.DossierCpsControle;
import pfa.model.dossier.DossierCpsEtude;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
@Repository("cpsControleDao")
public class DossierCpsControleDaoImp extends DaoImp implements DossierCpsControleDao{
    private final String CLASS_NAME = "DossierCpsControle";
    private DossierCpsControle dossierCpsControle;
    private ArrayList<DossierCpsControle> allDossierCpsControles;
    
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public DossierCpsControle findById(int id) {
        this.dossierCpsControle = null;
        try {
            this.beginTransaction();
            this.dossierCpsControle = this.session.get(DossierCpsControle.class,id);
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
        return this.dossierCpsControle;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<DossierCpsControle> findAll() {
        this.allDossierCpsControles = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM " + CLASS_NAME+ " as d WHERE d.idDossier NOT IN (select distinct a.dossier.idDossier FROM Archive as a)");
            this.allDossierCpsControles= (ArrayList<DossierCpsControle>) this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
        return this.allDossierCpsControles;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<DossierCpsControle> findAll(Utilisateur user) {
        this.allDossierCpsControles = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM " + CLASS_NAME + " as d,Utilisateur as u WHERE d.idDossier NOT IN (select distinct a.dossier.idDossier FROM Archive as a)"
                    + " AND u.matricule = :mat AND u in elements(d.responsables)");
            this.query.setParameter("mat", user.getMatricule());
            this.allDossierCpsControles= (ArrayList<DossierCpsControle>) this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        }
        return this.allDossierCpsControles;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean add(DossierCpsControle dossier) {
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
    public boolean update(DossierCpsControle dossier) {
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
