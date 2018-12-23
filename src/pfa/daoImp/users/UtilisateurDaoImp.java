/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.daoImp.users;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import pfa.dao.users.UtilisateurDao;
import pfa.daoImp.DaoImp;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
@Repository("utilisateurDao")
public class UtilisateurDaoImp extends DaoImp implements UtilisateurDao{
    //**************************************************************************************//       
    //**************************************************************************************//
    private static final String CLASS_NAME="Utilisateur";
    private Utilisateur utilisateur;
    private ArrayList<Utilisateur> allUtilisateurs;
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public Utilisateur findByMatricule(String matricule) {
        this.utilisateur = null;
        List<Utilisateur> all;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM "+CLASS_NAME+" WHERE matricule = :mat");
            this.query.setParameter("mat",matricule);
            all = this.query.list();
            if(all != null && all.size() > 0){
                this.utilisateur = all.get(0);
            }
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        } 
        return this.utilisateur;
    }
    //**************************************************************************************//
    public Utilisateur findByLoginPassword(String login,String password){
        this.utilisateur = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM "+CLASS_NAME+" WHERE cin = :cin AND password = :password");
            this.query.setParameter("cin",login);
            this.query.setParameter("password",password);
            this.query.setMaxResults(1);
            this.utilisateur = (Utilisateur)this.query.uniqueResult();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            this.transaction.rollback();
        } 
        return this.utilisateur;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public ArrayList<Utilisateur> findAll() {
        this.allUtilisateurs = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM " + CLASS_NAME);
            this.allUtilisateurs = (ArrayList<Utilisateur>) this.query.list();
            this.commitTransaction();
            this.closeSession();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.transaction.rollback();
        }
        return this.allUtilisateurs;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Override
    public boolean add(Utilisateur utilisateur) {
        boolean r = false;
        try {
            this.beginTransaction();
            this.session.save(utilisateur);
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
    public boolean update(Utilisateur utilisateur) {
        boolean r = false;
        try {
            this.beginTransaction();
            this.session.update(utilisateur);
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
