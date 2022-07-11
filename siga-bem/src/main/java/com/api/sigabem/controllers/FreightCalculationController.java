package com.api.sigabem.controllers;

import java.time.*;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.api.sigabem.dtos.FreightCalculationDto;
import com.api.sigabem.dtos.ViaCepDto;
import com.api.sigabem.models.FreightCalculationModel;
import com.api.sigabem.pojos.DiscountAndDayShipPojo;
import com.api.sigabem.services.FreightCalculationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

@Controller
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "API REST Calculo Frete")
@CrossOrigin(origins = "*")
public class FreightCalculationController {

    final FreightCalculationService freightCalculationService;

    public FreightCalculationController(FreightCalculationService freightCalculationService) {
        this.freightCalculationService = freightCalculationService;
    }

    @GetMapping("/freight-calculation/{id}")
    @ApiOperation(value = "Retorna um unico frete")
    public ResponseEntity<Object> getFreight(@PathVariable(value = "id") UUID id) {
        Optional<FreightCalculationModel> freightCalculationModelOptional = freightCalculationService.findById(id);
        if (!freightCalculationModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Freight not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(freightCalculationModelOptional.get());
    }

    @GetMapping("/freight-calculation")
    @ApiOperation(value = "Retorna uma lista de fretes")
    public ResponseEntity<Page<FreightCalculationModel>> getAllParkingSpots(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(freightCalculationService.findAll(pageable));
    }

    @DeleteMapping("/freight-calculation/{id}")
    @ApiOperation(value = "Deleta um frete")
    public ResponseEntity<Object> deleteFreight(@PathVariable(value = "id") UUID id) {
        Optional<FreightCalculationModel> freightCalculationModelOptional = freightCalculationService.findById(id);
        if (!freightCalculationModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Freight not found");
        }
        freightCalculationService.delete(freightCalculationModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Freight deleted successfully");
    }

    @PostMapping("/freight-calculation")
    @ApiOperation(value = "Salva um frete")
    public ResponseEntity<Object> saveFreightCalculation(
            @RequestBody @Valid FreightCalculationDto freightCalculationDto) {
        ViaCepDto cepOrigem = freightCalculationService.consultaCep(freightCalculationDto.getCepOrigem());
        ViaCepDto cepDestino = freightCalculationService.consultaCep(freightCalculationDto.getCepDestino());
        Float price = freightCalculationService.getPrice(freightCalculationDto.getPeso());
        DiscountAndDayShipPojo discountAndDay = freightCalculationService.getDiscountAndDayShipping(cepOrigem,
                cepDestino);
        Float discount = freightCalculationService.calculateDiscount(price, discountAndDay.getDiscount());
        Float newPrice = price - discount;

        FreightCalculationModel freightCalculationModel = new FreightCalculationModel();
        BeanUtils.copyProperties(freightCalculationDto, freightCalculationModel);
        freightCalculationModel.setDataConsulta(LocalDateTime.now(ZoneId.of("UTC")));
        freightCalculationModel
                .setDataPrevistaEntrega(LocalDateTime.now(ZoneId.of("UTC")).plusDays(discountAndDay.getDay()));
        freightCalculationModel.setVlTotalFrete(newPrice);

        FreightCalculationModel savedFreightCalculationModel = freightCalculationService.save(freightCalculationModel);
        JSONObject resp = new JSONObject();
        resp.put("vlTotalFrete", savedFreightCalculationModel.getVlTotalFrete());
        resp.put("dataPrevistaEntrega", savedFreightCalculationModel.getDataPrevistaEntrega());
        resp.put("cepOrigem", savedFreightCalculationModel.getCepOrigem());
        resp.put("cepDestino", savedFreightCalculationModel.getCepDestino());

        return ResponseEntity.status(HttpStatus.OK).body(resp.toString());

    }

    @PutMapping("/freight-calculation/{id}")
    @ApiOperation(value = "Atualiza um frete")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid FreightCalculationDto freightCalculationDto) {
        Optional<FreightCalculationModel> freightCalculationModelOptional = freightCalculationService.findById(id);
        if (!freightCalculationModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Freight not found");
        }
        var freightCalculationModel = freightCalculationModelOptional.get();

        ViaCepDto cepOrigem = freightCalculationService.consultaCep(freightCalculationDto.getCepOrigem());
        ViaCepDto cepDestino = freightCalculationService.consultaCep(freightCalculationDto.getCepDestino());
        Float price = freightCalculationService.getPrice(freightCalculationDto.getPeso());
        DiscountAndDayShipPojo discountAndDay = freightCalculationService.getDiscountAndDayShipping(cepOrigem,
                cepDestino);
        Float discount = freightCalculationService.calculateDiscount(price, discountAndDay.getDiscount());
        Float newPrice = price - discount;
        BeanUtils.copyProperties(freightCalculationDto, freightCalculationModel);
        freightCalculationModel.setDataConsulta(LocalDateTime.now(ZoneId.of("UTC")));
        freightCalculationModel
                .setDataPrevistaEntrega(LocalDateTime.now(ZoneId.of("UTC")).plusDays(discountAndDay.getDay()));
        freightCalculationModel.setVlTotalFrete(newPrice);

        FreightCalculationModel savedFreightCalculationModel = freightCalculationService.save(freightCalculationModel);
        JSONObject resp = new JSONObject();
        resp.put("vlTotalFrete", savedFreightCalculationModel.getVlTotalFrete());
        resp.put("dataPrevistaEntrega", savedFreightCalculationModel.getDataPrevistaEntrega());
        resp.put("cepOrigem", savedFreightCalculationModel.getCepOrigem());
        resp.put("cepDestino", savedFreightCalculationModel.getCepDestino());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resp.toString());
    }
}
