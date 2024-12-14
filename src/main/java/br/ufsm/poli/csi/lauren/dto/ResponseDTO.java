package br.ufsm.poli.csi.lauren.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {
    private Status status;
    private String message;
    private T data;

    private enum Status {
        SUCCESS, ERROR, WARNING, CONFLICT
    }

    public static <T> ResponseDTO<T> ok(T data) {
        return new ResponseDTO<T>(Status.SUCCESS, "Sucesso!", data);
    }

    public static <T> ResponseDTO<T> notFound() {
        return new ResponseDTO<T>(Status.ERROR, "Não encontrado!", null);
    }

    public static <T> ResponseDTO<T> noContent() {
        return new ResponseDTO<T>(Status.SUCCESS, "Sucesso!", null);
    }

    public static <T> ResponseDTO<T> badRequest(T data) {
        return new ResponseDTO<T>(Status.ERROR, "Ocorreu um erro ao processar sua requisição!", data);
    }
}
