package com.exampleQrCodeCorreiosGenerator.services;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Serviço para geração de código DataMatrix para AR Digital.
 */
@Service
public class DataMatrixGeneratorService {

    /**
     * Método para gerar um código DataMatrix com base no código do AR Digital e do cliente.
     *
     * @param arDigitalCode O código do AR Digital
     * @param clientCode O código do cliente
     * @return Um array de bytes representando a imagem do código DataMatrix gerado
     * @throws WriterException Se ocorrer um erro ao gerar o código DataMatrix
     * @throws IOException Se ocorrer um erro ao escrever a imagem para o stream
     */
    public byte[] generateDataMatrix(String arDigitalCode, String clientCode) throws WriterException, IOException {
        // Gerar o ID do AR Digital a partir do código fornecido
        String arDigitalId = generateArDigitalId(arDigitalCode);
        // Combinar o ID do AR Digital com o código do cliente para formar o conteúdo do DataMatrix
        String dataMatrixContent = arDigitalId + clientCode;

        // Configurar dicas (hints) para a geração do código DataMatrix
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.DATA_MATRIX_SHAPE, com.google.zxing.datamatrix.encoder.SymbolShapeHint.FORCE_SQUARE);
        hints.put(EncodeHintType.ERROR_CORRECTION,  ErrorCorrectionLevel.L);

        // Criar um escritor (writer) de DataMatrix e codificar o conteúdo em um BitMatrix
        DataMatrixWriter writer = new DataMatrixWriter();
        BitMatrix bitMatrix = writer.encode(dataMatrixContent, BarcodeFormat.DATA_MATRIX, 200, 200, hints);

        // Converter o BitMatrix em uma imagem PNG e gravar em um ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream, new MatrixToImageConfig(0xFF000000,0xFFFFFFFF)); // Cores de fundo e primeiro plano

        // Retornar os bytes da imagem gerada
        return outputStream.toByteArray();
    }

    /**
     * Método para gerar o ID do AR Digital com base no código fornecido.
     *
     * @param arDigitalCode O código do AR Digital
     * @return O ID do AR Digital gerado
     */
    private String generateArDigitalId(String arDigitalCode) {
        // Substituir 'BR' por 'AA' no código do AR Digital
        return arDigitalCode.substring(0, arDigitalCode.length() - 2) + "AA";
    }
}
