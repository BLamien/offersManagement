/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.dao.dossier;

import java.util.ArrayList;
import java.util.List;
import pfa.model.dossier.DossierCpsControle;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
public interface DossierCpsControleDao {
    DossierCpsControle findById(int id);
    ArrayList<DossierCpsControle> findAll();
    List<DossierCpsControle> findAll(Utilisateur user);
    boolean add(DossierCpsControle dossier);
    boolean update(DossierCpsControle dossier);  
}
