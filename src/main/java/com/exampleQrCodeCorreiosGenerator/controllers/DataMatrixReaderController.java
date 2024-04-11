package com.exampleQrCodeCorreiosGenerator.controllers;

import com.exampleQrCodeCorreiosGenerator.services.DataMatrixReaderService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/decode-datamatrix")
public class DataMatrixReaderController {

    private final DataMatrixReaderService dataMatrixReaderService;

    public DataMatrixReaderController(DataMatrixReaderService dataMatrixReaderService) {
        this.dataMatrixReaderService = dataMatrixReaderService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String decodeDataMatrix(@RequestParam("file") MultipartFile file) throws Exception {
        return dataMatrixReaderService.decodeBarcode(file.getInputStream());
    }
}
