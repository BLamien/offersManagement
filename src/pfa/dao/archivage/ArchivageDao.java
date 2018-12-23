/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.dao.archivage;

import pfa.model.archivage.Archive;

/**
 *
 * @author karim
 */
public interface ArchivageDao {
public boolean add(Archive archive);
public boolean update(Archive archive);
public Archive findById(int idDossier);
}
