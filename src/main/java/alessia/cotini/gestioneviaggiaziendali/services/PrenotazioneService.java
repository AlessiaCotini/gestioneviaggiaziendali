package alessia.cotini.gestioneviaggiaziendali.services;

import alessia.cotini.gestioneviaggiaziendali.entities.Dipendente;
import alessia.cotini.gestioneviaggiaziendali.entities.Prenotazione;
import alessia.cotini.gestioneviaggiaziendali.entities.Viaggio;
import alessia.cotini.gestioneviaggiaziendali.exceptions.BadRequest;
import alessia.cotini.gestioneviaggiaziendali.exceptions.NotFound;
import alessia.cotini.gestioneviaggiaziendali.records.PrenotazioneDTO;
import alessia.cotini.gestioneviaggiaziendali.repositories.DipendeteRepository;
import alessia.cotini.gestioneviaggiaziendali.repositories.PrenotazioneRepository;
import alessia.cotini.gestioneviaggiaziendali.repositories.ViaggioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final DipendeteRepository dipendeteRepository;
    private final ViaggioRepository viaggioRepository;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, DipendeteRepository dipendenteRepository, ViaggioRepository viaggioRepository, DipendeteRepository dipendeteRepository, ViaggioRepository viaggioRepository1) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.dipendeteRepository = dipendeteRepository;
        this.viaggioRepository = viaggioRepository1;
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
        Dipendente dipendente = dipendeteRepository.findById(payloads.dipendenteId()).orElseThrow() ;
        Viaggio viaggio = viaggioRepository.findById(payloads.viaggioId()).orElseThrow();
        boolean dipendenteImpegnato = prenotazioneRepository.existsByDipendenteAndViaggioDataPartenza(
                dipendente, viaggio.getDataPartenza()
        );
        if(dipendenteImpegnato)throw new BadRequest("Il dipendente è già in viaggio per la data richiesta");
        Prenotazione nuova = new Prenotazione(payloads.preferenze(), dipendente,viaggio);
        return this.prenotazioneRepository.save(nuova);
    }

    public Prenotazione modificaPrenotazione (UUID prenotazioneId, PrenotazioneDTO payloads){
        Dipendente dipendente = dipendeteRepository.findById(payloads.dipendenteId()).orElseThrow() ;
        Viaggio viaggio = viaggioRepository.findById(payloads.viaggioId()).orElseThrow();
        Prenotazione trovata = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(()-> new NotFound("Prenotazione con id "+ prenotazioneId+ " non è stata trovata."));
        trovata.setPreferenze(payloads.preferenze());
        trovata.setDipendente(dipendente);
        trovata.setViaggio(viaggio);
        return trovata;
    }

    public void eliminaPrenotazione (UUID prenotazioneId){
        Prenotazione trovata = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(()-> new NotFound("Prenotazione con id "+ prenotazioneId+ " non è stata trovata."));
        prenotazioneRepository.delete(trovata);
    }

}
