package alessia.cotini.gestioneviaggiaziendali.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class Errore {
    private String message;
    private LocalDateTime localDateTime;

    public Errore(String message,LocalDateTime localDateTime) {
        this.message = message;
        this.localDateTime = localDateTime;
    }
}
