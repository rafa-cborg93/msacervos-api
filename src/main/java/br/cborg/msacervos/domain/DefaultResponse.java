package br.cborg.msacervos.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class is used tho return a default response to the client.
 *
 */
@Data
@AllArgsConstructor
public class DefaultResponse {
    private Integer code;
    private String message;
    private Object data;

    public DefaultResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
