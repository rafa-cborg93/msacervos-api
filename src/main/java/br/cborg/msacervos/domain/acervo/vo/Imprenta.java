package br.cborg.msacervos.domain.acervo.vo;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Imprenta {
    private String local;
    private String editora;
    private String ano;
}
