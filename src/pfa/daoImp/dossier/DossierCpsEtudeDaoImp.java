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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pfa.dao.dossier.DossierCpsEtudeDao;
import pfa.daoImp.DaoImp;
import pfa.model.dossier.DossierCpsEtude;
import pfa.model.dossier.DossierOa;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
@Repository(value = "cpsEtudeDao")
public class DossierCpsEtudeDaoImp extends DaoImp implements DossierCpsEtudeDao{
    //**************************************************************************************//       
    //**************************************************************************************//
    private DossierCpsEtude dossierCpsEtude;
    private ArrayList<DossierCpsEtude> allDossierCpsEtudes;
    private final String CLASS_NAME = "DossierCpsEtude";
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public DossierCpsEtude findById(int id) {
      this.dossierCpsEtude = null;
      try {
            this.beginTransaction();
            this.dossierCpsEtude = this.session.get(DossierCpsEtude.class,id);
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
        }
      return this.dossierCpsEtude;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<DossierCpsEtude> findAll() {
       this.allDossierCpsEtudes = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM " + CLASS_NAME+ " as d WHERE d.idDossier NOT IN (select distinct a.dossier.idDossier FROM Archive as a)");
            this.allDossierCpsEtudes = (ArrayList<DossierCpsEtude>) this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
        }
       return this.allDossierCpsEtudes;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<DossierCpsEtude> findAll(Utilisateur user) {
       this.allDossierCpsEtudes = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM " + CLASS_NAME + " as d,Utilisateur as u WHERE d.idDossier NOT IN (select distinct a.dossier.idDossier FROM Archive as a)"
                    + " AND u.matricule = :mat AND u in elements(d.responsables)");
            this.query.setParameter("mat", user.getMatricule());
            this.allDossierCpsEtudes = (ArrayList<DossierCpsEtude>) this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
        }
       return this.allDossierCpsEtudes;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean add(DossierCpsEtude dossier) {
        boolean r;
        try {
            this.beginTransaction();
            this.session.save(dossier);
            this.commitTransaction();
            this.closeSession();
            r = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            r=false;
        }

        return r;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean update(DossierCpsEtude dossier) {
        boolean r;
        try {
            this.beginTransaction();
            this.session.update(dossier);
            this.commitTransaction();
            this.closeSession();
            r = true;
        } catch (Exception e) {
            r = false;
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
