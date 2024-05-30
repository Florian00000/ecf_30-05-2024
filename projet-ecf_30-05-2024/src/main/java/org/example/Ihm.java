package org.example;


import org.example.entities.Article;
import org.example.entities.Client;
import org.example.service.MagasinServiceOld;
import org.example.utils.CategorieArticle;

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
    }

    //Articles
    private void menuArticles(){
        String choixMenuArticle;
        do {
            System.out.println("0/ Pour revenir au menu principal");
            System.out.println("1/ Ajouter un article");

            choixMenuArticle = scanner.nextLine();
            switch (choixMenuArticle) {
                case "1" -> ajouterArticle();
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
}
