/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.service.util;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfa.model.users.Utilisateur;
import pfa.service.journalisation.ServiceJournalisation;
import pfa.service.users.ServiceUtilisateur;

@Service("serviceSession")
public class UserSession implements Serializable{
     private  HttpSession session;
     private  String realPath ;
     private  HttpServletRequest request;
     private  Utilisateur user;
       
    @Autowired(required = true)
    private ServiceJournalisation serviceLog;
    
    @Autowired(required = true)
    private ServiceUtilisateur serviceUtilisateur;
    public HttpSession getSession() {
          return session;
      }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public String getRealPath() {
        return realPath;
    }
    
      public  HttpServletRequest getRequest()
      {
        return  request;
      }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Utilisateur getUser() { 
        if(getSession()!=null){
        user=(Utilisateur)getSession().getAttribute("user");
        return user;
        }
        return  serviceUtilisateur.findByMatricule("cd2269");
    }
    public void destroySession(){
       // journaliser
        if(this.user != null){
        System.err.println("------------logOut--------------");
        String desc = "deconnexion de l'utilisateur  " + this.user.getNom();
        this.serviceLog.journaliser(user,serviceLog.OP_LOGOUT,serviceLog.TYPE_AUTHENTIFICATION,desc);
        this.user=null;
        addUsertoSession(user);
        }   
    }
    public void addUsertoSession(Utilisateur user)
    {
       session.setAttribute("user", user);
       // journaliser
        if(user != null){
        String desc = "Authentification de l'utilisateur  " + user.getNom();
        this.serviceLog.journaliser(user,serviceLog.OP_LOGIN,serviceLog.TYPE_AUTHENTIFICATION,desc);
        }
    }
}