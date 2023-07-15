package br.cborg.msacervos.domain.acervo.service;

import br.cborg.msacervos.domain.DefaultResponse;
import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import br.cborg.msacervos.domain.acervo.response.AcervoResponse;
import br.cborg.msacervos.domain.acervo.validator.AcervoValidator;
import br.cborg.msacervos.entity.Acervo;
import br.cborg.msacervos.repository.AcervoRepository;
import br.cborg.msacervos.utils.AcervoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcervoService {
    private final AcervoRepository acervoRepository;
    private final AcervoUtils acervoUtils;
    private final AcervoValidator acervoValidator;

    public DefaultResponse getAcervoList(String isbn, String numeroChamada) {
        if (isbn != null && numeroChamada != null) {
            log.info("getAcervoList() - finding acervo by isbn: {} and numeroChamada: {}", isbn, numeroChamada);
            return returnAcervoList(() -> acervoRepository.findAcervoListByIsbnAndNumeroChamada(isbn, numeroChamada));
        } else if (isbn != null) {
            log.info("getAcervoList() - finding acervo by isbn: {}", isbn);
            return returnAcervoList(() -> acervoRepository.findAcervoListByIsbn(isbn));
        } else if (numeroChamada != null) {
            log.info("getAcervoList() - finding acervo by numeroChamada: {}", numeroChamada);
            return returnAcervoList(() -> acervoRepository.getAcervoListByNumeroChamada(numeroChamada));
        } else {
            log.info("getAcervoList() - finding all acervos.");
            return returnAcervoList(() -> acervoRepository.findAll());
        }
    }

    private DefaultResponse returnAcervoList(Supplier<List<Acervo>> listSupplier) {
        try {
            log.info("returnAcervoList() - [START] start process of find acervos.");

            log.info("returnAcervoList() - finding all acervo.");
            List<Acervo> acervoList = listSupplier.get();

            log.info("returnAcervoList() - converting list to response.");
            List<AcervoResponse> acervoResponseList = acervoUtils.convertToRsponseList(acervoList);

            log.info("returnAcervoList() - [END] acervoResponseList: {}", acervoResponseList);
            return new DefaultResponse(HttpStatus.OK.value(), "Successfully to generate list.", acervoResponseList);
        } catch (Exception e) {
            log.error("returnAcervoList() - error:{}", e.getStackTrace());
            return new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    public DefaultResponse createAcervo(AcervoRequest request) {
        try {
            log.info("createAcervo({}) - [START] start process of save acervo.", request);

            log.info("createAcervo() - validating request.", request);
            acervoValidator.validateAcervoRequest(request);

            log.info("createAcervo() - verifying if isbn already exists.");
            acervoValidator.verifyIfIsbnAlreadyExists(request.getIsbn());

            log.info("createAcervo() - converting request to entity.");
            Acervo acervo = acervoUtils.convertRequestToEntity(request);

            log.info("createAcervo() - acervo: {}", acervo);
            acervoRepository.save(acervo);

            log.info("createAcervo() - acervo saved successfully.");
            return new DefaultResponse(HttpStatus.OK.value(), "Successfully to save.");
        } catch (Exception e) {
            log.error("createAcervo() - error:{}", e.getStackTrace());
            return new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
