package com.dio.hateoas.resource;

import com.dio.hateoas.controller.SoldadoController;
import com.dio.hateoas.controller.response.SoldadoListResponse;
import com.dio.hateoas.entity.SoldadoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
}