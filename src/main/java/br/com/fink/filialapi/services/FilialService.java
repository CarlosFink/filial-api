package br.com.fink.filialapi.services;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.fink.filialapi.models.Filial;
import br.com.fink.filialapi.payload.FilialDTO;
import br.com.fink.filialapi.repositories.FilialRepository;
import br.com.fink.filialapi.services.exceptions.ObjectNotFoundException;

@Service
public class FilialService {
    private static final Logger log = LoggerFactory.getLogger(FilialService.class);

    @Autowired
    private FilialRepository filialRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<FilialDTO> listFiliais(Pageable pageable) {
        log.info("Obtendo lista de Filiais");
        Page<Filial> filialList = filialRepository.findAll(pageable);
        log.info("{} filial(is) obtidas", filialList.getSize()); 
        return filialList.map(this::convertFilialToDto);
    }

    public FilialDTO findById(Integer id) {
        log.info("Obtendo Filial {} por id", id);
        Optional<Filial> filial = filialRepository.findById(id);
        if (filial.isPresent()) {
            log.info("Filial {} obtida com sucesso", id);
            return convertFilialToDto(filial.get());
        } else {
            throw new ObjectNotFoundException("Filial " + id + " não encontrada");
        }
    }

    public FilialDTO insert(FilialDTO filialDTO) {
        log.info("Incluindo nova Filial");
        filialDTO.setId(null);
        Filial filial = filialRepository.save(convertDtoToFilial(filialDTO));
        log.info("Filial {} incluida com sucesso", filial.getId());
        return convertFilialToDto(filial);
    }

    public FilialDTO update(FilialDTO filialDTO) {
        this.findById(filialDTO.getId());
        log.info("Alterando a Filial {}", filialDTO.getId());
        Filial filial = filialRepository.save(convertDtoToFilial(filialDTO));
        log.info("Filial {} alterada com sucesso", filial.getId());
        return convertFilialToDto(filial);
    }

    public void delete(Integer id) {
        this.findById(id);
        log.info("Excluindo a Filial {}", id);
        filialRepository.deleteById(id);
        log.info("Filial {} excluida com sucesso", id);
    }

    public FilialDTO activateDeactivate(Integer id) {
        FilialDTO filialDTO = this.findById(id);
        if (Boolean.FALSE.equals(filialDTO.getAtivo())) {
            log.info("Ativando Filial {}", id);
        } else {
            log.info("Desativando Filial {}", id);
        }
        filialDTO.setAtivo(!filialDTO.getAtivo());
        Filial filial = filialRepository.save(convertDtoToFilial(filialDTO));
        if (Boolean.TRUE.equals(filial.getAtivo())) {
            log.info("Filial {} ativada com sucesso", id);
        } else {
            log.info("Filial {} desativada com sucesso", id);
        }
        return convertFilialToDto(filial);
    }

    private FilialDTO convertFilialToDto(Filial filial) {
        return modelMapper.map(filial, FilialDTO.class);
    }

    private Filial convertDtoToFilial(FilialDTO filialDTO) {
        return modelMapper.map(filialDTO, Filial.class);
    }
}
