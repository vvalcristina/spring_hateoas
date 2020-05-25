package com.dio.hateoas.resource;

import com.dio.hateoas.controller.SoldadoController;
import com.dio.hateoas.controller.response.SoldadoListResponse;
import com.dio.hateoas.controller.response.SoldadoResponse;
import com.dio.hateoas.entity.SoldadoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class SoldadoResource {

    private ObjectMapper objectMapper;

    public SoldadoResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public SoldadoListResponse criarLink(SoldadoEntity soldadoEntity) {
        SoldadoListResponse soldadoListResponse = objectMapper.convertValue(soldadoEntity, SoldadoListResponse.class);
        Link link= linkTo(methodOn(SoldadoController.class).buscarSoldado(soldadoEntity.getId())).withSelfRel();
        soldadoListResponse.add(link);
        return soldadoListResponse;
    }

    public SoldadoResponse criarLinkDetalhe(SoldadoEntity soldadoEntity) {
        SoldadoResponse soldadoListResponse = objectMapper.convertValue(soldadoEntity, SoldadoResponse.class);
        if (soldadoEntity.getStatus().equals("morto")){
            Link link= linkTo(methodOn(SoldadoController.class).deletarSoldado(soldadoEntity.getId()))
                    .withRel("remover")
                    .withTitle("Deletar Soldado")
                    .withType("delete");
            soldadoListResponse.add(link);
        }else if(soldadoEntity.getStatus().equals("vivo")) {
            Link link= linkTo(methodOn(SoldadoController.class).frenteCastelo(soldadoEntity.getId()))
                    .withRel("batalhar")
                    .withTitle("Ir para a frente do Castelo")
                    .withType("put");
            soldadoListResponse.add(link);
        }
        return soldadoListResponse;
    }
}
