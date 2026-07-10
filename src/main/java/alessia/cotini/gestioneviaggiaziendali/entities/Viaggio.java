package alessia.cotini.gestioneviaggiaziendali.entities;

import alessia.cotini.gestioneviaggiaziendali.enums.StatoViaggio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "viaggi")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Viaggio {
    @Id
    @GeneratedValue
    private UUID viaggioId;

    @Column(nullable = false)
    private String destinazione;

    @Column(nullable = false)
    private LocalDate dataPartenza;

    @Column(nullable = false)
    private StatoViaggio statoViaggio;

    public Viaggio(String destinazione, LocalDate dataPartenza) {
        this.destinazione = destinazione;
        this.dataPartenza = dataPartenza;
        this.statoViaggio = StatoViaggio.PROGRAMMATO;
    }
}
