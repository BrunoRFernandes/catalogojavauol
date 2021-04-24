package br.com.uol.catalogo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.uol.catalogo.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> { }
