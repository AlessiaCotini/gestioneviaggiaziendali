package alessia.cotini.gestioneviaggiaziendali.records;

import alessia.cotini.gestioneviaggiaziendali.entities.Dipendente;
import alessia.cotini.gestioneviaggiaziendali.entities.Viaggio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PrenotazioneDTO(
                              @NotBlank(message = "Inserire delle preferenze o note specifiche")
                              @Size(min = 2, max = 150, message = "Inserire un massimo di 150 caratteri")
                              String preferenze,

                              @NotBlank(message = "Associare un dipendente al viaggio")
                              Dipendente dipendente,

                              @NotBlank(message = "Associare un viaggio alla prenotazione")
                              Viaggio viaggio) {
}
