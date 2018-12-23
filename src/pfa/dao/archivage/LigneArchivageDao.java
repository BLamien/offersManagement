/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.dao.archivage;

import java.util.ArrayList;
import pfa.model.archivage.LigneArchivage;

/**
 *
 * @author karim
 */
public interface LigneArchivageDao {
    LigneArchivage findById(int id);
    ArrayList<LigneArchivage> findAll();
    boolean add(LigneArchivage ligneArchivage);
    boolean update(LigneArchivage ligneArchivage);
    boolean delete(LigneArchivage ligneArchivage);
}
