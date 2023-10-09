package br.cborg.msacervos.domain.acervo.service;

import br.cborg.msacervos.Exceptions.ISBNAlReadyExistsException;
import br.cborg.msacervos.Exceptions.ValidateRequestException;
import br.cborg.msacervos.constants.AcervoConstants;
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
            log.info("[AcervoService] createAcervo() - [START] start process of save acervo.");

            acervoValidator.validateAcervoRequest(request);
            acervoValidator.verifyIfIsbnAlreadyExists(request.getIsbn());
            Acervo acervo = acervoUtils.convertRequestToEntity(request);

            acervoRepository.save(acervo);

            log.info("[AcervoService] createAcervo() - acervo saved successfully.");
            return new DefaultResponse(HttpStatus.CREATED.value(), AcervoConstants.SUCCESSFULLY_TO_SAVE);
        } catch (ValidateRequestException v) {
            log.error("[AcervoService] createAcervo() - ValidateRequestException - error:{}", v.getMessage());
            return new DefaultResponse(HttpStatus.BAD_REQUEST.value(), v.getMessage());
        } catch (ISBNAlReadyExistsException i) {
            log.error("[AcervoService] createAcervo() - ISBNAlReadyExistsException - error:{}", i.getMessage());
            return new DefaultResponse(HttpStatus.BAD_REQUEST.value(), i.getMessage());
        } catch (Exception e) {
            log.error("[AcervoService] createAcervo() - Exception - error:{}", e.getMessage());
            return new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    public DefaultResponse getAcervoList(String isbn, String numeroChamada) {
        if (isbn != null && numeroChamada != null) {
            log.info("[AcervoService] getAcervoList() - finding acervo by isbn: {} and numeroChamada: {}", isbn, numeroChamada);
            return returnAcervoList(() -> acervoRepository.findAcervoListByIsbnAndNumeroChamada(isbn, numeroChamada));
        } else if (isbn != null) {
            log.info("[AcervoService] getAcervoList() - finding acervo by isbn: {}", isbn);
            return returnAcervoList(() -> acervoRepository.findAcervoListByIsbn(isbn));
        } else if (numeroChamada != null) {
            log.info("[AcervoService] getAcervoList() - finding acervo by numeroChamada: {}", numeroChamada);
            return returnAcervoList(() -> acervoRepository.findAcervoListByNumeroChamada(numeroChamada));
        } else {
            log.info("[AcervoService] getAcervoList() - finding all acervos.");
            return returnAcervoList(() -> acervoRepository.findAll());
        }
    }

    private DefaultResponse returnAcervoList(Supplier<List<Acervo>> listSupplier) {
        try {
            log.info("[AcervoService] returnAcervoList() - [START] start process of find acervos.");
            List<Acervo> acervoList = listSupplier.get();

            if (acervoList.isEmpty()) {
                log.error("[AcervoService] returnAcervoList() - acervo list is empty.");
                return new DefaultResponse(HttpStatus.OK.value(), AcervoConstants.EMPTY_LIST);
            }
            List<AcervoResponse> acervoResponseList = acervoUtils.convertToRsponseList(acervoList);

            log.info("[AcervoService] returnAcervoList() - [END] acervoResponseList: {}", acervoResponseList);
            return new DefaultResponse(HttpStatus.OK.value(), AcervoConstants.SUCCESSFULLY_TO_GENERATE_LIST, acervoResponseList);
        } catch (Exception e) {
            log.error("[AcervoService] returnAcervoList() - Exception - error:{}", e.getStackTrace());
            return new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    public DefaultResponse updateAcervo(Long id, AcervoRequest request) {
        try {
            log.info("[AcervoService] updateAcervo() - [START] start process of update acervo with id={}", id);

            Optional<Acervo> optional = acervoRepository.findById(id);
            if (!optional.isPresent()) {
                log.error("[AcervoService] updateAcervo() - acervo with id:{} not found.", id);
                return new DefaultResponse(HttpStatus.NOT_FOUND.value(), String.format(AcervoConstants.ID_NOT_FOUND, id));
            }
            Acervo acervo = acervoUtils.buildToUpdate(request, optional.get());
            acervoRepository.save(acervo);

            log.info("[AcervoService] updateAcervo() - acervo saved successfully.");
            return new DefaultResponse(HttpStatus.OK.value(), AcervoConstants.SUCCESSFULLY_TO_UPDATE);
        } catch (Exception e) {
            log.error("[AcervoService] updateAcervo() -Exception - error:{}", e.getStackTrace());
            return new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    public DefaultResponse deleteAcervo(Long id) {
        try {
            log.info("[AcervoService] deleteAcervo() - [START] start process of delete acervo with id={}", id);

            Optional<Acervo> optional = acervoRepository.findById(id);
            if (!optional.isPresent()) {
                log.error("[AcervoService] deleteAcervo() - acervo with id:{} not found.", id);
                return new DefaultResponse(HttpStatus.NOT_FOUND.value(), String.format(AcervoConstants.ID_NOT_FOUND, id));
            }
            Acervo acervo = optional.get();
            acervoRepository.delete(acervo);

            log.info("[AcervoService] deleteAcervo() - acervo deleted successfully.");
            return new DefaultResponse(HttpStatus.OK.value(), AcervoConstants.SUCCESSFULLY_TO_DELETE);
        } catch (Exception e) {
            log.error("[AcervoService] deleteAcervo() - Exception - error:{}", e.getStackTrace());
            return new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
