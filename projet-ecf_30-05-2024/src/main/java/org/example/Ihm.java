package org.example;


import org.example.entities.Article;
import org.example.entities.Client;
import org.example.entities.Vente;
import org.example.service.MagasinServiceOld;
import org.example.utils.CategorieArticle;
import org.example.utils.EtatVente;

import java.util.List;
import java.util.Scanner;

public class Ihm {
    private MagasinServiceOld magasinService;
    private Scanner scanner;

    public Ihm() {
        magasinService = new MagasinServiceOld();
        scanner = new Scanner(System.in);

    }

    public void start() {
        System.out.println("############ Magasin! ############");
        String choix;
        do {
            menu();
            choix = scanner.nextLine();
            switch (choix) {
                case "1" -> menuArticles();
                case "2" -> menuClients();
                case "3" -> menuVente();
                default -> System.out.println("choix invalide");
            }
        }while (!choix.equals("0"));
        magasinService.close();
        scanner.close();
        System.out.println("Fermeture du programme");
    }

    private void menu(){
        System.out.println("0/ Pour quitter le programme");
        System.out.println("1/ Menu articles");
        System.out.println("2/ Menu client");
        System.out.println("3/ Menu Vente");
    }

    //Articles
    private void menuArticles(){
        String choixMenuArticle;
        do {
            System.out.println("0/ Pour revenir au menu principal");
            System.out.println("1/ Ajouter un article");
            System.out.println("2/ Voir les articles en stock");
            System.out.println("3/ Supprimer un article");

            choixMenuArticle = scanner.nextLine();
            switch (choixMenuArticle) {
                case "1" -> ajouterArticle();
                case "2" -> articlesEnStock();
                case "3" -> supprimerArticle();
                default -> System.out.println("choix invalide");
            }
        }while (!choixMenuArticle.equals("0"));
    }

    private void ajouterArticle(){
        System.out.println("Entrez la description de l'article");
        String description = scanner.nextLine();
        String choixCategorie;
        CategorieArticle categorieArticle = null;
        do {
            System.out.println("Choisissez la catégorie");
            System.out.println("1/ Homme");
            System.out.println("2/ Femme");
            System.out.println("3/ Enfant");
            choixCategorie = scanner.nextLine();
            switch (choixCategorie){
                case "1" -> categorieArticle = CategorieArticle.HOMME;
                case "2" -> categorieArticle = CategorieArticle.FEMME;
                case "3" -> categorieArticle = CategorieArticle.ENFANT;
                default -> System.out.println("choix invalide");
            }

        }while (!choixCategorie.equals("1") && !choixCategorie.equals("2") && !choixCategorie.equals("3"));
        System.out.println("Entrez la taille ");
        String taille = scanner.nextLine();
        System.out.println("Entrez le prix");
        String scannerPrix = scanner.nextLine();
        double prix = Double.parseDouble(scannerPrix);
        System.out.println("entrez le stock");
        int stock = scanner.nextInt();
        scanner.nextLine();
        Article article = Article.builder().description(description).categorie(categorieArticle).taille(taille)
                .prix(prix).stock(stock).build();
        boolean resultat = magasinService.ajouterAticle(article);
        if (resultat) {
            System.out.println(article + " ajouté avec succès");
        }else System.out.println("Erreur lors de l'ajout");
    }

    private void articlesEnStock(){
        List<Article> articlesEnStock = magasinService.listeArticlesEnStock();
        for (Article article: articlesEnStock){
            System.out.println(article);
        }
    }

    private void supprimerArticle(){
        System.out.println("Entrez l'id de l'article à supprimer");
        int id = scanner.nextInt();
        boolean result = magasinService.supprimerArticleParId(id);
        if (result){
            System.out.println("Suppression effectuée avec succès!");
        }else System.out.println("Erreur lors de la suppression");
    }

    //Clients
    private void menuClients(){
        String choixMenuClient;
        do {
            System.out.println("0/ Pour revenir au menu principal");
            System.out.println("1/ Ajouter un client");
            System.out.println("2/ Historique des achats");

            choixMenuClient = scanner.nextLine();
            switch (choixMenuClient) {
                case "1" -> ajouterClient();
                case "2" -> historiqueVenteParClient();
                default -> System.out.println("choix invalide");
            }
        }while (!choixMenuClient.equals("0"));
    }

    private void ajouterClient(){
        System.out.println("Entrez le nom du client");
        String nom = scanner.nextLine();
        System.out.println("Entrez le mail du client");
        String mail = scanner.nextLine();
        Client client = Client.builder().nom(nom).email(mail).build();

        boolean resultat = magasinService.ajouterClient(client);
        if (resultat) {
            System.out.println(client + " ajouté avec succès");
        }else System.out.println("Erreur lors de l'ajout");
    }

    private void historiqueVenteParClient(){
        System.out.println("Entrez le nom du client");
        String nomClient = scanner.nextLine();
        List<Vente> ventes = magasinService.ListVenteParClient(nomClient);
        for (Vente vente: ventes){
            System.out.println(vente);
        }
    }

    //Vente
    private void menuVente(){
        String choixMenuVente;
        do {
            System.out.println("0/ Pour revenir au menu principal");
            System.out.println("1/ Enregistrer une vente");
            System.out.println("2/ Finaliser une vente");
            System.out.println("3/ Afficher les ventes");

            choixMenuVente = scanner.nextLine();
            switch (choixMenuVente) {
                case "1" -> enregistrerVente();
                case "2" -> finaliserVente();
                case "3" -> affichageVentes();
                default -> System.out.println("choix invalide");
            }
        }while (!choixMenuVente.equals("0"));
    }

    private void enregistrerVente(){
        System.out.println("Entrez l'id de l'article");
        int idArticle = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Entrez le nom du client");
        String nomClient = scanner.nextLine();
        System.out.println("Entre le nombre d'articles");
        int nbArticles = scanner.nextInt();
        scanner.nextLine();
        Vente vente = Vente.builder().etat(EtatVente.ENCOURS).nbArticles(nbArticles).build();
        boolean resultat = magasinService.enregistrerVente(vente, idArticle, nomClient);
        if (resultat) {
            System.out.println("Vente effectuée");
        }else System.out.println("Erreur lors de la vente");
    }

    private void finaliserVente(){
        System.out.println("Entrez l'id de la vente à finaliser");
        int idVente = scanner.nextInt();
        scanner.nextLine();
        boolean resultat = magasinService.modifierEtatVente(idVente, EtatVente.FINALISEE);
        if (resultat) {
            System.out.println("Vente finalisée");
        }else System.out.println("Erreur lors de la vente");
    }

    private void affichageVentes(){
        String choixCategorie;
        EtatVente etatVente = null;
        do {
            System.out.println("Menu Affichage des ventes");
            System.out.println("1/ Afficher les ventes en cours");
            System.out.println("2/ Afficher les ventes finalisées");
            System.out.println("3/ Afficher les ventes annulées");
            choixCategorie = scanner.nextLine();
            switch (choixCategorie){
                case "1" -> etatVente = EtatVente.ENCOURS;
                case "2" -> etatVente = EtatVente.FINALISEE;
                case "3" -> etatVente = EtatVente.ANNULEE;
                default -> System.out.println("choix invalide");
            }

        }while (!choixCategorie.equals("1") && !choixCategorie.equals("2") && !choixCategorie.equals("3"));
        List<Vente> listeVentes = magasinService.listeVenteParEtat(etatVente);
        for (Vente vente : listeVentes){
            System.out.println(vente);
        }
    }
}
