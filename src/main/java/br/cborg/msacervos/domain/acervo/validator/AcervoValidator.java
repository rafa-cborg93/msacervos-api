package br.cborg.msacervos.domain.acervo.validator;

import br.cborg.msacervos.exceptions.ISBNAlReadyExistsException;
import br.cborg.msacervos.exceptions.ValidateRequestException;
import br.cborg.msacervos.constants.AcervoConstants;
import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import br.cborg.msacervos.entity.Acervo;
import br.cborg.msacervos.repository.AcervoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AcervoValidator {
    private final AcervoRepository acervoRepository;
    public void validateAcervoRequest(AcervoRequest request) {
        List<String> emptyFields = new ArrayList<>();

        if (request.getIsbn() == null  || request.getIsbn().isEmpty()) {
            emptyFields.add("isbn");
        }
        if (request.getNumeroChamada() == null || request.getNumeroChamada().isEmpty()) {
            emptyFields.add("numeroChamada");
        }
        if (request.getAutor() == null || request.getAutor().isEmpty()) {
            emptyFields.add("autor");
        }
        if (request.getTitulo() == null || request.getTitulo().isEmpty()) {
            emptyFields.add("titulo");
        }
        if (request.getImprenta() == null) {
            emptyFields.add("imprenta");
        }
        if (request.getFormatoFisico() == null) {
            emptyFields.add("formatoFisico");
        }
        if (request.getAssuntos() == null || request.getAssuntos().isEmpty()) {
            emptyFields.add("assuntos");
        }
        if (request.getOutrosAutores() == null || request.getOutrosAutores().isEmpty()) {
            emptyFields.add("outrosAutores");
        }
        if (!emptyFields.isEmpty()) {
            throw new ValidateRequestException("The following fields are required: " + emptyFields);
        }
    }

    public void verifyIfIsbnAlreadyExists(String isbn) {
        Optional<Acervo> acervo = acervoRepository.findByIsbn(isbn);
        if (acervo.isPresent()) {
            throw new ISBNAlReadyExistsException(String.format(AcervoConstants.ISBN_ALREADY_EXISTS, isbn));
        }
    }
}
