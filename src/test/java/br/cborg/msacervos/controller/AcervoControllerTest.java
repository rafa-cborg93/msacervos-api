package br.cborg.msacervos.controller;

import br.cborg.msacervos.domain.DefaultResponse;
import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import br.cborg.msacervos.domain.acervo.response.AcervoResponse;
import br.cborg.msacervos.domain.acervo.service.AcervoService;
import br.cborg.msacervos.domain.acervo.vo.FormatoFisico;
import br.cborg.msacervos.domain.acervo.vo.Imprenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
class AcervoControllerTest {
    @InjectMocks
    AcervoController acervoController;
    @Mock
    AcervoService acervoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAcervos() {
        List<AcervoResponse> acervoResponseList = new ArrayList<>();
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.OK.value(), "Empty List.", acervoResponseList);
        when(acervoService.getAcervoList("123", "123")).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.getAcervos("123", "123");

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(acervoService).getAcervoList("123", "123");
    }

    @Test
    void getAcervoReturnError() {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "BAD_REQUEST");
        when(acervoService.getAcervoList("123", "123")).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.getAcervos("123", "123");

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        verify(acervoService).getAcervoList("123", "123");
    }

    @Test
    void postAcervo() {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.CREATED.value(), "Acervo created successfully.");
        when(acervoService.createAcervo(getAcervoRequest())).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.postAcervo(getAcervoRequest());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        verify(acervoService).createAcervo(getAcervoRequest());
    }

    @Test
    void postAcervoError() {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "BAD_REQUEST");
        when(acervoService.createAcervo(getAcervoRequest())).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.postAcervo(getAcervoRequest());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        verify(acervoService).createAcervo(getAcervoRequest());
    }

    @Test
    void updateAcervo() {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.OK.value(), "Acervo updated successfully.");
        when(acervoService.updateAcervo(1L, getAcervoRequest())).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.updateAcervo(1L, getAcervoRequest());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(acervoService).updateAcervo(1L, getAcervoRequest());
    }
    @Test
    void updateAcervoError(){
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "BAD_REQUEST");
        when(acervoService.updateAcervo(1L, getAcervoRequest())).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.updateAcervo(1L, getAcervoRequest());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        verify(acervoService).updateAcervo(1L, getAcervoRequest());
    }
    @Test
    void updateAcervoNotFoundError(){
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.NOT_FOUND.value(), "Acervo not found.");
        when(acervoService.updateAcervo(1L, getAcervoRequest())).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.updateAcervo(1L, getAcervoRequest());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        verify(acervoService).updateAcervo(1L, getAcervoRequest());
    }

    @Test
    void deleteAcervo() {
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.OK.value(), "Acervo deleted successfully.");
        when(acervoService.deleteAcervo(1L)).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.deleteAcervo(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        verify(acervoService).deleteAcervo(1L);
    }

    @Test
    void deleteAcervoError(){
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "BAD_REQUEST");
        when(acervoService.deleteAcervo(1L)).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.deleteAcervo(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        verify(acervoService).deleteAcervo(1L);
    }

    @Test
    void updateAcervoErrorNotFound(){
        DefaultResponse serviceResponse = new DefaultResponse(HttpStatus.NOT_FOUND.value(), "Acervo not found.");
        when(acervoService.deleteAcervo(1L)).thenReturn(serviceResponse);

        ResponseEntity<DefaultResponse> response = acervoController.deleteAcervo(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        verify(acervoService).deleteAcervo(1L);
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
        formatoFisico.setDimensoes("12cm x 20cm");
        return formatoFisico;
    }
}