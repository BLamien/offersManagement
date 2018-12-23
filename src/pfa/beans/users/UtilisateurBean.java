
package pfa.beans.users;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pfa.model.users.Utilisateur;
import pfa.service.dossier.ServiceDossier;
import pfa.service.users.ServiceUtilisateur;
import pfa.service.util.UserSession;

@javax.annotation.ManagedBean("userbean")
@RequestScoped
@ManagedBean(name = "userbean")
public class UtilisateurBean {
    
    private String cin;
    private String password;
    private String userType;
    private static HttpSession session =null;
    private static HttpServletRequest httpservletrequest;
    private Utilisateur user;
    
    @Autowired(required = true)
    @Qualifier("serviceUser")
    private ServiceUtilisateur serviceUtilisateur;
    
    @Autowired(required = true)
    @Qualifier("serviceSession")
    private UserSession usersess;
    
    @Autowired(required = true)
    @Qualifier("serviceDossier")
    private ServiceDossier serviceDossier;
    
    public void login() throws IOException{
        user=serviceUtilisateur.loginUtilisateur(cin, password);
          if (user!=null) {
          usersess.setSession((HttpSession)
          FacesContext.
          getCurrentInstance().
          getExternalContext().
          getSession(false));
          usersess.addUsertoSession(user);
          serviceDossier.init();
          if(user.getProfil().getIdProfil().equals(2))
                            FacesContext.getCurrentInstance().getExternalContext().redirect("utilisateurs"); 
          else
                            FacesContext.getCurrentInstance().getExternalContext().redirect("dossiers"); 
        } else {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"CIN ou Mot de passe incorrect",""));
           
        }
    }
    public void logOut() throws IOException
    {
     usersess.destroySession();
    FacesContext.getCurrentInstance().getExternalContext().redirect("login"); 
    }
    public Utilisateur logedUser()
    {
        return usersess.getUser();
    }
    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public static HttpSession getSession() {
        return session;
    }

    public static void setSession(HttpSession session) {
        UtilisateurBean.session = session;
    }

    public static HttpServletRequest getHttpservletrequest() {
        return httpservletrequest;
    }

    public static void setHttpservletrequest(HttpServletRequest httpservletrequest) {
        UtilisateurBean.httpservletrequest = httpservletrequest;
    }
    
    
}
