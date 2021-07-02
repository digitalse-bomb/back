package com.digitalse.cbm.back.DTO;

import java.util.Date;

import com.digitalse.cbm.back.entities.Documento;

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
    private Documento documento;
    private String nome;
    private Boolean ocr = false;
    private String status;
    private String mime;
    private Long tamanho;
    private Byte[] dados;
    private String texto;
    private Date criado;
    private Date atualizado;

}
