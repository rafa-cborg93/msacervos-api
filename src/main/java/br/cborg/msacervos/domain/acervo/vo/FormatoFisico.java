package br.cborg.msacervos.domain.acervo.vo;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class FormatoFisico {
    private Integer paginas;
    private boolean il;
    private String dimensoes;
}
