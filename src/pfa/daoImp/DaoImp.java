/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.daoImp;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pfa.model.dossier.Dossier;

/**
 *
 * @author karim
 */
public abstract class DaoImp {
    // proprietes Hiberntae
    protected SessionFactory sessionFactory;
    protected Session session;
    protected Transaction transaction;
    protected Query query;
    //**************************************************************************************//       
    //**************************************************************************************//      
    // constructeur
    public DaoImp() {

    }
    //**************************************************************************************//       
    //**************************************************************************************//      
    // getters && setters 
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
    //**************************************************************************************//       
    //**************************************************************************************//   
    protected void openSession (){
        if(this.sessionFactory != null){
                try {
                this.session = this.sessionFactory.openSession();	
                } catch (Exception e) {
                  e.getMessage();
                }	
        }
    }
    //**************************************************************************************//       
    //**************************************************************************************//   
    protected void closeSession(){
        try {
            if(this.session.isOpen()){
                this.session.close();
                this.session = null;
            }  
        } catch (Exception e) {
            e.getMessage();
        }
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    protected void beginTransaction(){
        try {
            if(this.session == null) openSession();
            if(this.session.isOpen()){
               this.transaction = this.session.beginTransaction();
            }
        } catch (Exception e) {
        }
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    protected void commitTransaction(){
        try {
             if(this.transaction != null)
             this.transaction.commit();
        } catch (Exception e) {
        }     
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
  
}
