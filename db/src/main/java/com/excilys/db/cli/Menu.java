package com.excilys.db.cli;

public enum Menu {
	AFFICHER_COMPAGNIE(1),AFFICHER_ORDINATEURS(2),AJOUTER_ORDINATEUR(3),SUPPRIMER_ORDINATEUR(4),
	AFFICHER_ORDINATEUR(5),METTRE_A_JOUR(6),SUPPRIMER_COMPAGNIE(7),QUITTER(8),DEFAUT(9);
	private final int value;

	public int getValue() {
		return value;
	}

	private Menu(int i) {
		value = i;
	}
}
