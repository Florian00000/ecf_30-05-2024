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
            System.out.println("2/ Voir les articles en stock1");

            choixMenuArticle = scanner.nextLine();
            switch (choixMenuArticle) {
                case "1" -> ajouterArticle();
                case "2" -> articlesEnStock();
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

    //Clients
    private void menuClients(){
        String choixMenuClient;
        do {
            System.out.println("0/ Pour revenir au menu principal");
            System.out.println("1/ Ajouter un client");

            choixMenuClient = scanner.nextLine();
            switch (choixMenuClient) {
                case "1" -> ajouterClient();
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

    //Vente
    private void menuVente(){
        String choixMenuVente;
        do {
            System.out.println("0/ Pour revenir au menu principal");
            System.out.println("1/ Enregistrer une vente");

            choixMenuVente = scanner.nextLine();
            switch (choixMenuVente) {
                case "1" -> enregistrerVente();
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
}
