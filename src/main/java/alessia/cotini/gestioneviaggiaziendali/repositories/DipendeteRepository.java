package alessia.cotini.gestioneviaggiaziendali.repositories;

import alessia.cotini.gestioneviaggiaziendali.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DipendeteRepository extends JpaRepository<Dipendente, UUID> {
    boolean existsByEmail(String email);
}
