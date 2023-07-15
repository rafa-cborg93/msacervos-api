package br.cborg.msacervos.entity;

import br.cborg.msacervos.domain.acervo.vo.FormatoFisico;
import br.cborg.msacervos.domain.acervo.vo.Imprenta;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "acervo")
public class Acervo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer isbn;
    private String numeroChamada;
    private String autor;
    private String titulo;
    @Embedded
    private Imprenta imprenta;
    @Embedded
    private FormatoFisico formatoFisico;
    private String assuntos;
    private String outrosAutores;
    private Date dataCadastro;
    private Date dataAtualizacao;
}
