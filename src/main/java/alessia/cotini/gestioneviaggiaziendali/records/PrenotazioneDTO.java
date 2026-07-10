package alessia.cotini.gestioneviaggiaziendali.records;
import jakarta.validation.constraints.NotNull;


import java.util.UUID;

public record PrenotazioneDTO(

                              String preferenze,

                              @NotNull(message = "Associare un dipendente al viaggio")
                              UUID dipendenteId,

                              @NotNull(message = "Associare un viaggio alla prenotazione")
                              UUID viaggioId) {
}
