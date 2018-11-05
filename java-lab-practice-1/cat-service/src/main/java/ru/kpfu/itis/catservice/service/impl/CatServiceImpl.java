package ru.kpfu.itis.catservice.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.catservice.model.dto.CatDto;
import ru.kpfu.itis.catservice.service.CatService;

@Service
public class CatServiceImpl implements CatService {

    @Value("${cat.api.url}")
    private String catServiceApiUrl;

    private final RestTemplate restTemplate;

    public CatServiceImpl(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CatDto getCat() {
        return restTemplate.getForEntity(catServiceApiUrl + "/search", CatDto[].class).getBody()[0];
    }
}
