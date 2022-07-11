package com.api.sigabem.services;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api.sigabem.dtos.ViaCepDto;
import com.api.sigabem.models.FreightCalculationModel;
import com.api.sigabem.pojos.DiscountAndDayShipPojo;
import com.api.sigabem.repositories.FreightCalculationRepository;

@Service
public class FreightCalculationService {

    final FreightCalculationRepository freightCalculationRepository;

    public FreightCalculationService(FreightCalculationRepository freightCalculationRepository) {
        this.freightCalculationRepository = freightCalculationRepository;
    }

    public ViaCepDto consultaCep(String cep) {
        return new RestTemplate().getForEntity("https://viacep.com.br/ws/" + cep + "/json", ViaCepDto.class).getBody();
    }

    public Float getPrice(Float peso) {
        return peso * 1.00F;
    }

    public DiscountAndDayShipPojo getDiscountAndDayShipping(ViaCepDto cepOrigem, ViaCepDto cepDestino) {
        DiscountAndDayShipPojo discountAndDayShipPojo = new DiscountAndDayShipPojo();
        if (cepOrigem.getDdd().equals(cepDestino.getDdd())) {
            discountAndDayShipPojo.setDiscount(50);
            discountAndDayShipPojo.setDay(1);
            return discountAndDayShipPojo;
        } else if (cepOrigem.getUf().equals(cepDestino.getUf())) {
            discountAndDayShipPojo.setDiscount(75);
            discountAndDayShipPojo.setDay(3);
            return discountAndDayShipPojo;
        } else {
            discountAndDayShipPojo.setDiscount(0);
            discountAndDayShipPojo.setDay(10);
            return discountAndDayShipPojo;
        }
    }

    public Float calculateDiscount(Float price, Integer discount) {
        return price * discount / 100f;
    }

    @Transactional
    public FreightCalculationModel save(FreightCalculationModel freightCalculationModel) {
        return freightCalculationRepository.save(freightCalculationModel);
    }

    public Optional<FreightCalculationModel> findById(UUID id) {
        return freightCalculationRepository.findById(id);
    }

    public Page<FreightCalculationModel> findAll(Pageable pageable) {
        return freightCalculationRepository.findAll(pageable);
    }

    @Transactional
    public void delete(FreightCalculationModel freightCalculationModel) {
        freightCalculationRepository.delete(freightCalculationModel);
    }
}
