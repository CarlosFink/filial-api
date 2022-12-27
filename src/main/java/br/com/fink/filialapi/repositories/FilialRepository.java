package br.com.fink.filialapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fink.filialapi.models.Filial;

public interface FilialRepository extends JpaRepository<Filial, Integer> {
    
}
