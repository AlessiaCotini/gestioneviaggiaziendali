package alessia.cotini.gestioneviaggiaziendali.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "dipendenti")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Dipendente {

    @Id
    @GeneratedValue
    private UUID dipendenteId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String email;

    private String profiloImg;

    public Dipendente(String username, String name, String surname, String email) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.profiloImg = "https://picsum.photos/200";
        //pensavo un immagine di default nel caso il dipendente non la avesse ancora caricata
    }
}
