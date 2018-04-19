package com.excilys.db.cli;
import com.excilys.db.page.PageCompaniesCLI;
import com.excilys.db.page.PageComputerCLI;
import com.excilys.db.service.ICompaniesService;
import com.excilys.db.service.IComputerService;
import com.excilys.db.validator.ComputerValidator;
import com.excilys.db.config.CLIConfig;
import com.excilys.db.exception.CompaniesIdIncorrectException;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.exception.IncoherentDatesException;
import com.excilys.db.exception.ServiceException;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;

import java.util.InputMismatchException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;




/**
 * L'interface en ligne de commande.
 * @author flotte
 *
 */
@Controller("cLI")
public class CLI {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CLI.class);
    Scanner scanner;
    @Autowired
    private IComputerService computerService;
    @Autowired
    private ICompaniesService companiesService;
    @Autowired
    private ComputerValidator computerValidator;
    @Autowired
    private ScanCLI scannerCLI;
    
    /**
     *
     * @param args aucun attendu
     * @throws CompaniesInexistantException erreur lors de la création d'une compagnie
     */
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CLIConfig.class);
        context.getBean(CLI.class).start();

    }
    
    private CLI() {
    }
    
    private void start() {
        System.out.println("Bienvenue sur le CLI de la base de donnée");
        scanner = new Scanner(System.in);
        boolean continu = true;
        while (continu) {
            menuIntroduction();
            int choix = choixMenuIntroduction();
            switch (Menu.values()[choix - 1]) {
            case AFFICHER_COMPAGNIE:
                afficherCompagnies();
                break;
            case AFFICHER_ORDINATEURS:
                afficherOrdinateurs();
                break;
            case AJOUTER_ORDINATEUR:
                ajouterOrdinateur();
                break;
            case SUPPRIMER_ORDINATEUR:
                supprimerOrdinateur();
                break;
            case AFFICHER_ORDINATEUR:
                try {
                    afficherOrdinateur();
                } catch (ServiceException e) {
                    logger.warn(e.getMessage());
                }
                break;
            case METTRE_A_JOUR:
                mettreAJour();
                break;
            case SUPPRIMER_COMPAGNIE:
                supprimerCompagnie();
                break;
            case QUITTER:
                continu = false;
                break;
            default:
            }
        }
        scanner.close();
    }


    private void supprimerCompagnie() {
        System.out.println("Entrer l'id de la compagnie a supprimer !");
        int idCompany = 0;
        idCompany = ScanCLI.scanInt(scanner);
        try {
            companiesService.destroy(idCompany);
        }catch (Exception e){
            System.out.println("La compagnie a supprimer n'existe pas");
        }
        
        
    }

    /**
     *
     */
    public void menuIntroduction() {
        System.out.println("Liste des commandes :");
        System.out.println("1 - Afficher la liste des compagnies");
        System.out.println("2 - Afficher la liste des ordinateurs");
        System.out.println("3 - Creer un nouvel ordinateur");
        System.out.println("4 - Supprimer un ordinateur");
        System.out.println("5 - Voir les details d'un ordinateur");
        System.out.println("6 - Mettre à jour un ordinateur");
        System.out.println("7 - Supprimer une compagnie");
        System.out.println("8 - Quitter le CLI");
    }

    /**
     *
     * @return Le choix de l'utilisateur, 0 si l'utilisateur rentre quelque chose d'incorrect
     */
    public int choixMenuIntroduction() {
        int result = 0;
        result = ScanCLI.scanInt(scanner);
        if ((result > 7) || (result < 1)) {
            return 8;
        }
        return result;
    }

    /**
     *
     */
    public void afficherCompagnies() {
        List<Company> listeCompanies = companiesService.listCompanies();
        PageCompaniesCLI page = new PageCompaniesCLI(listeCompanies, scanner);
        System.out.println("Voici la liste des compagnies ( Q to exit ): ");
        page.afficher();
    }

    /**
     *
     * @throws CompaniesInexistantException erreur avec les compagnies lkors de la création de l'ordinateur
     */
    public void afficherOrdinateurs() {
        List<Computer> listeOrdinateur = computerService.listComputer();
        PageComputerCLI page = new PageComputerCLI(listeOrdinateur, scanner);
        System.out.println("Voici la liste des ordinateurs ( Q to exit ): ");
        page.afficher();
    }

    /**
     *
     */
    public void ajouterOrdinateur() {
        Computer aAjouter = null;
            try {
                aAjouter = scannerCLI.scanComputer(scanner);
            } catch (InputMismatchException | CompaniesIdIncorrectException | IncoherentDatesException e1) {
                logger.warn(e1.getMessage());
            }
            try {
                computerService.createComputer(aAjouter);
            } catch (ServiceException e) {
                logger.warn(e.getMessage());
            }
    }

    /**
     *
     */
    private void supprimerOrdinateur() {
        System.out.println("Donner l'Id de l'ordinateur à supprimer ( -2 pour annuler )");
        int toDelete = -1;
        while (toDelete == -1) {
            toDelete = ScanCLI.scanInt(scanner);
        }
        if (toDelete != -2) {
            computerService.deleteComputer(toDelete);
        }
    }

    /**
     *
     * @throws CompaniesInexistantException erreur avec les compagnies lors de la création de l'ordinateur
     * @throws ServiceException 
     */
    private void afficherOrdinateur() throws ServiceException {
        System.out.println("Donner l'Id de l'ordinateur à afficher ( -2 pour annuler )");
        int toDisplay = -1;
        while (toDisplay == -1) {
            toDisplay = ScanCLI.scanInt(scanner);
            if ((toDisplay!=-2)&&(!computerValidator.exist(toDisplay))) {
                toDisplay = -1;
            }
        }
        if (toDisplay != -2) {
            Optional<Computer> computer = computerService.showDetails(toDisplay);
            if (computer.isPresent()) {
                System.out.println(computer.get());
            } else {
                System.out.println("Aucun ordinateur correspondant à l'Id rentré n'a été trouvé !");
            }
        }
    }

    /**
     * @throws ServiceException 
     *
     */
    private void mettreAJour() {
        Computer aAjouter = null;
        try {
            aAjouter = scannerCLI.scanComputer(scanner);
        } catch (IncoherentDatesException e) {
            System.out.println("Les dates rentrées sont incohérentes");
        } catch (InputMismatchException e) {
            System.out.println("Vous n'avez pas respecté le format des valeurs attendu");
        } catch (CompaniesIdIncorrectException e) {
            System.out.println("L'id de la compagnie que vous avez rentré ne correspond à aucune compagnie !");
        }
        System.out.println("Entrer l'Id de l'ordinateur a modifier");
        int toUpdate = -1;
        while (toUpdate == -1) {
            toUpdate = ScanCLI.scanInt(scanner);
        }
        if (toUpdate != -2) {
            try {
                computerService.updateAComputer(aAjouter, toUpdate);
            } catch (ServiceException e) {
                logger.warn(e.getMessage());
            }
        }

    }
}