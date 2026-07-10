package alessia.cotini.gestioneviaggiaziendali.records;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ViaggioDTO(

                         @NotBlank(message = "Destinazione necessaria")
                         String destinazione,

                         @NotBlank(message = "Data prevista per la partenza necessaria")
                         LocalDate dataPartenza) {
}
