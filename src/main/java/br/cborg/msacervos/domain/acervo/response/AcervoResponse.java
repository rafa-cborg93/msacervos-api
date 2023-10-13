package br.cborg.msacervos.domain.acervo.response;

import br.cborg.msacervos.domain.acervo.vo.FormatoFisico;
import br.cborg.msacervos.domain.acervo.vo.Imprenta;
import lombok.Data;

import java.util.Date;

@Data
public class AcervoResponse {
    private Long id;
    private String isbn;
    private String numeroChamada;
    private String autor;
    private String titulo;
    private Imprenta imprenta;
    private FormatoFisico formatoFisico;
    private String assuntos;
    private String outrosAutores;
    private Date dataCadastro;
    private Date dataAtualizacao;
}


