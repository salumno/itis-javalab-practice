package ru.kpfu.itis.catservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.catservice.model.dto.CatDto;
import ru.kpfu.itis.catservice.service.CatService;

@RestController
@RequestMapping("/cats")
public class CatController {
    private final CatService catService;

    public CatController(final CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/search")
    public ResponseEntity<CatDto> getCat() {
        return ResponseEntity.ok(catService.getCat());
    }
}