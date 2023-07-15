package br.cborg.msacervos.utils;

import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import br.cborg.msacervos.entity.Acervo;
import org.springframework.stereotype.Component;

import java.util.Date;

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
}
