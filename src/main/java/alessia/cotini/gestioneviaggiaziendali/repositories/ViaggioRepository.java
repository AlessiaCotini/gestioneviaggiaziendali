package alessia.cotini.gestioneviaggiaziendali.repositories;

import alessia.cotini.gestioneviaggiaziendali.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, UUID> {
}
