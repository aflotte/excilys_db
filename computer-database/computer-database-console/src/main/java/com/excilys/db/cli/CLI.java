package com.excilys.db.cli;
import com.excilys.db.page.PageCompaniesCLI;
import com.excilys.db.page.PageComputerCLI;
import com.excilys.db.service.ICompaniesService;
import com.excilys.db.service.IComputerService;
import com.excilys.db.validator.ComputerValidator;
import com.excilys.db.config.CLIConfig;
import com.excilys.db.dto.CompanyDTO;
import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.exception.CompaniesIdIncorrectException;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.exception.IncoherentDatesException;
import com.excilys.db.exception.ServiceException;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;

import java.util.InputMismatchException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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

    private static final String REST_URI = "http://localhost:8080/computer-database/";

    private WebTarget client = ClientBuilder.newClient().target(REST_URI);


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
                afficherOrdinateur();
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
            client
            .path("company/delete/" + idCompany)
            .request(MediaType.APPLICATION_JSON)
            .delete();
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
        List<CompanyDTO> listeCompanies = client
                .path("listCompany/")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<CompanyDTO>>() {});
        PageCompaniesCLI page = new PageCompaniesCLI(listeCompanies, scanner);
        System.out.println("Voici la liste des compagnies ( Q to exit ): ");
        page.afficher();
    }

    /**
     *
     * @throws CompaniesInexistantException erreur avec les compagnies lkors de la création de l'ordinateur
     */
    public void afficherOrdinateurs() {
        List<ComputerDTO> listeOrdinateur = client
                .path("listComputer/")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<ComputerDTO>>() {});
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
            client
            .path("computer/addComputer/")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(aAjouter, MediaType.APPLICATION_JSON));
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
            client
            .path("computer/delete/" + toDelete)
            .request(MediaType.APPLICATION_JSON)
            .delete();
        }
    }

    /**
     *
     * @throws CompaniesInexistantException erreur avec les compagnies lors de la création de l'ordinateur
     * @throws ServiceException 
     */
    private void afficherOrdinateur() {
        System.out.println("Donner l'Id de l'ordinateur à afficher ( -2 pour annuler )");
        int toDisplay = -1;
        while (toDisplay == -1) {
            toDisplay = ScanCLI.scanInt(scanner);
            if ((toDisplay!=-2)&&(!computerValidator.exist(toDisplay))) {
                toDisplay = -1;
            }
        }
        if (toDisplay != -2) {
            ComputerDTO computer = client
                    .path("computer/Computer/" + toDisplay)
                    .request(MediaType.APPLICATION_JSON)
                    .get(ComputerDTO.class);
            if (computer != null) {
                System.out.println(computer);
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
            aAjouter.setId(toUpdate);
            client
            .path("computer/updateComputer")
            .request(MediaType.APPLICATION_JSON)
            .put(Entity.entity(aAjouter, MediaType.APPLICATION_JSON));
        }

    }
}