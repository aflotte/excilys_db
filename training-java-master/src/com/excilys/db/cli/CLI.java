package com.excilys.db.cli;
import com.excilys.db.DAO.CompaniesDAO;
import com.excilys.db.DAO.ComputerDAO;
import com.excilys.db.mapper.Companies;
import com.excilys.db.mapper.Computer;
import com.excilys.db.page.PageCompanies;
import com.excilys.db.page.PageComputer;
import com.excilys.db.cli.ScanCLI;
import com.excilys.db.exception.CompaniesIdIncorrect;
import com.excilys.db.exception.IncoherentDates;

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
	
	public static void main(String[] args) {
		System.out.println("Bienvenue sur le CLI de la base de donnée");
		sc = new Scanner(System.in);
		boolean continu = true;
		while ( continu) {
			MenuIntroduction();		
			int choix = ChoixMenuIntroduction();
			switch (choix) 
			{
				case 1:
					AfficherCompagnies();
					break;
				case 2:
					AfficherOrdinateurs();
					break;
				case 3:
					AjouterOrdinateur();
					break;
				case 4:
					SupprimerOrdinateur();
					break;
				case 5:
					AfficherOrdinateur();
					break;
				case 6:
					MettreAJour();
					break;
				case 7:
					continu = false;
					break;
					default:
			}
		}
		sc.close();
	}	

	public static void MenuIntroduction() {
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
	public static int ChoixMenuIntroduction() {
		int result = 0;
		result = ScanCLI.scanInt(sc);
		if ((result > 7)||(result<1) ){
			System.out.println("Veuillez entrer un nombre correct");
			return 0;
		}
		return result;

		
	}
	
	public static void AfficherCompagnies() {
		CompaniesDAO companies = CompaniesDAO.getInstance();
		List<Companies> listeCompanies =companies.listCompanies();
		PageCompanies page = new PageCompanies(listeCompanies,sc);
		System.out.println("Voici la liste des ordinateurs ( Q to exit ): ");
		page.afficher();
	}
	
	public static void AfficherOrdinateurs() {
		ComputerDAO computer = ComputerDAO.getInstance();
		List<Computer> listeOrdinateur =computer.listComputer();
		PageComputer page = new PageComputer(listeOrdinateur,sc);
		System.out.println("Voici la liste des ordinateurs ( Q to exit ): ");
		page.afficher();
	}
	
	public static void AjouterOrdinateur() {
		ComputerDAO computer = ComputerDAO.getInstance();
		Computer aAjouter = new Computer();
		try {
			aAjouter = ScanCLI.scanComputer();
			computer.createAComputer(aAjouter);
		} catch (IncoherentDates e) {
			System.out.println("Les dates rentrées sont incohérentes");
		}catch (InputMismatchException e){
			System.out.println("Entrez un entier !");
		}catch (CompaniesIdIncorrect e) {
			System.out.println("L'id de la compagnie que vous avez rentré ne correspond à aucune compagnie !");
		}
	}
	
	private static void SupprimerOrdinateur() {
		//TODO: gérer le cas où l'id est faux
		ComputerDAO computer = ComputerDAO.getInstance();
		System.out.println("Donner l'Id de l'ordinateur à supprimer ( -2 pour annuler )");
		int toDelete = -1;
		while (toDelete == -1) {
			toDelete = ScanCLI.scanInt(sc);
		}
		if (toDelete != -2) {
			computer.deleteAComputer(toDelete);
		}
	}
	
	private static void AfficherOrdinateur() {
		ComputerDAO computer = ComputerDAO.getInstance();
		System.out.println("Donner l'Id de l'ordinateur à afficher ( -2 pour annuler )");
		int toDisplay = -1;
		while (toDisplay == -1) {
			toDisplay = ScanCLI.scanInt(sc);
		}
		if (toDisplay != -2) {
			System.out.println(computer.showDetails(toDisplay));
	
		}
	}
	
	private static void MettreAJour() {
		ComputerDAO computer = ComputerDAO.getInstance();
		Computer aAjouter = new Computer();
		try {
				aAjouter = ScanCLI.scanComputer();
		} catch (IncoherentDates e) {
				System.out.println("Les dates rentrées sont incohérentes");
		}catch (InputMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompaniesIdIncorrect e) {
			System.out.println("L'id de la compagnie que vous avez rentré ne correspond à aucune compagnie !");
		}
		System.out.println("Entrer l'Id de l'ordinateur a modifier");
		int toUpdate = -1;
		while (toUpdate == -1) {
			toUpdate = ScanCLI.scanInt(sc);
		}
		if (toUpdate != -2) {
			computer.updateAComputer(aAjouter,toUpdate);
		}
		
	}
}
