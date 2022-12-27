package br.com.fink.filialapi;

import java.util.Date;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fink.filialapi.models.ETipo;
import br.com.fink.filialapi.payload.FilialDTO;
import br.com.fink.filialapi.services.FilialService;
import br.com.fink.filialapi.services.exceptions.ObjectNotFoundException;

@WebMvcTest
@DisplayName("Teste de FilialController")
class FilialControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private FilialService filialService;

	@BeforeEach
	void setUp() throws Exception {		
		BDDMockito.given(filialService.findById(1)).willReturn(validFilial());
		BDDMockito.given(filialService.findById(2)).willThrow(ObjectNotFoundException.class);
	}

	@Test
	@DisplayName("Teste de GET Filiais - OK")
	void pageListFiliaisWhenSuccessful() throws Exception {
		PageImpl<FilialDTO> pageFilial = new PageImpl<>(List.of(this.validFilial()));
		BDDMockito.given(filialService.listFiliais(ArgumentMatchers.any())).willReturn(pageFilial);
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/filiais"));
		
		response.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size", CoreMatchers.is(pageFilial.getSize())));
	}
	
	@Test
	@DisplayName("Teste de GET Filial by id - OK")
	void findByIdFiliaisWhenSuccessful() throws Exception {
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/filiais/{id}", 1));
		
		FilialDTO filialDTO = validFilial();
		response.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.cnpj", CoreMatchers.is(filialDTO.getCnpj())));
	}
	
	@Test
	@DisplayName("Teste de GET Filial by id - NOT FOUND")
	void findByIdFiliaisWhenUnSuccessful() throws Exception {		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/filiais/{id}", 2));
		
		response.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	@DisplayName("Teste de POST Filial - OK")
	void createFiliaisWhenSuccessful() throws Exception {
		BDDMockito.given(filialService.insert(ArgumentMatchers.any(FilialDTO.class)))
			.willAnswer(invocation -> invocation.getArgument(0));
		
		FilialDTO filialDTO = validFilial();
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/filiais")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(filialDTO)));
		
		response.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.cnpj", CoreMatchers.is("04400990000106")));
	}
	
	@Test
	@DisplayName("Teste de POST Filial - Erro de validação")
	void createFiliaisValidationWhenUnsuccessful() throws Exception {
		BDDMockito.given(filialService.insert(ArgumentMatchers.any(FilialDTO.class)))
			.willAnswer(invocation -> invocation.getArgument(0));
		
		FilialDTO filialDTO = validFilial();
		filialDTO.setUf("");
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/filiais")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(filialDTO)));
		
		response.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Erro na validação dos campos")));
	}
	
	@Test
	@DisplayName("Teste de PUT Alteração Filial")
	void updateFiliaisWhenSuccessful() throws Exception {
		BDDMockito.given(filialService.update(ArgumentMatchers.any(FilialDTO.class)))
			.willAnswer(invocation -> invocation.getArgument(0));
		
		FilialDTO filialDTO = changedFilial();
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/filiais")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(filialDTO)));
		
		response.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.cnpj", CoreMatchers.is("07104810000137")));
	}

	@Test
	@DisplayName("Teste de PUT Ativação/Desativação Filial")
	void activateDeactivateFiliaisWhenSuccessful() throws Exception {
		BDDMockito.given(filialService.activateDeactivate(ArgumentMatchers.anyInt()))
			.willReturn(deactivatedFilial());		
			
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/filiais/{id}", 1));
		
		response.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.ativo", CoreMatchers.is(false)));
	}
	
	@Test
	@DisplayName("Teste de DELETE Filial")	
	void deleteFiliaisWhenSuccessful() throws Exception {
		BDDMockito.doNothing().when(filialService).delete(ArgumentMatchers.anyInt());		
			
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/filiais/{id}", 1));
		
		response.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
		
	private FilialDTO validFilial() {
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
	
	private FilialDTO changedFilial() {
		FilialDTO filial = new FilialDTO();
		filial.setId(1);
		filial.setNome("Filial 1");
		filial.setCnpj("07104810000137");
		filial.setCidade("Osasco");
		filial.setUf("RJ");
		filial.setTipo(ETipo.LOJA);
		filial.setAtivo(false);
		filial.setDataCadastro(new Date());
		filial.setCnpjEditado("07.104.810/0001-37");
		return filial;
	}
	
	private FilialDTO deactivatedFilial() {
		FilialDTO filial = new FilialDTO();
		filial.setId(1);
		filial.setNome("Filial 1");
		filial.setCnpj("04400990000106");
		filial.setCidade("Osasco");
		filial.setUf("SP");
		filial.setTipo(ETipo.CD);
		filial.setAtivo(false);
		filial.setDataCadastro(new Date());
		filial.setCnpjEditado("04.400.990/0001-06");
		return filial;
	}
}
