package com.excilys.db.page;

import java.util.List;
import java.util.Scanner;

import com.excilys.db.model.Company;


public class PageCompanies {
    List<Company> companies;
    int index;
    static final int NOMBRE_AFFICHAGE = 10;
    private Scanner sc;

    /**
     *
     * @param list la liste a afficher
     * @param sc le scanner
     */
    public PageCompanies(List<Company> list, Scanner sc) {
        companies = list;
        index = 0;
        this.sc = sc;
    }

    /**
     *
     * @param n nombre d'élément a afficher
     */
    public void afficherNElements(int n) {
        int end = index + n;
        for (int i = index; i < Math.min(end, companies.size()); i++) {
            System.out.println("Compagnie " + (i + 1) + companies.get(i).toString());
            index++;
        }

    }

    /**
     *
     */
    public void afficher() {
        while (index < companies.size()) {
            String exit = sc.nextLine();
            if (exit.equals("Q") || exit.equals("q")) {
                index = companies.size();
            }
            afficherNElements(NOMBRE_AFFICHAGE);
        }
    }


}