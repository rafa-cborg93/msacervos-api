package br.cborg.msacervos.utils;

import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import br.cborg.msacervos.domain.acervo.response.AcervoResponse;
import br.cborg.msacervos.domain.acervo.vo.FormatoFisico;
import br.cborg.msacervos.domain.acervo.vo.Imprenta;
import br.cborg.msacervos.entity.Acervo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
class AcervoUtilsTest {
    @InjectMocks
    AcervoUtils acervoUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convertRequestToEntity() {
        Acervo acervo = getAcervo();
        AcervoRequest request = getAcervoRequest();

        Acervo converterResponse = acervoUtils.convertRequestToEntity(request);
        assertNotNull(converterResponse);
        assertEquals(acervo.getClass(), converterResponse.getClass());
    }

    @Test
    void convertToRsponseList() {
        List<Acervo> acervoList = List.of(getAcervo());

        List<AcervoResponse> responseList = acervoUtils.convertToRsponseList(acervoList);
        assertNotNull(responseList);
        assertEquals(1, responseList.size());
    }

    @Test
    void buildToUpdate() {
        Acervo acervo = getAcervo();
        AcervoRequest request = getAcervoRequest();

        Acervo buildToUpdate = acervoUtils.buildToUpdate(request, acervo);

        assertNotNull(buildToUpdate);
        assertEquals(acervo.getClass(), buildToUpdate.getClass());
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

    public AcervoResponse getAcervoResponse() {
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