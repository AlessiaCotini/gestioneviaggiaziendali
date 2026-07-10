package alessia.cotini.gestioneviaggiaziendali.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Prenotazione {

    @Id
    @GeneratedValue
    private UUID prenotazioneId;

    LocalDate dataRichiesta;
    String preferenze;

    @ManyToOne
    @JoinColumn(name = "dipendenteId",nullable = false)
    Dipendente dipendente;

    @ManyToOne
    @JoinColumn(name = "viaggioId",nullable = false)
    Viaggio viaggio;

    public Prenotazione(String preferenze, Dipendente dipendente, Viaggio viaggio) {
        this.dataRichiesta = LocalDate.now();
        this.preferenze = preferenze;
        this.dipendente = dipendente;
        this.viaggio = viaggio;
    }
}
