package td1.commandes;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import td1.paires.Paire;

public class Commande {
    private List<Paire<Produit, Integer>> lignes;

    //Exercice 1 - Question 2
    private static final Function<Paire<Produit, Integer>, String> formatteurLigne = l-> String.format("%s %d",  l.fst(), l.snd())


    public Commande() {
        this.lignes = new ArrayList<>();
    }

    public Commande ajouter(Produit p, int q) {
        lignes.add(new Paire<>(p, q));
        return this;
    }

    public List<Paire<Produit, Integer>> lignes() {
        return lignes;
    }

    @Override
    public String toString() {
        //Exercice 1 - Question 2
        return lignes.stream().map(l -> formatteurLigne.apply(l)).collect(Collectors.joining("\n")).toString();
        /*
        StringBuilder str = new StringBuilder();
        str.append("Commande\n");
        for (Paire<Produit, Integer> ligne : lignes) {
            str.append(String.format("%s x%d\n", ligne.fst(), ligne.snd()));
        }
        return str.toString();
         */
    }

    /**
     * cumule les lignes en fonction des produits
     */
    public Commande normaliser() {
        Map<Produit, Integer> lignesCumulees = new HashMap<>();
        for (Paire<Produit, Integer> ligne : lignes) {
            Produit p = ligne.fst();
            int qte = ligne.snd();
            if (lignesCumulees.containsKey(ligne.fst())) {
                lignesCumulees.put(p, lignesCumulees.get(p) + qte);
            } else {
                lignesCumulees.put(p, qte);
            }
        }
        Commande commandeNormalisee = new Commande();
        for (Produit p : lignesCumulees.keySet()) {
            commandeNormalisee.ajouter(p, lignesCumulees.get(p));
        }
        return commandeNormalisee;
    }

    public Double cout(Function<Paire<Produit, Integer>, Double> calculLigne) {
        return lignes.stream()
                .map(l -> calculLigne.apply(l))
                .reduce(0.0, (x, y)->x+y);
        /*double rtr = 0;
        for (Paire<Produit, Integer> l : normaliser().lignes) {
            rtr += calculLigne.apply(l);
        }
        return rtr;*/
    }

    public String affiche(Function<Paire<Produit, Integer>, Double> calculLigne) {
        Commande c = this.normaliser();
        final String HLINE = "+------------+------------+-----+------------+--------+------------+\n";
        StringBuilder str = new StringBuilder();
        str.append("\n\nCommande\n");
        str.append(HLINE);
        str.append("+ nom        + prix       + qté + prix ht    + tva    + prix ttc   +\n");
        str.append(HLINE);
        for (Paire<Produit, Integer> ligne : c.lignes) {
            str.append(String.format("+ %10s + %10.2f + %3d + %10.2f + %5.2f%% + %10.2f +\n", ligne.fst(), // nom
                    ligne.fst().prix(), // prix unitaire
                    ligne.snd(), // qté
                    ligne.fst().prix() * ligne.snd(), // prix ht
                    ligne.fst().cat().tva() * 100, // tva
                    calculLigne.apply(ligne)));
        }
        str.append(HLINE);
        str.append(String.format("Total : %10.2f", c.cout(calculLigne)));
        return str.toString();
    }

    public <T,U>Map<T, List<U>> regrouper(List<Paire<T, U>> ligne)
    {
        Map<T,List<U>> res = new HashMap<>();
        //On parcourt une liste de ligne
        ligne.forEach(l -> res.computeIfAbsent(l.fst(),  a -> new ArrayList<>()).add(l.snd()));
        return res;
    }



}
