package pfa.model.archivage;
// Generated 23 avr. 2016 02:24:37 by Hibernate Tools 4.3.1

import pfa.model.dossier.Dossier;




/**
 * ArchivageId generated by hbm2java
 */
public class ArchivageId  implements java.io.Serializable {


     private ColonneArchivage colonneArchivage;
     private Dossier dossier;

    public ArchivageId() {
    }

    public ArchivageId(ColonneArchivage colonneArchivage, Dossier dossier) {
        this.colonneArchivage = colonneArchivage;
        this.dossier = dossier;
    }

    public ColonneArchivage getColonneArchivage() {
        return colonneArchivage;
    }

    public void setColonneArchivage(ColonneArchivage colonneArchivage) {
        this.colonneArchivage = colonneArchivage;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.colonneArchivage != null ? this.colonneArchivage.hashCode() : 0);
        hash = 23 * hash + (this.dossier != null ? this.dossier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ArchivageId other = (ArchivageId) obj;
        if (this.colonneArchivage != other.colonneArchivage && (this.colonneArchivage == null || !this.colonneArchivage.equals(other.colonneArchivage))) {
            return false;
        }
        if (this.dossier != other.dossier && (this.dossier == null || !this.dossier.equals(other.dossier))) {
            return false;
        }
        return true;
    }






}


