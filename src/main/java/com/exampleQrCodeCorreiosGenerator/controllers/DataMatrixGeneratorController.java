package com.exampleQrCodeCorreiosGenerator.controllers;

import com.exampleQrCodeCorreiosGenerator.services.DataMatrixGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para geração de imagens de códigos DataMatrix.
 */
@RestController
public class DataMatrixGeneratorController {

    private final DataMatrixGeneratorService dataMatrixGeneratorService;

    /**
     * Construtor para injetar o serviço DataMatrixGeneratorService.
     *
     * @param dataMatrixGeneratorService O serviço DataMatrixGeneratorService a ser injetado
     */
    @Autowired
    public DataMatrixGeneratorController(DataMatrixGeneratorService dataMatrixGeneratorService){
        this.dataMatrixGeneratorService = dataMatrixGeneratorService;
    }

    /**
     * Endpoint para gerar uma imagem de código DataMatrix com base nos parâmetros fornecidos.
     *
     * @param arDigitalCode O código do AR Digital
     * @param clientCode O código do cliente
     * @return ResponseEntity contendo a imagem do código DataMatrix como array de bytes
     */
    @GetMapping(value = "/generate-datamatrix")
    public ResponseEntity<byte[]> generateDataMatrixImage(
            @RequestParam String arDigitalCode,
            @RequestParam String clientCode) {
        // Gerar a imagem do código DataMatrix com base nos parâmetros fornecidos
        try {
            byte[] imageData = dataMatrixGeneratorService.generateDataMatrix(arDigitalCode, clientCode);

            // Configurar os headers da resposta para indicar que o conteúdo é uma imagem PNG para download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentDispositionFormData("attachment", "datamatrix.png");

            // Retornar a imagem como ResponseEntity de sucesso
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageData);
        } catch (Exception e) {
            // Em caso de erro, imprimir o stack trace e retornar uma resposta de erro interno do servidor
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
