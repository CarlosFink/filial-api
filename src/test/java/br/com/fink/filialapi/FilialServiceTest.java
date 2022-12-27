package br.com.fink.filialapi;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import br.com.fink.filialapi.models.ETipo;
import br.com.fink.filialapi.models.Filial;
import br.com.fink.filialapi.payload.FilialDTO;
import br.com.fink.filialapi.repositories.FilialRepository;
import br.com.fink.filialapi.services.FilialService;
import br.com.fink.filialapi.services.exceptions.ObjectNotFoundException;

@ExtendWith(SpringExtension.class)
@DisplayName("Teste de FilialService") 
class FilialServiceTest {

	@InjectMocks
	private FilialService filialService;

	@Mock
	private FilialRepository filialRepository;

	@Mock
	private ModelMapper modelMapper;

	@BeforeEach
	void setUp() throws Exception {
		PageImpl<Filial> pageFilial = new PageImpl<>(List.of(this.validFilial()));
		BDDMockito.when(filialRepository.findAll(ArgumentMatchers.any(Pageable.class)))
				.thenReturn(pageFilial);

		Optional<Filial> filial = Optional.of(validFilial());
		BDDMockito.when(filialRepository.findById(1)).thenReturn(filial);

		BDDMockito.when(filialRepository.save(ArgumentMatchers.any())).thenReturn(validFilial());

		BDDMockito.when(modelMapper.map(ArgumentMatchers.any(Filial.class), ArgumentMatchers.any()))
				.thenReturn(validFilialDTO());
	}

	@Test
	@DisplayName("Teste de GET Filiais")
	void pageListFiliaisWhenSuccessful() {
		Pageable pageable = PageRequest.of(1, 5, Sort.unsorted());
		Page<FilialDTO> pageFiliais = filialService.listFiliais(pageable);
		Assertions.assertNotNull(pageFiliais);
		if (pageFiliais != null) {
			Assertions.assertFalse(pageFiliais.toList().isEmpty());
		}
	}
    
	@Test
	@DisplayName("Teste de GET Filial por id")
	void findByidFilialWhenSuccessful() {		
		FilialDTO filialDTO = filialService.findById(1);
		Assertions.assertNotNull(filialDTO);
		Assertions.assertEquals(1, filialDTO.getId());
	}
	
	@Test
	@DisplayName("Teste de GET Filial por id não encontrado")
	void findbyIdWhenUnsuccesful() {
		Assertions.assertThrows(ObjectNotFoundException.class,
				() -> filialService.findById(2));
	}

	@Test
	@DisplayName("Teste para inclusão de Filial")
	void saveCreateFilialWhenSuccessful() {
		FilialDTO filialDTO = createFilial();
		FilialDTO savedFilial = filialService.insert(filialDTO);
		Assertions.assertNotNull(savedFilial);
		Assertions.assertNotNull(savedFilial.getId());
		Assertions.assertEquals(savedFilial.getNome(), filialDTO.getNome());
	}
		
	@Test
	@DisplayName("Teste para alteração de Filial")
	void saveUpdateFilialWhenSuccessful() {		
		FilialDTO filialDTO = validFilialDTO();
		BDDMockito.when(filialRepository.save(ArgumentMatchers.any())).thenReturn(changedFilial());
		FilialDTO savedFilial = filialService.update(filialDTO);
		Assertions.assertNotNull(savedFilial);
		Assertions.assertNotNull(savedFilial.getId());
//		Assertions.assertEquals("RJ", savedFilial.getUf());
	}
	
	@Test
	@DisplayName("Teste para exclusão de Filial")
	void deleteFilialWhenSuccessful() {
		FilialDTO filialDTO = validFilialDTO();
		filialService.delete(filialDTO.getId());
		verify(filialRepository, times(1)).deleteById(anyInt());
	}

	@Test
	@DisplayName("Teste para Ativar/Desativar Filiais")
	void saveActivateDeactivateFilialWhenSuccessful() {
		FilialDTO filialDTO = validFilialDTO();
		FilialDTO savedFilial = filialService.activateDeactivate(filialDTO.getId());
		Assertions.assertNotNull(savedFilial);
		filialDTO.setAtivo(false);
		savedFilial = filialService.activateDeactivate(filialDTO.getId());
		Assertions.assertNotNull(savedFilial);
	}

	private FilialDTO createFilial() {
		FilialDTO filial = new FilialDTO();
		filial.setNome("Filial 1");
		filial.setCnpj("04400990000106");
		filial.setCidade("Osasco");
		filial.setUf("SP");
		filial.setTipo(ETipo.CD);
		filial.setAtivo(true);
		filial.setDataCadastro(new Date());
		return filial;
	}

	private Filial validFilial() {
		Filial filial = new Filial();
		filial.setId(1);
		filial.setNome("Filial 1");
		filial.setCnpj("04400990000106");
		filial.setCidade("Osasco");
		filial.setUf("SP");
		filial.setTipo(ETipo.CD);
		filial.setAtivo(true);
		filial.setDataCadastro(new Date());
		return filial;
	}
	
	private Filial changedFilial() {
		Filial filial = new Filial();
		filial.setId(1);
		filial.setNome("Filial 1");
		filial.setCnpj("04400990000106");
		filial.setCidade("Osasco");
		filial.setUf("RJ");
		filial.setTipo(ETipo.CD);
		filial.setAtivo(true);
		filial.setDataCadastro(new Date());
		return filial;
	}

	private FilialDTO validFilialDTO() {
		FilialDTO filial = new FilialDTO();
		filial.setId(1);
		filial.setNome("Filial 1");
		filial.setCnpj("04400990000106");
		filial.setCidade("Osasco");
		filial.setUf("SP");
		filial.setTipo(ETipo.CD);
		filial.setAtivo(true);
		filial.setDataCadastro(new Date());
		filial.setCnpjEditado("04.400.990/0001-06");
		return filial;
	}	
}
