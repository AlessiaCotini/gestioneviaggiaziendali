package alessia.cotini.gestioneviaggiaziendali.controllers;

import alessia.cotini.gestioneviaggiaziendali.entities.Dipendente;
import alessia.cotini.gestioneviaggiaziendali.entities.Prenotazione;
import alessia.cotini.gestioneviaggiaziendali.entities.Viaggio;
import alessia.cotini.gestioneviaggiaziendali.exceptions.BadRequest;
import alessia.cotini.gestioneviaggiaziendali.records.DipendenteDTO;
import alessia.cotini.gestioneviaggiaziendali.records.PrenotazioneDTO;
import alessia.cotini.gestioneviaggiaziendali.records.ViaggioDTO;
import alessia.cotini.gestioneviaggiaziendali.services.PrenotazioneService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;

    }
    //GET - http://localhost:3001/prenotazioni - CERCO TUTTE LE PRENOTAZIONI
    @GetMapping
    public Page<Prenotazione> getAll(@RequestParam(value = "page", defaultValue = "0") int page){
        return this.prenotazioneService.findAll(page);
    }
    //GET - http://localhost:3001/prenotazioni/{prenotazioneId} - NE CERCO UNA PER ID
    @GetMapping("/{prenotazioneId}")
    public Prenotazione findById(@PathVariable UUID prenotazioneId){
        return this.prenotazioneService.findById(prenotazioneId);
    }
    //POST - http://localhost:3001/prenotazioni/ + payload - AGGIUNGO UNA PRENOTAZIONE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione creoPrenotazione(@RequestBody @Valid PrenotazioneDTO payloads, BindingResult validation){
        if (validation.hasErrors())throw new BadRequest("Errore nella creazione di una nuova prenotazione");
        return this.prenotazioneService.creaNuovaPrenotazione(payloads);
    }
    //PUT - http://localhost:3001/prenotazioni/{prenotazioneId} + PAYLOAD - MODIFICO UNA PRENOTAZIONE
    @PutMapping("/{prenotazioneId}")
    public ResponseEntity<Prenotazione> modificaPrenotazione(@PathVariable UUID prenotazioneId,
                                                         @RequestBody @Valid PrenotazioneDTO payloads,
                                                         BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errorsList = validation.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new BadRequest("Controlla i campi inseriti : " + errorsList);
        }
        Prenotazione modificata = this.prenotazioneService.modificaPrenotazione(prenotazioneId, payloads);
        return ResponseEntity.ok(modificata);
    }
    //DELETE - http://localhost:3001/prenotazioni/{prenotazioneId} - CANCELLO UNA PRENOTAZIONE
    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelloViaggio(@PathVariable UUID prenotazioneId){
        this.prenotazioneService.eliminaPrenotazione(prenotazioneId);
    }
}
