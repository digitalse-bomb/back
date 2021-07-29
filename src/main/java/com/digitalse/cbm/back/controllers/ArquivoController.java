package com.digitalse.cbm.back.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.digitalse.cbm.back.DTO.ArquivoDTO;
import com.digitalse.cbm.back.responseFiles.RFArquivo;
import com.digitalse.cbm.back.responseFiles.RFBucket;
import com.digitalse.cbm.back.responseFiles.RFCriarArquivo;
import com.digitalse.cbm.back.security.KeycloakUtils;
import com.digitalse.cbm.back.services.ArquivoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping
public class ArquivoController {

    @Autowired
    private ArquivoService arquivoService;

    private KeycloakUtils KCU = new KeycloakUtils();

    @ApiOperation(value = "Adiciona um ou mais arquivos a um documento")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Adicionou um ou mais arquivos a um documento e salvou no DB"),
            @ApiResponse(code = 404, message = "Não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção") })
    @PostMapping(value = "/documentos/{documento_id}/arquivos", consumes = { "multipart/form-data" })
    @ResponseBody
    public ResponseEntity<List<RFCriarArquivo>> criarArquivo(@PathVariable(required = true) long documento_id,
            @RequestPart(required = true) List<ArquivoDTO> arquivosDTO,
            @RequestPart(required = true) List<MultipartFile> files) throws IOException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(arquivoService.addAllArchives(documento_id,
                    new LinkedList<>(arquivosDTO), new LinkedList<>(files)));
        } catch (NullPointerException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //GEEEEEEEEET
    @ApiOperation(value = "Lista os arquivos de um documento")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Listou os arquivos de um documento"),
            @ApiResponse(code = 404, message = "Não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção") })
    @GetMapping("/documentos/{documento_id}/arquivos")
    @ResponseBody
    public ResponseEntity<List<RFArquivo>> listarArquivos(@RequestHeader HttpHeaders header, @PathVariable long documento_id) throws IOException {
        try {
            System.out.println(header.toString());
            /* if(KCU.isAuthorized(token)) {
                return ResponseEntity.status(HttpStatus.OK).body(arquivoService.getArchivesFromDocument(documento_id));
            } else {
                 */
                //return ResponseEntity.status(HttpStatus.OK).body(arquivoService.getArchivesFromDocumentExternal(documento_id));
                return null;
            /* } */
        } catch (NullPointerException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    //TODO: GEEEEEEEEET
    @ApiOperation(value = "Retorna um arquivo especifico de um documento")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Retornou um arquivo de um documento"),
            @ApiResponse(code = 404, message = "Não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção") })
    @GetMapping("/documentos/{documento_id}/arquivos/{arquivo_id}")
    @ResponseBody
    public ResponseEntity<RFArquivo> obterArquivos(@PathVariable long arquivo_id) throws IOException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(arquivoService.getArquivo(arquivo_id));
        } catch (NullPointerException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Atualiza um arquivo de um documento")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Atualizou um arquivo"),
            @ApiResponse(code = 404, message = "Não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção") })
    @PutMapping("/documentos/{documento_id}/arquivos/{arquivo_id}")
    @ResponseBody
    public ResponseEntity<RFArquivo> atualizarArquivos(@PathVariable long arquivo_id, @RequestBody ArquivoDTO arquivodto) throws IOException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(arquivoService.editar(arquivo_id, arquivodto));
        } catch (NullPointerException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //TODO: GEEEEEEEEET
    @ApiOperation(value = "Retorna um InputStreamResource de um arquivo")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Retornou um InputStreamResource"),
            @ApiResponse(code = 404, message = "Não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção") })
    @GetMapping("/documentos/{documento_id}/arquivos/{arquivo_id}/arquivo")
    @ResponseBody

    public ResponseEntity<InputStreamResource> obterDados(@PathVariable long arquivo_id) throws IOException {
        try {
            RFBucket rfbucket = arquivoService.getBucket(arquivo_id);
            return ResponseEntity.status(HttpStatus.OK).contentLength(rfbucket.getTamanho())
                    .contentType(org.springframework.http.MediaType.parseMediaType(rfbucket.getMime()))
                    .body(new InputStreamResource(new ByteArrayInputStream(rfbucket.getDados())));

        } catch (NullPointerException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Deleta um arquivo pelo seu ID")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Deletou um arquivo com sucesso"),
            @ApiResponse(code = 404, message = "Não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção") })
    @DeleteMapping("/documentos/{documento_id}/arquivos/{arquivo_id}")
    @ResponseBody
    public ResponseEntity<String> deletarArquivos(@PathVariable long arquivo_id) throws IOException {
        try {
            arquivoService.deletarArquivo(arquivo_id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NullPointerException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    } 
}
