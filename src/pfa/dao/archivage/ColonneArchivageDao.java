/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.dao.archivage;

import java.util.ArrayList;
import pfa.model.archivage.ColonneArchivage;

/**
 *
 * @author karim
 */
public interface ColonneArchivageDao {
    ColonneArchivage findById(int id);
    ArrayList<ColonneArchivage> findAll();
    boolean add(ColonneArchivage colonneArchivage);
    boolean update(ColonneArchivage colonneArchivage);
    boolean delete(ColonneArchivage colonneArchivage);  
}
