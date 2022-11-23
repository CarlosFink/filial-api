package br.com.fink.filialapi.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import br.com.fink.filialapi.models.Filial;
import br.com.fink.filialapi.repositories.FilialRepository;
import br.com.fink.filialapi.services.exceptions.DataIntegrityViolationExcept;
import br.com.fink.filialapi.services.exceptions.ObjectNotFoundException;

@Service
public class FilialService {
    
    @Autowired
    private FilialRepository filialRepository;

    public List<Filial> ListAll() {
        return filialRepository.findAll();
    }

    public Filial findById(Integer id) {
        Optional<Filial> filial =  filialRepository.findById(id);              
        return filial.orElseThrow(() -> new ObjectNotFoundException("Filial " + id + " não encontrada"));
    }

    public Filial save(Filial filial) {
        if (filial.getId() != null) {
            this.findById(filial.getId());
        } 
        try {
            return filialRepository.save(filial);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationExcept("Filial não pode ser incluída ou alterada. CNPJ existente");
        }
    }        

    public void delete(Integer id) {
        this.findById(id);
        filialRepository.deleteById(id);
    }

    public Filial activateDeactivate(Integer id) {
        Filial filial = this.findById(id);
        filial.setAtivo(!filial.getAtivo());
        return filialRepository.save(filial);
    }    
}

