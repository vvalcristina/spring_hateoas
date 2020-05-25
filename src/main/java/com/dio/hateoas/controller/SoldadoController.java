package com.dio.hateoas.controller;

import com.dio.hateoas.controller.request.SoldadoEditRequest;
import com.dio.hateoas.controller.response.SoldadoListResponse;
import com.dio.hateoas.controller.response.SoldadoResponse;
import com.dio.hateoas.dto.Soldado;
import com.dio.hateoas.service.SoldadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/soldado")
public class SoldadoController {

    public Object buscarSoldado;
    private ObjectMapper objectMapper;

    @Autowired
    SoldadoService soldadoService;

    @GetMapping("/{id}")
    public ResponseEntity<SoldadoResponse> buscarSoldado(@PathVariable() Long id) {
        SoldadoResponse soldadoResponse = soldadoService.buscarSoldado(id);
        return ResponseEntity.status(HttpStatus.OK).body(soldadoResponse);
    }

    @PostMapping
    public ResponseEntity criarSoldado(@RequestBody Soldado soldado) {
        soldadoService.criarSoldado(soldado);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity alterarSoldado(@PathVariable() Long id,
                                         @RequestBody SoldadoEditRequest soldadoEditRequest){
        soldadoService.alterarSoldado(id, soldadoEditRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarSoldado(@PathVariable Long id){
        soldadoService.deletarSoldado(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<CollectionModel<SoldadoListResponse>> buscarSoldados() {
        CollectionModel<SoldadoListResponse> soldadoListResponses= soldadoService.buscarSoldados();
        return ResponseEntity.status(HttpStatus.OK).body(soldadoListResponses);
    }

}
