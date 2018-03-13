package com.excilys.db.cli;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.excilys.db.mapper.Computer;

public class ScanCLI {
	
	
	public static Computer scanComputer() {
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
		//TODO: vérifier l'Id
		aRetourner.setCompanyId(sc.nextInt());
		return aRetourner;
	}
	
	
	public static Date scanDate(Scanner used) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
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
            e.printStackTrace();
            return new Date();
        }
		
	}
}
