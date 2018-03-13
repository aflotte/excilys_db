package com.excilys.db.cli;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.excilys.db.DAO.CompaniesDAO;
import com.excilys.db.exception.CompaniesIdIncorrect;
import com.excilys.db.mapper.Computer;

public class ScanCLI {
	
	//TODO: un scanInt qui gère les erreurs du nextInt comme voulu
	
	
	
	
	public static Computer scanComputer() throws InputMismatchException, CompaniesIdIncorrect{
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
		System.out.println("Entrer l'Id de la compagnie :");
		int id_companie = sc.nextInt();
		if (CompaniesDAO.existCompanies(id_companie)) {
			aRetourner.setCompanyId(id_companie);
		}else {
			System.out.println("La compagnie rentrée n'existe pas");
			throw new CompaniesIdIncorrect();
		}
		//TODO: vérifier l'Id + gestion de l'erreur

		return aRetourner;
	}
	
	
	public static Date scanDate(Scanner used) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String stringDate = used.next();
        if ( stringDate.equals("null")) {
        	return null;
        }
        try {
            Date date = formatter.parse(stringDate);
            System.out.println(date);
            System.out.println(formatter.format(date));
            return date;
        } catch (ParseException e) {
            System.out.println("Comme la date entrée ne correspondait pas au format, aujourd'hui a été rentré par default !");
            return new Date();
        }	
	}
}
