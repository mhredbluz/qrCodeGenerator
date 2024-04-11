package com.exampleQrCodeCorreiosGenerator.services;

import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.*;

@Service
public class DataMatrixReaderService {

    public String decodeBarcode(InputStream barcodeInputStream) throws Exception {
        BufferedImage barcodeBufferedImage = ImageIO.read(barcodeInputStream);
        LuminanceSource source = new BufferedImageLuminanceSource(barcodeBufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

        // Configurar os formatos de códigos de barras que o leitor deve tentar reconhecer
        List<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.DATA_MATRIX);
        formats.add(BarcodeFormat.CODE_128);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, formats);

        Result result = new MultiFormatReader().decode(bitmap, hints);

        return result.getText();
    }
}