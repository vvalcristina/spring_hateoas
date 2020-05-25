package com.dio.hateoas.service;

import com.dio.hateoas.controller.request.SoldadoEditRequest;
import com.dio.hateoas.controller.response.SoldadoListResponse;
import com.dio.hateoas.controller.response.SoldadoResponse;
import com.dio.hateoas.dto.Soldado;
import com.dio.hateoas.entity.SoldadoEntity;
import com.dio.hateoas.repository.SoldadoRepository;
import com.dio.hateoas.resource.SoldadoResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoldadoService {

    private SoldadoRepository soldadoRepository;
    private ObjectMapper objectMapper;
    private SoldadoResource soldadoResource;

    public SoldadoService(SoldadoRepository soldadoRepository, ObjectMapper objectMapper, SoldadoResource soldadoResource) {
        this.soldadoRepository = soldadoRepository;
        this.objectMapper = objectMapper;
        this.soldadoResource = soldadoResource;
    }

    public SoldadoResponse buscarSoldado(Long id){
        SoldadoEntity soldado = soldadoRepository.findById(id).orElseThrow();
        SoldadoResponse soldadoResponse=soldadoResource.criarLinkDetalhe(soldado);
        return soldadoResponse;
    }

    public void criarSoldado(Soldado soldado){
        SoldadoEntity soldadoEntity = objectMapper.convertValue(soldado, SoldadoEntity.class);
        soldadoRepository.save(soldadoEntity);
    }

    public void alterarSoldado(Long id, SoldadoEditRequest soldadoEditRequest) {
        SoldadoEntity soldadoEntity = objectMapper.convertValue(soldadoEditRequest, SoldadoEntity.class);
        soldadoEntity.setId(id);
        soldadoRepository.save(soldadoEntity);
    }

    public void deletarSoldado(Long id) {
        SoldadoEntity soldado = soldadoRepository.findById(id).orElseThrow();
        soldadoRepository.delete(soldado);
    }

    public CollectionModel<SoldadoListResponse> buscarSoldados(){
       List<SoldadoEntity> all = soldadoRepository.findAll();
       List<SoldadoListResponse>soldadoStream = all.stream()
               .map(it -> soldadoResource.criarLink(it))
               .collect(Collectors.toList());
       return new CollectionModel<>(soldadoStream);
    }
}
