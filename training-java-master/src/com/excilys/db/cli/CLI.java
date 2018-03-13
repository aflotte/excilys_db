package com.excilys.db.cli;
import com.excilys.db.DAO.CompaniesDAO;
import com.excilys.db.DAO.ComputerDAO;
import com.excilys.db.mapper.Companies;
import com.excilys.db.mapper.Computer;

import java.util.List;
import java.util.Scanner;

import javax.xml.stream.events.EndDocument;

public class CLI {
	public static void main(String[] args) {
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
					
					break;
		
				case 4:
	
					break;
				case 5:
	
					break;
				case 6:
	
					break;
				case 7:
					continu = false;
					break;
					default:
						
		
			}
		
		}

		
	}
	
	public static void MenuIntroduction() {
		System.out.println("Bienvenue sur le CLI de la base de donnée");
		System.out.println("Liste des commandes :");
		System.out.println("1 - Afficher la liste des compagnies");
		System.out.println("2 - Afficher la liste des ordinateurs");
		System.out.println("3 - Creer un nouvel ordinateur");
		System.out.println("4 - Supprimer un ordinateur");
		System.out.println("5 - Voir les details d'un ordinateur");
		System.out.println("6 - Mettre à jour un ordinateur");
		System.out.println("7 - Quitter le CLI");
	}
	
	public static int ChoixMenuIntroduction() {
		Scanner sc = new Scanner(System.in);
		//TODO: gérer l'entrée de lettre ou carractère spéciaux
		int result = sc.nextInt();
		if ((result > 7)||(result<1) ){
			System.out.println("Veuillez entrer un nombre correct");
			return 0;
		}
		return result;
	}
	
	public static void AfficherCompagnies() {
		CompaniesDAO companies = new CompaniesDAO();
		List<Companies> listeCompanies =companies.listCompanies();
		System.out.println("Voici la liste des compagnies : ");
		for (int i = 0; i < listeCompanies.size();i++) {
    		System.out.println(listeCompanies.get(i));
    	}
	}
	
	public static void AfficherOrdinateurs() {
		ComputerDAO computer = new ComputerDAO();
		List<Computer> listeOrdinateur =computer.listComputer();
		System.out.println("Voici la liste des ordinateurs : ");
		for (int i = 0; i < listeOrdinateur.size();i++) {
    		System.out.println(listeOrdinateur.get(i));
    	}
	}
}
