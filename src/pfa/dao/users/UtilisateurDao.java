/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.dao.users;

import java.util.ArrayList;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
public interface UtilisateurDao {
    Utilisateur findByMatricule(String matricule);
    ArrayList<Utilisateur> findAll();
    boolean add(Utilisateur utilisateur);
    boolean update(Utilisateur utilisateur);
}
