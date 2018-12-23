/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.model.users;

/**
 *
 * @author karim
 */
public enum SituationFamiliale {
    CELIBATAIRE(1,"Célibataire"),MARIEE(2,"Marié(e)");
    private final int idSituation;
    private final String value;
    
    private SituationFamiliale(int idSituation,String value) {
        this.idSituation = idSituation;
        this.value = value;
    }

    public int getIdSituation() {
        return idSituation;
    }

    public String getValue() {
        return value;
    }

    
}
