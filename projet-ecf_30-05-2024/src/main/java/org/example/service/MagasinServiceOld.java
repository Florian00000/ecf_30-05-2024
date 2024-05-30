package org.example.service;

import org.example.entities.Article;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

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

    public boolean supprimerArticle(Article article){
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(article);
        session.getTransaction().commit();
        session.close();
        return true;
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



}
