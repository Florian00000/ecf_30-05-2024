package org.example.service;

import org.example.entities.Article;
import org.example.entities.Client;
import org.example.entities.Vente;
import org.example.utils.EtatVente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class MagasinServiceOld {

    private StandardServiceRegistry registre;
    private SessionFactory sessionFactory;
    private Session session;

    public MagasinServiceOld() {
        registre = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registre).buildMetadata().buildSessionFactory();
    }

    public void close(){
        sessionFactory.close();
    }


    //Article
    public boolean ajouterAticle(Article article){
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(article);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public boolean modifierArticle(Article article){
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(article);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    public boolean supprimerArticleParId(int id){
        Article article = articleParId(id);
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query queryVente = session.createQuery("delete from Vente where article = :article");
        queryVente.setParameter("article", article);
        queryVente.executeUpdate();
        Query queryArticle = session.createQuery("delete from Article where id = :id");
        queryArticle.setParameter("id", id);
        int resultArticle = queryArticle.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return resultArticle > 0;
    }

    public Article articleParId(int id){
        session = sessionFactory.openSession();
        Article article = session.get(Article.class, id);
        session.close();
        return article;
    }

    public List<Article> listeArticles(){
        session = sessionFactory.openSession();
        List<Article> articles = session.createQuery("from Article ", Article.class).list();
        session.close();
        return articles;
    }

    public List<Article> listeArticlesEnStock(){
        session = sessionFactory.openSession();
        Query<Article> query = session.createQuery("from Article where stock > 0", Article.class);
        List<Article> articles = query.list();
        session.close();
        return articles;
    }

    //Client
    public boolean ajouterClient(Client client){
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(client);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    //Vente
    public boolean enregistrerVente(Vente vente, int idArticle, String nomClient){
        session = sessionFactory.openSession();
        Article article = session.get(Article.class, idArticle);
        Query<Client> queryClient = session.createQuery("from Client where nom = :nomClient", Client.class);
        queryClient.setParameter("nomClient", nomClient);
        Client client = queryClient.getSingleResult();
        vente.setClient(client);
        vente.setArticle(article);
        int nbArticlesVendu = vente.getNbArticles();

        if(nbArticlesVendu < article.getStock()){
            session.beginTransaction();
            Query queryArticle = session.createQuery("update Article set stock = :stock where id = :id");
            queryArticle.setParameter("stock", article.getStock() - nbArticlesVendu);
            queryArticle.setParameter("id", idArticle);
            queryArticle.executeUpdate();
            session.save(vente);
            session.getTransaction().commit();
            session.close();
        return true;
        }else {
            System.out.println("Vente impossible");
            return false;
        }

    }

    public boolean modifierEtatVente(int idVente, EtatVente etatVente){
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Vente> query = session.createQuery("update Vente set etat = :etatVente where id = :id");
        query.setParameter("etatVente", etatVente);
        query.setParameter("id", idVente);
        int nbEtatVente = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return nbEtatVente > 0;
    }

    public List<Vente> listeVenteParEtat(EtatVente etatVente){
        session = sessionFactory.openSession();
        Query<Vente> venteQuery = session.createQuery("from Vente where etat = :etat", Vente.class);
        venteQuery.setParameter("etat", etatVente);
        List<Vente> ventes = venteQuery.list();
        session.close();
        return ventes;
    }

    public List<Vente> ListVenteParClient(String nomClient){
        session = sessionFactory.openSession();
        Client client = null;
        try {
            Query<Client> queryClient = session.createQuery("from Client where nom = :nomClient", Client.class);
            queryClient.setParameter("nomClient", nomClient);
            client = queryClient.getSingleResult();
        }catch (Exception e){
            System.out.println(" Client non trouvé ");
        }
        Query<Vente> queryVente = session.createQuery("from Vente where client = :client", Vente.class);
        queryVente.setParameter("client", client);
        List<Vente> ventes = queryVente.list();
        session.close();
        return ventes;
    }


}
