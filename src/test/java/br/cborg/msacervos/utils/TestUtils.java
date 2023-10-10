package br.cborg.msacervos.utils;

import br.cborg.msacervos.domain.acervo.request.AcervoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestUtils {
    public static String getAcervoRequestJson() {
        String request = getRequest();
        return request;
    }

    public static AcervoRequest getAcervoRequest() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String request = getRequest();
            return mapper.readValue(request, AcervoRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getRequest() {
        try {
            Path jsonFilePath = ResourceUtils.getFile("classpath:acervoRequest.json").toPath();
            String request = new String(Files.readAllBytes(jsonFilePath));
            return request;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
