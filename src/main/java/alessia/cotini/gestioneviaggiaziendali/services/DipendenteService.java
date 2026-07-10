package alessia.cotini.gestioneviaggiaziendali.services;

import alessia.cotini.gestioneviaggiaziendali.entities.Dipendente;
import alessia.cotini.gestioneviaggiaziendali.exceptions.BadRequest;
import alessia.cotini.gestioneviaggiaziendali.exceptions.NotFound;
import alessia.cotini.gestioneviaggiaziendali.records.DipendenteDTO;
import alessia.cotini.gestioneviaggiaziendali.repositories.DipendeteRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@Service
public class DipendenteService {
    private final DipendeteRepository dipendeteRepository;
    private final Cloudinary cloudinary;

    public DipendenteService(DipendeteRepository dipendeteRepository, Cloudinary cloudinary) {
        this.dipendeteRepository = dipendeteRepository;
        this.cloudinary = cloudinary;
    }

    public Page<Dipendente> findAll(int page){
        Pageable pageable = PageRequest.of(page,10);
        return this.dipendeteRepository.findAll(pageable);
    }

    public Dipendente findById (UUID dipendenteId) {
        return dipendeteRepository.findById(dipendenteId).orElseThrow(()-> new NotFound("Dipendente con id "+ dipendenteId+ " non è stato trovato."));
    }

    public Dipendente creaNuovoDipendente (DipendenteDTO payloads){
        if(dipendeteRepository.existsByEmail(payloads.email())){
            throw new BadRequest("La mail selezionata è gia in uso");
        }
        Dipendente nuovo = new Dipendente(payloads.username(), payloads.name(), payloads.surname(), payloads.email());
        this.dipendeteRepository.save(nuovo);
        return nuovo;
    }

    public Dipendente modificaDipendente (UUID dipendenteId, DipendenteDTO payloads){
        Dipendente trovato = dipendeteRepository.findById(dipendenteId)
                .orElseThrow(()-> new NotFound("Dipendente con id "+ dipendenteId+ " non è stato trovato."));
        trovato.setUsername(payloads.username());
        trovato.setName(payloads.name());
        trovato.setSurname(payloads.surname());
        trovato.setEmail(payloads.email());
        return trovato;
    }

    public void eliminaDipendente ( UUID dipendenteId){
        Dipendente trovato = dipendeteRepository.findById(dipendenteId)
                .orElseThrow(()-> new NotFound("Dipendente con id "+ dipendenteId + " non è stato trovato."));
        dipendeteRepository.delete(trovato);
    }


    public String cambiaImgProfilo (UUID dipendenteId, MultipartFile file) throws Exception {
        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String url = (String) result.get("url");
        Dipendente dipendente = dipendeteRepository.findById(dipendenteId)
                .orElseThrow(() ->  new NotFound("Dipendente con id "+ dipendenteId + " non è stato trovato."));
        dipendente.setProfiloImg(url);
        dipendeteRepository.save(dipendente);
        return url;
    }
}
