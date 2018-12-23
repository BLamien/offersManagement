/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.dao.dossier;

import java.util.ArrayList;
import java.util.List;
import pfa.model.dossier.DossierCpsEtude;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
public interface DossierCpsEtudeDao {
    DossierCpsEtude findById(int id);
    ArrayList<DossierCpsEtude> findAll();
    List<DossierCpsEtude> findAll(Utilisateur user);
    boolean add(DossierCpsEtude dossier);
    boolean update(DossierCpsEtude dossier);  
}
