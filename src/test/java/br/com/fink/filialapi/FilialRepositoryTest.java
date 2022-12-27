package br.com.fink.filialapi;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.fink.filialapi.models.ETipo;
import br.com.fink.filialapi.models.Filial;
import br.com.fink.filialapi.repositories.FilialRepository;

@DataJpaTest
@DisplayName("Testes para FilialRepository")
class FilialRepositoryTests {

	@Autowired
	private FilialRepository filialRepository;

	@Test
	@DisplayName("Teste para obtenção da Filial por id")
	void findByidFilialWhenSuccessful() {
		Filial filial = createFilial();
		Filial savedFilial = filialRepository.save(filial);
		Optional<Filial> foundedFilial = filialRepository.findById(savedFilial.getId());
		Assertions.assertTrue(foundedFilial.isPresent());
	}

	@Test
	@DisplayName("Teste para inclusão de Filial")
	void saveCreateFilialWhenSuccessful() {
		Filial filial = createFilial();
		Filial savedFilial = filialRepository.save(filial);
		Assertions.assertNotNull(savedFilial);
		Assertions.assertNotNull(savedFilial.getId());
		Assertions.assertEquals(savedFilial.getNome(), filial.getNome());
	}

	@Test
	@DisplayName("Teste para alteração de Filial")
	void saveUpdateFilialWhenSuccessful() {
		Filial filial = createFilial();
		Filial savedFilial = filialRepository.save(filial);
		savedFilial.setUf("RJ");
		Filial updatedFilial = filialRepository.save(savedFilial);
		Assertions.assertNotNull(updatedFilial);
		Assertions.assertEquals(updatedFilial.getUf(), savedFilial.getUf());
	}

	@Test
	@DisplayName("Teste para exclusão de Filial")
	void deleteFilialWhenSuccessful() {
		Filial filial = createFilial();
		Filial savedFilial = filialRepository.save(filial);
		filialRepository.deleteById(savedFilial.getId());
		Optional<Filial> deletedFilial = filialRepository.findById(savedFilial.getId());
		Assertions.assertTrue(deletedFilial.isEmpty());
	}
	
	private Filial createFilial() {
		Filial filial = new Filial();
		filial.setNome("Filial 1");
		filial.setCnpj("04400990000106");
		filial.setCidade("Osasco");
		filial.setUf("SP");
		filial.setTipo(ETipo.CD);
		filial.setAtivo(true);
		filial.setDataCadastro(new Date());
		return filial;
	}
}