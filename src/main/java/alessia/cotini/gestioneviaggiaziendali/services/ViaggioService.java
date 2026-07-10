package alessia.cotini.gestioneviaggiaziendali.services;

import alessia.cotini.gestioneviaggiaziendali.entities.Viaggio;
import alessia.cotini.gestioneviaggiaziendali.exceptions.NotFound;
import alessia.cotini.gestioneviaggiaziendali.records.ViaggioDTO;
import alessia.cotini.gestioneviaggiaziendali.repositories.ViaggioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Service
public class ViaggioService {

    private final ViaggioRepository viaggioRepository;

    public ViaggioService(ViaggioRepository viaggioRepository) {
        this.viaggioRepository = viaggioRepository;
    }
    public Page<Viaggio> findAll(int page){
        Pageable pageable = PageRequest.of(page,10);
        return this.viaggioRepository.findAll(pageable);
    }

    public Viaggio findById (UUID viaggioId) {
        return viaggioRepository.findById(viaggioId)
                .orElseThrow(()-> new NotFound("Viaggio con id "+ viaggioId+ " non è stato trovato."));
    }

    public Viaggio creaNuovoViaggio (ViaggioDTO payloads){
        Viaggio nuovo = new Viaggio(payloads.destinazione(), payloads.dataPartenza());
        this.viaggioRepository.save(nuovo);
        return nuovo;
    }

    public Viaggio modificaViaggio (@PathVariable UUID viaggioId, ViaggioDTO payloads){
        Viaggio trovato = viaggioRepository.findById(viaggioId)
                .orElseThrow(()-> new NotFound("Viaggio con id "+ viaggioId+ " non è stato trovato."));
        trovato.setDestinazione(payloads.destinazione());
        trovato.setDataPartenza(payloads.dataPartenza());
        return trovato;
    }

    public void eliminaViaggio (@PathVariable UUID viaggioId){
        Viaggio trovato = viaggioRepository.findById(viaggioId)
                .orElseThrow(()-> new NotFound("Viaggio con id "+ viaggioId+ " non è stato trovato."));
        viaggioRepository.delete(trovato);
    }
}
