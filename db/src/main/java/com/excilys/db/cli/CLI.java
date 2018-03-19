package com.excilys.db.cli;
import com.excilys.db.page.PageCompanies;
import com.excilys.db.page.PageComputer;
import com.excilys.db.service.CompaniesService;
import com.excilys.db.service.ComputerService;
import com.excilys.db.validator.ComputerValidator;
import com.excilys.db.cli.ScanCLI;
import com.excilys.db.exception.CompaniesIdIncorrect;
import com.excilys.db.exception.CompaniesInexistant;
import com.excilys.db.exception.IncoherentDates;
import com.excilys.db.model.Companies;
import com.excilys.db.model.Computer;


import java.util.InputMismatchException;

import java.util.List;
import java.util.Scanner;




/**
 * L'interface en ligne de commande
 * @author flotte
 *
 */
public class CLI {
	static Scanner sc;


	public static void main(String[] args) throws CompaniesInexistant {
		System.out.println("Bienvenue sur le CLI de la base de donnée");
		sc = new Scanner(System.in);
		boolean continu = true;
		while ( continu) {
			menuIntroduction();		
			int choix = choixMenuIntroduction();
			switch (Menu.values()[choix-1]) 
			{
			case afficherCompagnie:
				afficherCompagnies();
				break;
			case afficherOrdinateurs:
				afficherOrdinateurs();
				break;
			case ajouterOrdinateur:
				ajouterOrdinateur();
				break;
			case supprimerOrdinateur:
				supprimerOrdinateur();
				break;
			case afficherOrdinateur:
				afficherOrdinateur();
				break;
			case mettreAJour:
				mettreAJour();
				break;
			case quitter:
				continu = false;
				break;
			default:
			}
		}
		sc.close();
	}	

	public static void menuIntroduction() {
		System.out.println("Liste des commandes :");
		System.out.println("1 - Afficher la liste des compagnies");
		System.out.println("2 - Afficher la liste des ordinateurs");
		System.out.println("3 - Creer un nouvel ordinateur");
		System.out.println("4 - Supprimer un ordinateur");
		System.out.println("5 - Voir les details d'un ordinateur");
		System.out.println("6 - Mettre à jour un ordinateur");
		System.out.println("7 - Quitter le CLI");
	}

	/**
	 * 
	 * @return Le choix de l'utilisateur, 0 si l'utilisateur rentre quelque chose d'incorrect
	 */
	public static int choixMenuIntroduction() {
		int result = 0;
		result = ScanCLI.scanInt(sc);
		if ((result > 7)||(result<1) ){
			System.out.println("Veuillez entrer un nombre correct");
			return 0;
		}
		return result;


	}

	public static void afficherCompagnies() {
		List<Companies> listeCompanies =CompaniesService.INSTANCE.listCompanies();
		PageCompanies page = new PageCompanies(listeCompanies,sc);
		System.out.println("Voici la liste des compagnies ( Q to exit ): ");
		page.afficher();
	}

	public static void afficherOrdinateurs() throws CompaniesInexistant {
		List<Computer> listeOrdinateur = ComputerService.INSTANCE.listComputer();
		PageComputer page = new PageComputer(listeOrdinateur,sc);
		System.out.println("Voici la liste des ordinateurs ( Q to exit ): ");
		page.afficher();
	}

	public static void ajouterOrdinateur() {
		Computer aAjouter = new Computer();
		try {
			aAjouter = ScanCLI.scanComputer();
			ComputerService.INSTANCE.createComputer(aAjouter);
		} catch (IncoherentDates e) {
			System.out.println("Les dates rentrées sont incohérentes");
		}catch (InputMismatchException e){
			System.out.println("Entrez un entier !");
		}catch (CompaniesIdIncorrect e) {
			System.out.println("L'id de la compagnie que vous avez rentré ne correspond à aucune compagnie !");
		} catch (CompaniesInexistant e) {

		}
	}

	private static void supprimerOrdinateur() {
		System.out.println("Donner l'Id de l'ordinateur à supprimer ( -2 pour annuler )");
		int toDelete = -1;
		while (toDelete == -1) {
			toDelete = ScanCLI.scanInt(sc);
		}
		if (toDelete != -2) {
			ComputerService.INSTANCE.deleteComputer(toDelete);
		}
	}

	private static void afficherOrdinateur() throws CompaniesInexistant {
		System.out.println("Donner l'Id de l'ordinateur à afficher ( -2 pour annuler )");
		int toDisplay = -1;
		while (toDisplay == -1) {
			toDisplay = ScanCLI.scanInt(sc);
			if (!ComputerValidator.INSTANCE.exist(toDisplay)) {
				toDisplay = -1;
			}
		}
		if (toDisplay != -2) {
			
			System.out.println(ComputerService.INSTANCE.showDetails(toDisplay));

		}
	}

	private static void mettreAJour() {
		Computer aAjouter = new Computer();
		try {
			aAjouter = ScanCLI.scanComputer();
		} catch (IncoherentDates e) {
			System.out.println("Les dates rentrées sont incohérentes");
		}catch (InputMismatchException e) {
			System.out.println("Vous n'avez pas respecté le format des valeurs attendu");
		} catch (CompaniesIdIncorrect e) {
			System.out.println("L'id de la compagnie que vous avez rentré ne correspond à aucune compagnie !");
		} catch (CompaniesInexistant e) {

		}
		System.out.println("Entrer l'Id de l'ordinateur a modifier");
		int toUpdate = -1;
		while (toUpdate == -1) {
			toUpdate = ScanCLI.scanInt(sc);
		}
		if (toUpdate != -2) {
			ComputerService.INSTANCE.updateAComputer(aAjouter,toUpdate);
		}

	}
}
