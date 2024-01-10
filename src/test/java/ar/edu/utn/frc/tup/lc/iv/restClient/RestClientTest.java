package ar.edu.utn.frc.tup.lc.iv.restClient;

import ar.edu.utn.frc.tup.lc.iv.records.Cargo;
import ar.edu.utn.frc.tup.lc.iv.records.Distrito;
import ar.edu.utn.frc.tup.lc.iv.records.Resultado;
import ar.edu.utn.frc.tup.lc.iv.records.Seccion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RestClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestClient restClient;

    private static final String URL = "http://server:8080";
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllDistritos() {
        when(restTemplate.exchange(URL + "/distritos", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Distrito>>() {})).thenReturn(ResponseEntity.ok(null));
        assertEquals(ResponseEntity.ok(null), restClient.getAllDistritos());
    }

    @Test
    void getDistritoByNombre() {
        when(restTemplate.exchange(URL + "/distritos?distritoNombre=" + "nombre", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Distrito>>() {})).thenReturn(ResponseEntity.ok(null));
        assertEquals(ResponseEntity.ok(null), restClient.getDistritoByNombre("nombre"));
    }

    @Test
    void getDistritoById() {
        when(restTemplate.exchange(URL + "/distritos?distritoId=" + 1L, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Distrito>>() {})).thenReturn(ResponseEntity.ok(null));
        assertEquals(ResponseEntity.ok(null), restClient.getDistritoById(1L));
    }

    @Test
    void getCargosByDistrito() {
        when(restTemplate.exchange(URL + "/cargos?distritoId=" + 1L, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Cargo>>() {})).thenReturn(ResponseEntity.ok(null));
        assertEquals(ResponseEntity.ok(null), restClient.getCargosByDistrito(1L));
    }

    @Test
    void getSeccionByDistrito() {
        when(restTemplate.exchange(URL + "/secciones?distritoId=" + 1L, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Seccion>>() {})).thenReturn(ResponseEntity.ok(null));
        assertEquals(ResponseEntity.ok(null), restClient.getSeccionByDistrito(1L));
    }

    @Test
    void getSeccionByDistritoBySeccion() {
        when(restTemplate.exchange(URL + "/secciones?seccionId=" + 1L + "&distritoId=" + 1L, HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Seccion>>() {})).thenReturn(ResponseEntity.ok(null));
        assertEquals(ResponseEntity.ok(null), restClient.getSeccionByDistritoBySeccion(1L, 1L));
    }

    @Test
    void getResultadosBySeccion() {
        when(restTemplate.exchange(URL + "/resultados?seccionId=" + 1L,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Resultado>>() {})).thenReturn(ResponseEntity.ok(null));
        assertEquals(ResponseEntity.ok(null), restClient.getResultadosBySeccion( 1L));
    }
}