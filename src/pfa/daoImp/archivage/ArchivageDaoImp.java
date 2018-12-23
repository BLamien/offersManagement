/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.daoImp.archivage;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import pfa.dao.archivage.ArchivageDao;
import pfa.daoImp.DaoImp;
import pfa.model.archivage.Archive;

/**
 *
 * @author karim
 */
@Repository("archivageDao")
public class ArchivageDaoImp extends DaoImp implements ArchivageDao{
    private Archive archive;
    //**************************************************************************************//       
    //**************************************************************************************//   
    @Override
    public boolean add(Archive archive) {
     if(archive == null)
         return false;
        try {
            this.beginTransaction();
            this.session.save(archive);
            this.commitTransaction();
            this.closeSession();
            return true;
        } catch (Exception e) {
            this.transaction.rollback();
            System.out.println(e.getMessage());
            return false;
        }
    }
    //**************************************************************************************//       
    //**************************************************************************************//   
    @Override
    public boolean update(Archive archive) {
     if(archive == null)
         return false;
        try {
            this.beginTransaction();
            this.session.saveOrUpdate(archive);
            this.commitTransaction();
            this.closeSession();
            return true;
        } catch (Exception e) {
            this.transaction.rollback();
            return false;
        }
    }     
    //**************************************************************************************//       
    //**************************************************************************************//   
    @Override
    @Autowired(required = true)
    public void setSessionFactory(@Qualifier("sessionFactory")SessionFactory sessionFactory) {
       this.sessionFactory  = sessionFactory;
    } 

    @Override
    public Archive findById(int idDossier) {
       this.archive = null;
        try {
            this.beginTransaction();
            this.query = this.session.createQuery("FROM Archive WHERE dossier.idDossier = :idDossier");
            this.query.setParameter("idDossier",idDossier);
            this.query.setMaxResults(1);
            this.archive = (Archive)this.query.uniqueResult();
        } catch (Exception e) {
            this.transaction.rollback();
            this.archive = null;
        }
        return this.archive;
    }
}
