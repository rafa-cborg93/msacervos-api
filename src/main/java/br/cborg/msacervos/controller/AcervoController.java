package br.cborg.msacervos.controller;

import br.cborg.msacervos.domain.DefaultResponse;
import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import br.cborg.msacervos.domain.acervo.service.AcervoService;
import br.cborg.msacervos.entity.Acervo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acervo")
@RequiredArgsConstructor
public class AcervoController {

    private final AcervoService acervoService;
    @GetMapping()
    public ResponseEntity<DefaultResponse> findAll() {
        DefaultResponse response = acervoService.findAll();
        return ResponseEntity.status(response.getCode()).body(response);
    }
    @PostMapping
    public ResponseEntity<DefaultResponse> save(@RequestBody AcervoRequest request) {
        DefaultResponse response = acervoService.save(request);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
