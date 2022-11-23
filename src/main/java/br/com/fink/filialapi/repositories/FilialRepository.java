package br.com.fink.filialapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fink.filialapi.models.Filial;

@Repository
public interface FilialRepository extends JpaRepository<Filial, Integer> {

}
