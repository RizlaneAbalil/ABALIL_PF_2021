package main.java.TD2.universite;

import java.util.HashSet;
import java.util.Set;
import main.java.TD2.universite.UE;
import main.java.TD2.universite.Etudiant;

public class Annee {
    private Set<UE> ues;
    private Set<Etudiant> etudiants;

    public Annee(Set<UE> ues) {
        this.ues = ues;
        this.etudiants = new HashSet<>();
    }

    public Set<UE> ues() {
        return ues;
    }

    public Set<Etudiant> etudiants() {
        return etudiants;
    }

    void inscrire(Etudiant e) {
        etudiants.add(e);
    }
}
