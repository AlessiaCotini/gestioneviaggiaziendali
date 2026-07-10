package alessia.cotini.gestioneviaggiaziendali.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ViaggioDTO(

                         @NotBlank(message = "Destinazione necessaria")
                         String destinazione,

                         @NotNull(message = "La data di partenza è obbligatoria")
                         LocalDate dataPartenza) {
}
