/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.dao.dossier;

import java.util.ArrayList;
import java.util.List;
import pfa.model.dossier.DossierLiaison;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
public interface DossierLiaisonDao {
    DossierLiaison findById(int id);
    ArrayList<DossierLiaison> findAll();
    List<DossierLiaison> findAll(Utilisateur user);
    boolean add(DossierLiaison dossier);
    boolean update(DossierLiaison dossier);  
}
