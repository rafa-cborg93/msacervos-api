package br.cborg.msacervos.domain.acervo.service;

import br.cborg.msacervos.domain.DefaultResponse;
import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import br.cborg.msacervos.domain.acervo.validator.AcervoValidator;
import br.cborg.msacervos.entity.Acervo;
import br.cborg.msacervos.repository.AcervoRepository;
import br.cborg.msacervos.utils.AcervoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcervoService {
    private final AcervoRepository acervoRepository;
    private final AcervoUtils acervoUtils;
    private final AcervoValidator acervoValidator;

    public DefaultResponse findAll() {
        try {
            List<Acervo> acervoList = acervoRepository.findAll();
            return new DefaultResponse(HttpStatus.OK.value(), "Successfully to generate list.", acervoList);
        } catch (Exception e) {
            return new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    public DefaultResponse save(AcervoRequest request) {
        try {
            log.info("save({}) - [START] start process of save acervo.", request);

            log.info("save() - validating request.", request);
            acervoValidator.validateAcervoRequest(request);

            log.info("save() - verifying if isbn already exists.");
            acervoValidator.verifyIfIsbnAlreadyExists(request.getIsbn());

            log.info("save() - converting request to entity.");
            Acervo acervo = acervoUtils.convertRequestToEntity(request);

            log.info("save() - acervo: {}", acervo);
            acervoRepository.save(acervo);

            log.info("save() - acervo saved successfully.");
            return new DefaultResponse(HttpStatus.OK.value(), "Successfully to save.", acervo);
        } catch (Exception e) {
            log.error("save() - error:{}", e.getStackTrace());
            return new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
