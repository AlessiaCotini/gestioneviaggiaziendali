package alessia.cotini.gestioneviaggiaziendali.controllers;

import alessia.cotini.gestioneviaggiaziendali.entities.Dipendente;
import alessia.cotini.gestioneviaggiaziendali.entities.Prenotazione;
import alessia.cotini.gestioneviaggiaziendali.entities.Viaggio;
import alessia.cotini.gestioneviaggiaziendali.exceptions.BadRequest;
import alessia.cotini.gestioneviaggiaziendali.exceptions.NotFound;
import alessia.cotini.gestioneviaggiaziendali.records.DipendenteDTO;
import alessia.cotini.gestioneviaggiaziendali.records.PrenotazioneDTO;
import alessia.cotini.gestioneviaggiaziendali.services.DipendenteService;
import alessia.cotini.gestioneviaggiaziendali.services.PrenotazioneService;
import alessia.cotini.gestioneviaggiaziendali.services.ViaggioService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    private final DipendenteService dipendenteService;
    private final PrenotazioneService prenotazioneService;
    private final ViaggioService viaggioService;

    public DipendenteController(DipendenteService dipendenteService, PrenotazioneService prenotazioneService, ViaggioService viaggioService) {
        this.dipendenteService = dipendenteService;
        this.prenotazioneService = prenotazioneService;
        this.viaggioService = viaggioService;
    }

    //GET - - CERCO TUTTI I DIPENDENTI
    @GetMapping
    public Page<Dipendente> getAll(@RequestParam(value = "page", defaultValue = "0") int page){
        return this.dipendenteService.findAll(page);
    }
    //GET - http://localhost:3001/dipendenti/{dipendenteId} - NE CERCO UNO PER ID
    @GetMapping("/{dipendenteId}")
    public Dipendente findById(@PathVariable UUID dipendenteId){
        return this.dipendenteService.findById(dipendenteId);
    }
    //POST - http://localhost:3001/prenotazioni/ + payload - AGGIUNGO UNA DIPENDENTE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente aggiungoDipendente(@RequestBody @Valid DipendenteDTO payloads, BindingResult validation){
        if (validation.hasErrors())throw new BadRequest("Errore nell'aggiunta di un nuovo dipendente");
        return this.dipendenteService.creaNuovoDipendente(payloads);
    }
    //PUT - http://localhost:3001/dipendenti/{dipendenteId} + PAYLOAD - MODIFICO UN DIPENDENTE
    @PutMapping("/{dipendenteId}")
    public ResponseEntity<Dipendente> modificaDipendente(@PathVariable UUID dipendenteId,
                                                   @RequestBody @Valid DipendenteDTO payloads,
                                                   BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errorsList = validation.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new BadRequest("Controlla i campi inseriti : " + errorsList);
        }
        Dipendente dipendente = this.dipendenteService.modificaDipendente(dipendenteId, payloads);
        return ResponseEntity.ok(dipendente);
    }
    //DELETE - http://localhost:3001/dipendenti/{dipendenteId} - ELIMINO IL PROFILO DI UN DIPENDENTE
    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rimuovoDipendente(@PathVariable UUID dipendenteId){
        this.dipendenteService.eliminaDipendente(dipendenteId);
    }
    //PATCH -  http://localhost:3001/dipendenti/{dipendenteId}/cambiaImg - CAMBIO IMMAGINE DEL PROFILO DI UN DIPENDENTE
    @PatchMapping("/{dipendenteId}/cambiaImgProfilo")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String cambiaImgProfilo(@PathVariable UUID dipendenteId, @RequestParam("avatar") MultipartFile file) {
        try {
            return dipendenteService.cambiaImgProfilo(dipendenteId, file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore nel caricamento del file : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Errore generico: " + e.getMessage());
        }
    }
    //PATCH -  http://localhost:3001/dipendenti/{dipendenteID}/creaPrenotazione - PER ASSEGNARE UN VIAGGIO
    @PatchMapping("/{dipendenteId}/aggiungiPrenotazione")
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione aggiungiPrenotazionePerCliente(
                     @PathVariable UUID dipendenteId,
                     @RequestParam UUID viaggioId,
                     @RequestParam(defaultValue = "Nessuna preferenza") String preferenze) {
            Dipendente dipendenteTrovato = dipendenteService.findById(dipendenteId);
            Viaggio viaggioTrovato = viaggioService.findById(viaggioId);
            PrenotazioneDTO payload = new PrenotazioneDTO(preferenze, dipendenteTrovato, viaggioTrovato);
            return prenotazioneService.creaNuovaPrenotazione(payload);
    }
}