package br.cborg.msacervos.domain.acervo.validator;

import br.cborg.msacervos.exceptions.ISBNAlReadyExistsException;
import br.cborg.msacervos.exceptions.ValidateRequestException;
import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import br.cborg.msacervos.domain.acervo.vo.FormatoFisico;
import br.cborg.msacervos.domain.acervo.vo.Imprenta;
import br.cborg.msacervos.entity.Acervo;
import br.cborg.msacervos.repository.AcervoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
class AcervoValidatorTest {

    @InjectMocks
    private AcervoValidator acervoValidator;

    @Mock
    private AcervoRepository acervoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateAcervoRequestTest() {
        AcervoRequest request = getAcervoRequest();
        acervoValidator.validateAcervoRequest(request);
        assertDoesNotThrow(() -> acervoValidator.validateAcervoRequest(request));
    }
    @Test
    void validateAcervoRequestTestWithNullIsbn() {
        AcervoRequest request = getAcervoRequest();
        request.setIsbn(null);
        assertThrows(ValidateRequestException.class, () -> acervoValidator.validateAcervoRequest(request));
    }
    @Test
    void validateAcervoRequestTestWithNullNumeroChamada() {
        AcervoRequest request = getAcervoRequest();
        request.setNumeroChamada(null);
        assertThrows(ValidateRequestException.class, () -> acervoValidator.validateAcervoRequest(request));
    }
    @Test
    void validateAcervoRequestTestWithNullAutor() {
        AcervoRequest request = getAcervoRequest();
        request.setAutor(null);
        assertThrows(ValidateRequestException.class, () -> acervoValidator.validateAcervoRequest(request));
    }
    @Test
    void validateAcervoRequestTestWithNullTitulo() {
        AcervoRequest request = getAcervoRequest();
        request.setTitulo(null);
        assertThrows(ValidateRequestException.class, () -> acervoValidator.validateAcervoRequest(request));
    }
    @Test
    void validateAcervoRequestTestWithNullImprenta() {
        AcervoRequest request = getAcervoRequest();
        request.setImprenta(null);
        assertThrows(ValidateRequestException.class, () -> acervoValidator.validateAcervoRequest(request));
    }
    @Test
    void validateAcervoRequestTestWithNullFormatoFisico() {
        AcervoRequest request = getAcervoRequest();
        request.setFormatoFisico(null);
        assertThrows(ValidateRequestException.class, () -> acervoValidator.validateAcervoRequest(request));
    }
    @Test
    void validateAcervoRequestTestWithNullAssuntos() {
        AcervoRequest request = getAcervoRequest();
        request.setAssuntos(null);
        assertThrows(ValidateRequestException.class, () -> acervoValidator.validateAcervoRequest(request));
    }
    @Test
    void validateAcervoRequestTestWithNullOutrosAutores() {
        AcervoRequest request = getAcervoRequest();
        request.setOutrosAutores(null);
        assertThrows(ValidateRequestException.class, () -> acervoValidator.validateAcervoRequest(request));
    }

    @Test
    void verifyIfIsbnAlreadyExists() {
        Acervo acervo = getAcervo();
        Optional<Acervo> acervoOptional = Optional.of(acervo);
        when(acervoRepository.findByIsbn(anyString())).thenReturn(acervoOptional);
        assertThrows(ISBNAlReadyExistsException.class, () -> acervoValidator.verifyIfIsbnAlreadyExists("123456789"));
    }

    public Acervo getAcervo() {
        Acervo acervo = new Acervo();
        acervo.setIsbn("123456789");
        acervo.setNumeroChamada("123456789");
        acervo.setTitulo("Titulo");
        acervo.setAutor("Autor");
        acervo.setImprenta(getImprenta());
        acervo.setFormatoFisico(getFormatoFisico());
        acervo.setAssuntos("Assuntos");
        acervo.setOutrosAutores("Outros Autores");
        acervo.setDataCadastro(new Date());
        acervo.setDataAtualizacao(new Date());
        return acervo;
    }
    public AcervoRequest getAcervoRequest() {
        AcervoRequest request = new AcervoRequest();
        request.setIsbn("123");
        request.setNumeroChamada("123");
        request.setTitulo("Titulo");
        request.setAutor("Autor");
        request.setImprenta(getImprenta());
        request.setFormatoFisico(getFormatoFisico());
        request.setAssuntos("Assuntos");
        request.setOutrosAutores("Outros Autores");
        return request;
    }

    public Imprenta getImprenta() {
        Imprenta imprenta = new Imprenta();
        imprenta.setEditora("Editora");
        imprenta.setLocal("Local");
        imprenta.setAno("Ano");
        return imprenta;
    }

    public FormatoFisico getFormatoFisico() {
        FormatoFisico formatoFisico = new FormatoFisico();
        formatoFisico.setPaginas(200);
        formatoFisico.setIl(true);
        formatoFisico.setDimensoes("20cm x 20cm");
        return formatoFisico;
    }

}