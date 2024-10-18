package entrevista.tecnica.practicameli.model.dto;

import java.io.Serializable;

public class ResponseDTO implements Serializable {

    private String message;

    // Constructor
    public ResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseDTO [message=" + message + "]";
    }
}
