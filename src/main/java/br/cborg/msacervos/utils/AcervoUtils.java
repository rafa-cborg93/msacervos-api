package br.cborg.msacervos.utils;

import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import br.cborg.msacervos.domain.acervo.response.AcervoResponse;
import br.cborg.msacervos.entity.Acervo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AcervoUtils {

    public Acervo convertRequestToEntity(AcervoRequest request) {
        Acervo acervo = new Acervo();
        acervo.setIsbn(request.getIsbn());
        acervo.setNumeroChamada(request.getNumeroChamada());
        acervo.setAutor(request.getAutor());
        acervo.setTitulo(request.getTitulo());
        acervo.setImprenta(request.getImprenta());
        acervo.setFormatoFisico(request.getFormatoFisico());
        acervo.setAssuntos(request.getAssuntos());
        acervo.setOutrosAutores(request.getOutrosAutores());
        acervo.setDataCadastro(new Date());
        acervo.setDataAtualizacao(new Date());
        return acervo;
    }

    public List<AcervoResponse> convertToRsponseList(List<Acervo> acervoList) {
        List<AcervoResponse> acervoResponseList = new ArrayList<>();

        acervoList.forEach(acervo -> {
            AcervoResponse acervoResponse = new AcervoResponse();
            acervoResponse.setId(acervo.getId());
            acervoResponse.setIsbn(acervo.getIsbn());
            acervoResponse.setNumeroChamada(acervo.getNumeroChamada());
            acervoResponse.setAutor(acervo.getAutor());
            acervoResponse.setTitulo(acervo.getTitulo());
            acervoResponse.setImprenta(acervo.getImprenta());
            acervoResponse.setFormatoFisico(acervo.getFormatoFisico());
            acervoResponse.setAssuntos(acervo.getAssuntos());
            acervoResponse.setOutrosAutores(acervo.getOutrosAutores());
            acervoResponse.setDataCadastro(acervo.getDataCadastro());
            acervoResponse.setDataAtualizacao(acervo.getDataAtualizacao());

            acervoResponseList.add(acervoResponse);
        });
        return acervoResponseList;
    }

    public Acervo buildToUpdate(AcervoRequest request, Acervo acervo) {
        acervo.setIsbn(request.getIsbn());
        acervo.setNumeroChamada(request.getNumeroChamada());
        acervo.setAutor(request.getAutor());
        acervo.setTitulo(request.getTitulo());
        acervo.setImprenta(request.getImprenta());
        acervo.setFormatoFisico(request.getFormatoFisico());
        acervo.setAssuntos(request.getAssuntos());
        acervo.setOutrosAutores(request.getOutrosAutores());
        acervo.setDataAtualizacao(new Date());
        return acervo;
    }
}
