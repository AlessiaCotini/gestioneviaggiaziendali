package alessia.cotini.gestioneviaggiaziendali.controllers;
import alessia.cotini.gestioneviaggiaziendali.entities.Viaggio;
import alessia.cotini.gestioneviaggiaziendali.enums.StatoViaggio;
import alessia.cotini.gestioneviaggiaziendali.exceptions.BadRequest;
import alessia.cotini.gestioneviaggiaziendali.records.ViaggioDTO;
import alessia.cotini.gestioneviaggiaziendali.services.ViaggioService;
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
@RequestMapping("/viaggi")
public class ViaggioController {

    private final ViaggioService viaggioService;

    public ViaggioController(ViaggioService viaggioService) {
        this.viaggioService = viaggioService;
    }


    //GET - http://localhost:3001/viaggi - CERCO TUTTI I VIAGGI
    @GetMapping
    public Page<Viaggio> getAll(@RequestParam(value = "page", defaultValue = "0") int page){
        return this.viaggioService.findAll(page);
    }

    //GET - http://localhost:3001/viaggi/{viaggioId} - NE CERCO UNO PER ID
    @GetMapping("/{authorId}")
    public Viaggio findById(@PathVariable UUID viaggioId){
        return this.viaggioService.findById(viaggioId);
    }
    //POST - http://localhost:3001/viaggi/ + payload - AGGIUNGO UN VIAGGIO
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio creoViaggio(@RequestBody @Valid ViaggioDTO payloads, BindingResult validation){
        if (validation.hasErrors())throw new BadRequest("Errore nella creazione di un nuovo viaggio");
        return this.viaggioService.creaNuovoViaggio(payloads);
    }
    //PUT - http://localhost:3001/viaggi/{viaggioId} + PAYLOAD - MODIFICO UN VIAGGIO
    @PutMapping("/{viaggioId}")
    public ResponseEntity<Viaggio> modificaViaggio(@PathVariable UUID viaggioId,
                                                         @RequestBody @Valid ViaggioDTO payloads,
                                                         BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errorsList = validation.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new BadRequest("Controlla i campi inseriti : " + errorsList);
        }
        Viaggio modificato = this.viaggioService.modificaViaggio(viaggioId, payloads);
        return ResponseEntity.ok(modificato);
    }
    // PATCH - http://localhost:3001/viaggi/{viaggioId}/stato - MODIFICO LO STATO DI UN VIAGGIO
    @PatchMapping("/{viaggioId}/stato")
    public Viaggio cambiaStato(@PathVariable UUID viaggioId, @RequestParam StatoViaggio stato) {
        return this.viaggioService.cambiaStatoViaggio(viaggioId, stato);
    }
    //DELETE - http://localhost:3001/viaggi/{viaggioId} - CANCELLO UN VIAGGIO
    @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelloViaggio(@PathVariable UUID viaggioId){
        this.viaggioService.eliminaViaggio(viaggioId);
    }
}
