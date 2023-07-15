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
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcervoService {
    private final AcervoRepository acervoRepository;
    private final AcervoUtils acervoUtils;
    private final AcervoValidator acervoValidator;

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

    public DefaultResponse updateAcervo(Long id, AcervoRequest request) {
        try {
            log.info("updateAcervo({},{}) - [START] start process of update acervo.", id, request);

            log.info("updateAcervo() - verifying if isbn already exists.");
            Optional<Acervo> optional = acervoRepository.findById(id);
            if (!optional.isPresent()) {
                log.error("updateAcervo() - acervo with id:{} not found.", id);
                return new DefaultResponse(HttpStatus.NOT_FOUND.value(), "Acervo with id:" + id + " not found.");
            }
            log.info("updateAcervo() - converting request to entity.");
            Acervo acervo = acervoUtils.buildToUpdate(request, optional.get());

            log.info("updateAcervo() - acervo: {}", acervo);
            acervoRepository.save(acervo);

            log.info("updateAcervo() - acervo saved successfully.");
            return new DefaultResponse(HttpStatus.OK.value(), "Successfully to update.");
        } catch (Exception e) {
            log.error("updateAcervo() - error:{}", e.getStackTrace());
            return new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    public DefaultResponse deleteAcervo(Long id) {
        try {
            log.info("deleteAcervo({}) - [START] start process of delete acervo.", id);

            log.info("deleteAcervo() - verifying if isbn already exists.");
            Optional<Acervo> optional = acervoRepository.findById(id);
            if (!optional.isPresent()) {
                log.error("deleteAcervo() - acervo with id:{} not found.", id);
                return new DefaultResponse(HttpStatus.NOT_FOUND.value(), "Acervo with id:" + id + " not found.");
            }
            Acervo acervo = optional.get();
            log.info("deleteAcervo() - acervo: {}", acervo);
            acervoRepository.delete(acervo);

            log.info("deleteAcervo() - acervo deleted successfully.");
            return new DefaultResponse(HttpStatus.OK.value(), "Successfully to delete.");
        } catch (Exception e) {
            log.error("deleteAcervo() - error:{}", e.getStackTrace());
            return new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
