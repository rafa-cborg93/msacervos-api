package br.cborg.msacervos.domain.acervo.service;

import br.cborg.msacervos.domain.DefaultResponse;
import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import br.cborg.msacervos.domain.acervo.validator.AcervoValidator;
import br.cborg.msacervos.domain.acervo.vo.FormatoFisico;
import br.cborg.msacervos.domain.acervo.vo.Imprenta;
import br.cborg.msacervos.entity.Acervo;
import br.cborg.msacervos.repository.AcervoRepository;
import br.cborg.msacervos.utils.AcervoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
class AcervoServiceTest {

    @InjectMocks
    private AcervoService acervoService;
    @Mock
    private AcervoRepository acervoRepository;
    @Mock
    private AcervoUtils acervoUtils;
    @Mock
    private AcervoValidator acervoValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAcervo() {
        Acervo acervo = getAcervo();
        AcervoRequest acervoRequest = getAcervoRequest();

        when(acervoUtils.convertRequestToEntity(acervoRequest)).thenReturn(acervo);

        DefaultResponse response = acervoService.createAcervo(acervoRequest);

        verify(acervoRepository, times(1)).save(any());
        assertDoesNotThrow(() -> acervoService.createAcervo(acervoRequest));
        assertEquals(HttpStatus.CREATED.value(), response.getCode());
    }

    @Test
    void createAcervoValidateRequestError() {
        AcervoRequest acervoRequest = getAcervoRequest();
        doThrow(IllegalArgumentException.class).when(acervoValidator).validateAcervoRequest(acervoRequest);

        DefaultResponse response = acervoService.createAcervo(acervoRequest);

        verify(acervoValidator, times(1)).validateAcervoRequest(acervoRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getCode());
    }

    @Test
    void getAcervoListByIsbnAndNumeroChamada() {
        List<Acervo> acervoList = List.of(getAcervo());
        when(acervoRepository.findAcervoListByIsbnAndNumeroChamada("123", "123")).thenReturn(acervoList);

        DefaultResponse response = acervoService.getAcervoList("123", "123");

        assertEquals(HttpStatus.OK.value(), response.getCode());
        verify(acervoRepository, times(1)).findAcervoListByIsbnAndNumeroChamada("123", "123");
    }

    @Test
    void getAcervoListByIsbn() {
        List<Acervo> acervoList = List.of(getAcervo());
        when(acervoRepository.findAcervoListByIsbn("123")).thenReturn(acervoList);

        DefaultResponse response = acervoService.getAcervoList("123", null);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        verify(acervoRepository, times(1)).findAcervoListByIsbn("123");
    }

    @Test
    void getAcervoListByNumeroChamada() {
        List<Acervo> acervoList = List.of(getAcervo());
        when(acervoRepository.findAcervoListByNumeroChamada("123")).thenReturn(acervoList);

        DefaultResponse response = acervoService.getAcervoList(null, "123");

        assertEquals(HttpStatus.OK.value(), response.getCode());
        verify(acervoRepository, times(1)).findAcervoListByNumeroChamada("123");
    }

    @Test
    void getAllAcervos(){
        List<Acervo> acervoList = List.of(getAcervo());
        when(acervoRepository.findAll()).thenReturn(acervoList);

        DefaultResponse response = acervoService.getAcervoList(null, null);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        verify(acervoRepository, times(1)).findAll();
    }
    @Test
    void getAcervosReturnEmptyList(){
        List<Acervo> acervoList = new ArrayList<>();
        when(acervoRepository.findAll()).thenReturn(acervoList);

        DefaultResponse response = acervoService.getAcervoList(null, null);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        verify(acervoRepository, times(1)).findAll();
    }
    @Test
    void getAcervoListError(){
        when(acervoRepository.findAll()).thenThrow(PersistenceException.class);

        DefaultResponse response = acervoService.getAcervoList(null, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getCode());
        verify(acervoRepository, times(1)).findAll();
    }

    @Test
    void updateAcervo() {
    }

    @Test
    void deleteAcervo() {
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