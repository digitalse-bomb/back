package com.bombeiros.siteinterno.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.bombeiros.siteinterno.DTO.ArquivoDTO;
import com.bombeiros.siteinterno.DTO.DocumentoDTO;
import com.bombeiros.siteinterno.models.Documento;
import com.bombeiros.siteinterno.repository.DocumentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class DocumentoService {

    @Autowired
    private DocumentRepository documentoRepository;

    public DocumentoDTO criar(DocumentoDTO documentoDTO) throws IOException {
        Documento documento = new Documento(documentoDTO.getTipo(), documentoDTO.getNome(), documentoDTO.getCriador(),
                documentoDTO.getDataHoraCadastro(), documentoDTO.getVisivel());
        documentoRepository.save(documento);
        return documentoDTO;
    }

    // mudar para string
    public List<DocumentoDTO> getDocumentos(Long id) throws IOException {
        List<DocumentoDTO> list = documentoRepository.findAll().stream().map(doc -> {
            return new DocumentoDTO(doc.getDocumento_id(), doc.getTipo(), doc.getNome(), doc.getCriador(),
                    doc.getDataHoraCadastro(), doc.getVisivel(), doc.getArquivos());
        }).collect(Collectors.toList());
        return list;
    }

    public List<ArquivoDTO> getArquivosDeDocumento(Long id) {
        List<ArquivoDTO> arquivoDTO = documentoRepository.getOne(id).getArquivos().stream().map(arquivo -> {
            // String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/todos/listar/")
            //         .path(arquivo.getArquivo_id().toString()).toUriString();
            return new ArquivoDTO(arquivo.getArquivo_id(), arquivo.getDocumento(), arquivo.getNome(), arquivo.getTipo(),
            arquivo.getCriador(), arquivo.getDataHoraCadastro(), arquivo.getStatus(), arquivo.getNoOcr(), arquivo.getTamanho());
        }).collect(Collectors.toList());
        return arquivoDTO;
    }

    // ------ V1 ------

    // // SALVAR
    // @Transactional
    // public Documento salvarV1(Bga artigo, MultipartFile file) throws IOException
    // {
    // String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    // Documento documento = new Documento(fileName, file.getContentType(),
    // file.getBytes());

    // artigoRepository.save(artigo);
    // documento.setBga(artigo);
    // documentoRepository.save(documento);

    // return documento;
    // }

    // // LISTAR DOCUMENTOS
    // public List<ArtigoResponseFile> getDocumentosV1(Long id) {

    // List<ArtigoResponseFile> files =
    // artigoRepository.findById(id).stream().map(artigo -> {

    // List<DocumentoResponseFile> documentos =
    // artigo.getDocumentos().stream().map(documento -> {

    // String fileDownloadUri =
    // ServletUriComponentsBuilder.fromCurrentContextPath().path("/todos/listar/")
    // .path(documento.getIdDocumento().toString()).toUriString();
    // return new DocumentoResponseFile(documento.getIdDocumento(),
    // documento.getName(), fileDownloadUri,
    // documento.getType(), documento.getDocumentoData().length);

    // }).collect(Collectors.toList());

    // return new ArtigoResponseFile(artigo.getId(), artigo.getNome(),
    // artigo.getNum(), documentos);
    // }).collect(Collectors.toList());

    // return files;
    // }

    // // LISTAR ARTIGOS
    // public List<BgaResponseFile> getArtigosV1() {
    // List<BgaResponseFile> files = artigoRepository.findAll().stream().map(artigo
    // -> {
    // return new BgaResponseFile(artigo.getId(), artigo.getNome(),
    // artigo.getNum());
    // }).collect(Collectors.toList());

    // return files;
    // }

    // // LISTAR DOCUMENTOS DE UM ARTIGO
    // public List<DocumentoResponseFile> getDocumentosDeUmArtigoV1(Long id) {

    // List<DocumentoResponseFile> responseFiles =
    // artigoRepository.getOne(id).getDocumentos().stream().map(documento -> {

    // String fileDownloadUri =
    // ServletUriComponentsBuilder.fromCurrentContextPath().path("/todos/listar/")
    // .path(documento.getIdDocumento().toString()).toUriString();
    // return new DocumentoResponseFile(documento.getIdDocumento(),
    // documento.getName(), fileDownloadUri,
    // documento.getType(), documento.getDocumentoData().length);

    // }).collect(Collectors.toList());

    // return responseFiles;
    // }

    // //------ EXTRAS TEMPORARIOS ------

    // // need refatoration / for tests, remove that on prod!!!!
    // public List<ArtigoResponseFile> getTudo() {
    // // to implement:
    // // get last bga, send 10 lasts and return the id of the last one as recursive
    // // method
    // // if next page, call the method again passing that x-10-1 as the next id for
    // // the next 10 docs

    // List<ArtigoResponseFile> files =
    // artigoRepository.findAll().stream().map(artigo -> {

    // List<Documento> documentos = artigo.getDocumentos();
    // Stream<Documento> documentosStream = documentos.stream();
    // List<DocumentoResponseFile> documentosRF = null;

    // documentosRF = documentosStream.map(documento -> {

    // String fileDownloadUri =
    // ServletUriComponentsBuilder.fromCurrentContextPath().path("/todos/listar/")
    // .path(documento.getIdDocumento().toString()).toUriString();

    // // todo
    // return new DocumentoResponseFile(documento.getIdDocumento(),
    // documento.getName(), fileDownloadUri,
    // documento.getType(), 0/* documento.getDocumentoData().length */);
    // }).collect(Collectors.toList());

    // return new ArtigoResponseFile(artigo.getId(), artigo.getNome(),
    // artigo.getNum(), documentosRF);
    // }).collect(Collectors.toList());

    // return files;
    // }

    // Necessario no documentoUploadController por algum motivo
    // public Documento salvarDocumento(MultipartFile file) throws IOException {
    // String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    // Documento documento = new Documento(fileName, file.getContentType(),
    // file.getBytes());

    // return documentoRepository.save(documento);
    // }

    // Método de retornar uma documento pelo seu ID, porem ele itera por todos os
    // documentos... não apenas no bga.
    /*
     * public Documento getDocumento(Long id) { return
     * documentoRepository.findById(id).get(); }
     */

    // Método de retornar todas as documentos
    /*
     * public Stream<Documento> getAllDocumentos() { return
     * documentoRepository.findAll().stream(); }
     */

}