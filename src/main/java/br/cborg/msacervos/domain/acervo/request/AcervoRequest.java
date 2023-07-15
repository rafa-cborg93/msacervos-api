package br.cborg.msacervos.domain.acervo.request;

import br.cborg.msacervos.domain.acervo.vo.FormatoFisico;
import br.cborg.msacervos.domain.acervo.vo.Imprenta;
import lombok.Data;

@Data
public class AcervoRequest {
    private Integer isbn;
    private String numeroChamada;
    private String autor;
    private String titulo;
    private Imprenta imprenta;
    private FormatoFisico formatoFisico;
    private String assuntos;
    private String outrosAutores;
}
