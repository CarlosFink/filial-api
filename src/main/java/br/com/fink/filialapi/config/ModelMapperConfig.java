package br.com.fink.filialapi.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.fink.filialapi.models.Filial;
import br.com.fink.filialapi.payload.FilialDTO;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper filialModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        final Converter<String, String> cnpjConverter = context -> editCnpj(context.getSource());
        PropertyMap<Filial, FilialDTO> cnpjMap = new PropertyMap<Filial, FilialDTO>() {
            protected void configure() {
                using(cnpjConverter).map(source.getCnpj(), destination.getCnpjEditado());
            }
        };
        modelMapper.addMappings(cnpjMap);
        return modelMapper;
    }

    private String editCnpj(String cnpj) {
        Pattern pattern = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})");
        Matcher matcher = pattern.matcher(cnpj);
        if (matcher.find()) {
            return matcher.replaceAll("$1.$2.$3/$4-$5");
        }
        return null;
    }
}
