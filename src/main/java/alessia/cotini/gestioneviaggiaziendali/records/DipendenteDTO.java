package alessia.cotini.gestioneviaggiaziendali.records;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(
                            @NotBlank(message = "Username necessario")
                            @Size(min = 2, max = 10, message = "Specificare un username tra due e dieci caratteri")
                            String username,

                            @NotBlank(message = "Il nome è necessario")
                            @Size(min = 2, max = 15, message = "Specificare un nome tra due e quindici caratteri")
                            String name,

                            @NotBlank(message = "Il cognome è necessario")
                            @Size(min = 1, max = 10, message = "Specificare un cognome tra uno e dieci caratteri")
                            String surname,

                            @NotBlank(message = "L'email è necessaria")
                            @Email(message = "L'email inserita non è valida")
                            String email)

                             {
}
