package br.cborg.msacervos.service;

import br.cborg.msacervos.exceptions.ISBNAlReadyExistsException;
import br.cborg.msacervos.exceptions.ValidateRequestException;
import br.cborg.msacervos.constants.AcervoConstants;
import br.cborg.msacervos.domain.DefaultResponse;
import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import br.cborg.msacervos.domain.acervo.response.AcervoResponse;
import br.cborg.msacervos.domain.acervo.validator.AcervoValidator;
import br.cborg.msacervos.domain.acervo.vo.FormatoFisico;
import br.cborg.msacervos.domain.acervo.vo.Imprenta;
import br.cborg.msacervos.entity.Acervo;
import br.cborg.msacervos.repository.AcervoRepository;
import br.cborg.msacervos.utils.AcervoUtils;
import br.cborg.msacervos.utils.TestUtils;
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
import java.util.Optional;

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
    void createAcervoTest() {
        Acervo acervo = getAcervo();
        AcervoRequest acervoRequest = TestUtils.getAcervoRequest();

        when(acervoUtils.convertRequestToEntity(acervoRequest)).thenReturn(acervo);

        DefaultResponse response = acervoService.createAcervo(acervoRequest);

        verify(acervoRepository, times(1)).save(any());
        assertDoesNotThrow(() -> acervoService.createAcervo(acervoRequest));
        assertEquals(HttpStatus.CREATED.value(), response.getCode());
        assertEquals(AcervoConstants.SUCCESSFULLY_TO_SAVE, response.getMessage());
        assertNull(response.getData());
    }
    @Test
    void createAcervoReturnAnExceptionTest() {
        Acervo acervo = getAcervo();
        AcervoRequest acervoRequest = TestUtils.getAcervoRequest();

        when(acervoUtils.convertRequestToEntity(acervoRequest)).thenReturn(acervo);
        doThrow(PersistenceException.class).when(acervoRepository).save(acervo);

        DefaultResponse response = acervoService.createAcervo(acervoRequest);

        verify(acervoRepository, times(1)).save(acervo);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getCode());
        assertNull(response.getData());
    }

    @Test
    void createAcervoValidateRequestErrorTest() {
        AcervoRequest acervoRequest = TestUtils.getAcervoRequest();
        doThrow(ValidateRequestException.class).when(acervoValidator).validateAcervoRequest(acervoRequest);

        DefaultResponse response = acervoService.createAcervo(acervoRequest);

        verify(acervoValidator, times(1)).validateAcervoRequest(acervoRequest);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getCode());
    }
    @Test
    void createAcervoIfAcervoExistsErrorTest(){
        AcervoRequest acervoRequest = TestUtils.getAcervoRequest();
        Acervo acervo = getAcervo();

        when(acervoUtils.convertRequestToEntity(acervoRequest)).thenReturn(acervo);
        doThrow(ISBNAlReadyExistsException.class).when(acervoRepository).save(acervo);

        DefaultResponse response = acervoService.createAcervo(acervoRequest);

        verify(acervoRepository, times(1)).save(acervo);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getCode());
    }

    @Test
    void getAcervoListByIsbnAndNumeroChamada() {
        AcervoResponse acervoResponse = getAcervoResponse();
        List<AcervoResponse> responseList = List.of(acervoResponse);
        List<Acervo> acervoList = List.of(getAcervo());

        when(acervoRepository.findAcervoListByIsbnAndNumeroChamada("123", "123")).thenReturn(acervoList);
        when(acervoUtils.convertToRsponseList(acervoList)).thenReturn(responseList);

        DefaultResponse response = acervoService.getAcervoList("123", "123");

        assertEquals(HttpStatus.OK.value(), response.getCode());
        verify(acervoRepository, times(1)).findAcervoListByIsbnAndNumeroChamada("123", "123");
    }

    @Test
    void getAcervoListByIsbn() {
        AcervoResponse acervoResponse = getAcervoResponse();
        List<AcervoResponse> responseList = List.of(acervoResponse);
        List<Acervo> acervoList = List.of(getAcervo());

        when(acervoRepository.findAcervoListByIsbn("123")).thenReturn(acervoList);
        when(acervoUtils.convertToRsponseList(acervoList)).thenReturn(responseList);

        DefaultResponse response = acervoService.getAcervoList("123", null);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        verify(acervoRepository, times(1)).findAcervoListByIsbn("123");
    }

    @Test
    void getAcervoListByNumeroChamada() {
        AcervoResponse acervoResponse = getAcervoResponse();
        List<AcervoResponse> responseList = List.of(acervoResponse);
        List<Acervo> acervoList = List.of(getAcervo());

        when(acervoRepository.findAcervoListByNumeroChamada("123")).thenReturn(acervoList);
        when(acervoUtils.convertToRsponseList(acervoList)).thenReturn(responseList);

        DefaultResponse response = acervoService.getAcervoList(null, "123");

        assertEquals(HttpStatus.OK.value(), response.getCode());
        verify(acervoRepository, times(1)).findAcervoListByNumeroChamada("123");
    }

    @Test
    void getAllAcervos() {
        List<Acervo> acervoList = List.of(getAcervo());
        AcervoResponse acervoResponse = getAcervoResponse();
        List<AcervoResponse> responseList = List.of(acervoResponse);

        when(acervoRepository.findAll()).thenReturn(acervoList);
        when(acervoUtils.convertToRsponseList(acervoList)).thenReturn(responseList);

        DefaultResponse response = acervoService.getAcervoList(null, null);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        verify(acervoRepository, times(1)).findAll();
    }

    @Test
    void getAcervosReturnEmptyList() {
        List<Acervo> acervoList = new ArrayList<>();

        when(acervoRepository.findAll()).thenReturn(acervoList);

        DefaultResponse response = acervoService.getAcervoList(null, null);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        verify(acervoRepository, times(1)).findAll();
    }

    @Test
    void getAcervoListError() {
        when(acervoRepository.findAll()).thenThrow(PersistenceException.class);

        DefaultResponse response = acervoService.getAcervoList(null, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getCode());
        verify(acervoRepository, times(1)).findAll();
    }

    @Test
    void updateAcervo() {
        AcervoRequest request = TestUtils.getAcervoRequest();
        Acervo acervo = getAcervo();
        when(acervoRepository.findById(1L)).thenReturn(Optional.of(acervo));
        when(acervoUtils.convertRequestToEntity(request)).thenReturn(acervo);
        when(acervoRepository.save(acervo)).thenReturn(acervo);

        DefaultResponse response = acervoService.updateAcervo(1L, request);
        assertEquals(HttpStatus.OK.value(), response.getCode());
        verify(acervoRepository, times(1)).findById(1L);
    }
    @Test
    void updateAcervoValidateRequestError() {
        AcervoRequest request = TestUtils.getAcervoRequest();
        doThrow(PersistenceException.class).when(acervoRepository).findById(1L);

        DefaultResponse response = acervoService.updateAcervo(1L, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getCode());
        verify(acervoRepository, times(1)).findById(1L);
    }

    @Test
    void updateAcervoReturnNotFound(){
        AcervoRequest request = TestUtils.getAcervoRequest();
        when(acervoRepository.findById(1L)).thenReturn(Optional.empty());

        DefaultResponse response = acervoService.updateAcervo(1L, request);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getCode());
        verify(acervoRepository, times(1)).findById(1L);
    }

    @Test
    void deleteAcervo() {
        Acervo acervo = getAcervo();
        when(acervoRepository.findById(1L)).thenReturn(Optional.of(acervo));

        DefaultResponse response = acervoService.deleteAcervo(1L);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        verify(acervoRepository, times(1)).findById(1L);
    }

    @Test
    void deleteAcervoReturnNotFound() {
        when(acervoRepository.findById(1L)).thenReturn(Optional.empty());

        DefaultResponse response = acervoService.deleteAcervo(1L);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getCode());
        verify(acervoRepository, times(1)).findById(1L);
    }
    @Test
    void deleteAcervoError() {
        when(acervoRepository.findById(1L)).thenThrow(PersistenceException.class);

        DefaultResponse response = acervoService.deleteAcervo(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getCode());
        verify(acervoRepository, times(1)).findById(1L);
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
    public AcervoResponse getAcervoResponse(){
        AcervoResponse response = new AcervoResponse();
        response.setIsbn("123");
        response.setNumeroChamada("123");
        response.setTitulo("Titulo");
        response.setAutor("Autor");
        response.setImprenta(getImprenta());
        response.setFormatoFisico(getFormatoFisico());
        response.setAssuntos("Assuntos");
        response.setOutrosAutores("Outros Autores");
        response.setDataCadastro(new Date());
        response.setDataAtualizacao(new Date());
        return response;
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