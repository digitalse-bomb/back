package com.bombeiros.siteinterno.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.bombeiros.siteinterno.DTO.MilitarDTO;

@Entity
@Table(name = "MILITAR")
public class Militar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String numMatricula;

    @ManyToMany(mappedBy = "militares", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Documento> documentos;

    public Militar() {
    }

    public Militar(String numMatricula) {
        this.numMatricula = numMatricula;
    }

    public Militar(MilitarDTO militar) {
        this.numMatricula = militar.getNumMatricula();
    }

    public String getNumMatricula() {
        return numMatricula;
    }

    public void setNumMatricula(String numMatricula) {
        this.numMatricula = numMatricula;
    }

    
}
