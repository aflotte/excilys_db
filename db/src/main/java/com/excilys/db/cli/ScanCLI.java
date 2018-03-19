package com.excilys.db.cli;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;



import com.excilys.db.exception.CompaniesIdIncorrect;
import com.excilys.db.exception.CompaniesInexistant;
import com.excilys.db.exception.IncoherentDates;
import com.excilys.db.model.Companies;
import com.excilys.db.model.Computer;
import com.excilys.db.validator.ComputerValidator;

/**
 * L'implémentation des scans avec la gestion d'erreur qui convient pour la CLI
 * @author flotte
 *
 */
public class ScanCLI {



	/**
	 * 
	 * @param Le scanner précédement ouvert
	 * @return ce qui est entrée par l'utilisateur, -1 en cas d'erreur
	 */
	public static int scanInt(Scanner used) {
		int result;
		try {
			result = used.nextInt();
		}catch (InputMismatchException e){
			used.nextLine();
			return -1;
		}
		return result;
	}

	/**
	 * 
	 * @return L'ordinateur entrée par l'utilisateur
	 * @throws InputMismatchException
	 * @throws CompaniesIdIncorrect
	 * @throws IncoherentDates
	 * @throws CompaniesInexistant 
	 */
	public static Computer scanComputer() throws InputMismatchException, CompaniesIdIncorrect, IncoherentDates, CompaniesInexistant{
		Computer aRetourner = new Computer(); 
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrer le nom de l'ordinateur : ");
		aRetourner.setName(sc.next());
		System.out.println("Entrer la date d'introduction de l'ordinateur (aaaa/mm/jj) : ");
		System.out.println("rentrer null pour ne pas remplir le champ");
		aRetourner.setIntroduced(ScanCLI.scanDate(sc));
		System.out.println("Entrer la date d'abandon de l'ordinateur (aaaa/mm/jj) : ");
		System.out.println("rentrer null pour ne pas remplir le champ");
		aRetourner.setDiscontinued(ScanCLI.scanDate(sc));

		System.out.println("Entrer l'Id de la compagnie ( -2 pour laisser vide ) :");
		int id_companie = sc.nextInt();
		if (id_companie != -2) {
			aRetourner.setCompanyId(new Companies(id_companie));
		}
		ComputerValidator.init(aRetourner);
		ComputerValidator.validate();
		return aRetourner;
	}

	/**
	 * 
	 * @param le scanner précédement ouvert
	 * @return Une date rentré par l'utilisateur
	 */
	public static LocalDate scanDate(Scanner used) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String stringDate = used.next();
		if ( stringDate.equals("null")) {
			return null;
		}
		try {
			Date date = formatter.parse(stringDate);
			System.out.println(date);
			System.out.println(formatter.format(date));
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		} catch (ParseException e) {
			System.out.println("Comme la date entrée ne correspondait pas au format, aujourd'hui a été rentré par default !");
			return new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}	
	}
}
