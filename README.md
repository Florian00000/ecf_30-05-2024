# ecf_30-05-2024

Système de gestion d'inventaire pour un magasin de vêtements intéragissant grâce au terminal, utilisant hibernate pour la gestion des intéractions avec la Base de données (içi en mysql).

## Contenu

- [Fonctionnalitées](#fonctionnalités)
- [Installation](#installation)
- [Utilisation](#utilisation)
- [Structure](#structure-du-projet)
- [Annexes](#annexes)

##  Fonctionnalités

1. Gestion des Articles  

    - Ajouter un article
    - Modifier un article
    - Supprimer un article
    - Liser les articles en stock

2. Gestion des Clients

    - Ajouter un client 
    - Aficher l'historique des achats d'un client

3. Gestion des Ventes 

    - Enregistrer une vente 
    - Modifier l'état d'une vent (en cours, finalisée, annulée)
    - Lister les ventes par état
    - Lister les ventes par clients

## Installation 

1. Clonez le dépôt :

```bash
git clone https://github.com/Florian00000/ecf_30-05-2024.git
```

2. Configurez la base de données dans le fichier `hibernate.cfg.xml`.  

3. Compilez le projet avec Maven.

## Utilisation 

Pouir démarrer l'application, exécuter la classe `Ihm` en démarrant le fichier `org.example.Main`.  

Suivez les instructions à l'écran pour naviguer dans les menus et utiliser les différentes fonctionnalités de l'application.

## Structure du Projet

- org.example.service

    - `MagasinServiceOld`: Fournit les méthodes pour gérer les articles, les clients ou les ventes, et intéragir avec la BDD.  
- org.example.entities
    - `Article`: Entité représentant un article dans le magasin.
    - `Client`: Entité représentant un client.
    - `Vente`: Entité représentant une vente (`article_client` pour la table dans la BDD).
- org.example
    - `Ihm`: Inbterface utilisateur permettant d'interagir avec le service du magasin.

## Annexes

- [MCD](https://github.com/Florian00000/ecf_30-05-2024/blob/main/MCD_ecf.jpg) 
- [MLD](https://github.com/Florian00000/ecf_30-05-2024/blob/main/MLD_ecf.jpg) 
- [Sujet](https://github.com/Florian00000/ecf_30-05-2024/blob/main/sujet-ecf-30-05-2024.pdf)
    