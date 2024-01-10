package ar.edu.utn.frc.tup.lc.iv.restClient;

import ar.edu.utn.frc.tup.lc.iv.records.Cargo;
import ar.edu.utn.frc.tup.lc.iv.records.Distrito;
import ar.edu.utn.frc.tup.lc.iv.records.Resultado;
import ar.edu.utn.frc.tup.lc.iv.records.Seccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestClient {

    @Autowired
    RestTemplate restTemplate;

    private static final String URL = "http://server:8080";

    public ResponseEntity<List<Distrito>> getAllDistritos(){
        return restTemplate.exchange(URL + "/distritos", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Distrito>>() {});
    }

    public ResponseEntity<List<Distrito>> getDistritoByNombre(String nombre){
        return restTemplate.exchange(URL + "/distritos?distritoNombre=" + nombre, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Distrito>>() {});
    }

    public ResponseEntity<List<Distrito>> getDistritoById(Long id){
        return restTemplate.exchange(URL + "/distritos?distritoId=" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Distrito>>() {});
    }

    public ResponseEntity<List<Cargo>> getCargosByDistrito(Long distritoId){
        return restTemplate.exchange(URL + "/cargos?distritoId=" + distritoId, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Cargo>>() {});
    }

    public ResponseEntity<List<Seccion>> getSeccionByDistrito(Long distritoId){
        return restTemplate.exchange(URL + "/secciones?distritoId=" + distritoId, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Seccion>>() {});
    }

    public ResponseEntity<List<Seccion>> getSeccionByDistritoBySeccion(Long distritoId, Long seccionId){
        return restTemplate.exchange(URL + "/secciones?seccionId=" + seccionId + "&distritoId=" + distritoId, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Seccion>>() {});
    }

    public ResponseEntity<List<Resultado>> getResultadosBySeccion(Long seccionId) {
        return restTemplate.exchange(URL + "/resultados?seccionId=" + seccionId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Resultado>>() {});
    }
}
