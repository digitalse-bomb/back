package com.bombeiros.siteinterno.services;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OcrService {
    
    //Em teste
    public MultiValueMap<String,String> send(MultipartFile file) throws IOException {
        
        // converte arquivo para formato enviavel
        ByteArrayResource fileConvertido = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        // seta o media type para o correto (opcional, não faz diferença na pratica)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // define um tipo de "json" para enviar os dados
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileConvertido);

        // cria entidade de resposta (opcional, não faz diferença na pratica)
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // envia um post para o serviço do ocr e recebe a resposta
        RestTemplate restTemplate = new RestTemplate();
        
        // retorna a resposta do ocr (precisa mudar pro formato la de
        // ResponseEntity.ok(resultado);
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:9191/ocr/extrair", requestEntity, String.class);

        MultiValueMap<String,String> response = new LinkedMultiValueMap<>();

        response.add("txt", result.getBody());

        return response;
    }
}