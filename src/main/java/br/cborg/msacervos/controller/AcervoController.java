package br.cborg.msacervos.controller;

import br.cborg.msacervos.domain.DefaultResponse;
import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import br.cborg.msacervos.domain.acervo.service.AcervoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/acervo")
@RequiredArgsConstructor
public class AcervoController {

    private final AcervoService acervoService;
    @GetMapping("/search")
    public ResponseEntity<DefaultResponse> getAcervos(@RequestParam(value = "isbn", required = false) String isbn, @RequestParam(value = "numeroChamada", required = false) String numeroChamada) {
        DefaultResponse response = acervoService.getAcervoList(isbn, numeroChamada);
        return ResponseEntity.status(response.getCode()).body(response);
    }
    @PostMapping("/create")
    public ResponseEntity<DefaultResponse> postAcervo(@RequestBody AcervoRequest request) {
        DefaultResponse response = acervoService.createAcervo(request);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
