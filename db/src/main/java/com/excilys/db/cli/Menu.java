package com.excilys.db.cli;

public enum Menu {
	afficherCompagnie(1),afficherOrdinateurs(2),ajouterOrdinateur(3),supprimerOrdinateur(4),
	afficherOrdinateur(5),mettreAJour(6),quitter(7);
	 private final int value;
	
	public int getValue() {
        return value;
    }
	
    private Menu(int i) {
    	value = i;
    }
}
