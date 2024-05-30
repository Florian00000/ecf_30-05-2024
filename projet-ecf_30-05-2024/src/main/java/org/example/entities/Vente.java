package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.utils.EtatVente;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "article_client")
public class Vente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private EtatVente etat;
    @Column(name = "nombre_articles")
    private int nbArticles;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
