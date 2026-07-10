package alessia.cotini.gestioneviaggiaziendali.services;

import alessia.cotini.gestioneviaggiaziendali.entities.Dipendente;
import alessia.cotini.gestioneviaggiaziendali.entities.Prenotazione;
import alessia.cotini.gestioneviaggiaziendali.exceptions.NotFound;
import alessia.cotini.gestioneviaggiaziendali.records.DipendenteDTO;
import alessia.cotini.gestioneviaggiaziendali.records.PrenotazioneDTO;
import alessia.cotini.gestioneviaggiaziendali.repositories.DipendeteRepository;
import alessia.cotini.gestioneviaggiaziendali.repositories.PrenotazioneRepository;
import alessia.cotini.gestioneviaggiaziendali.repositories.ViaggioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, DipendeteRepository dipendenteRepository, ViaggioRepository viaggioRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
    }

    public Page<Prenotazione> findAll(int page){
        Pageable pageable = PageRequest.of(page,10);
        return this.prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione findById (UUID prenotazioneId) {
        return prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(()-> new NotFound("Prenotazione con id "+ prenotazioneId+ " non è stata trovata."));
    }

    public Prenotazione creaNuovaPrenotazione (PrenotazioneDTO payloads){
        Prenotazione nuova = new Prenotazione(payloads.preferenze(), payloads.dipendente(), payloads.viaggio());
        this.prenotazioneRepository.save(nuova);
        return nuova;
    }

    public Prenotazione modificaPrenotazione (@PathVariable UUID prenotazioneId, PrenotazioneDTO payloads){
        Prenotazione trovata = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(()-> new NotFound("Prenotazione con id "+ prenotazioneId+ " non è stata trovata."));
        trovata.setPreferenze(payloads.preferenze());
        trovata.setDipendente(payloads.dipendente());
        trovata.setViaggio(payloads.viaggio());
        return trovata;
    }

    public void eliminaPrenotazione (@PathVariable UUID prenotazioneId){
        Prenotazione trovata = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(()-> new NotFound("Prenotazione con id "+ prenotazioneId+ " non è stata trovata."));
        prenotazioneRepository.delete(trovata);
    }

}
