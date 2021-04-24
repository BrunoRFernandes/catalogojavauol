package br.com.uol.catalogo.repositories;

import br.com.uol.catalogo.domain.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ProductCustomRepository {

    private EntityManager entityManager;

    public ProductCustomRepository(EntityManager em){
        this.entityManager = em;
    }

    public List<Product> find(String nome, Double valorMin, Double valorMax){

        String query = "select p from Product as p";
        String condicao = " where";
        boolean condicaoValores = false;

        if (nome != null){
        	query += condicao + " p.nome = :nome and p.descricao = :nome";
            condicao = " and";
        }

        if (valorMin != null && valorMax != null){
            query  += condicao + " p.preco between :valorMin and :valorMax";
            condicao = " and";
            condicaoValores = true;
        }

        if (valorMin != null && condicaoValores == false){
            query = "select p from Product as p";
            condicao = " and";
            condicaoValores = true;
        }

        if (valorMax != null && condicaoValores == false){
            query = "select p from Product as p";
            condicao = " and";
            condicaoValores = true;
        }

        var q = entityManager.createQuery(query, Product.class);

        if (nome != null){
            q.setParameter("nome", nome);
        }

        if (valorMin != null && valorMax != null){
            q.setParameter("valorMin", valorMin);
            q.setParameter("valorMax", valorMax);
        }

        if (valorMin != null && condicaoValores == false){
            q.setParameter("valorMin", valorMin);
        }

        if (valorMax != null && condicaoValores == false){
            q.setParameter("valorMax", valorMax);
        }

        return q.getResultList();
    }
}
