package com.digitalse.cbm.back.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArquivoDTO {

    private Long id;
    private Long documento_id;
    private String nome;
    private Boolean ocr = false;
    private String status = "Concluido";
    private String texto;
    private Long bucket;
    private Date criado;
    private Date atualizado;

    public ArquivoDTO(Long id, String nome, Boolean ocr, String status, Date criado,
            Date atualizado) {
        this.id = id;
        this.nome = nome;
        this.ocr = ocr;
        this.status = status;
        this.criado = criado;
        this.atualizado = atualizado;
    }

    

}
