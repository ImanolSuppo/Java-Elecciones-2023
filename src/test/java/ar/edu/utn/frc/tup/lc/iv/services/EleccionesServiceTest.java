package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.records.Cargo;
import ar.edu.utn.frc.tup.lc.iv.records.Distrito;
import ar.edu.utn.frc.tup.lc.iv.records.Resultado;
import ar.edu.utn.frc.tup.lc.iv.records.Seccion;
import ar.edu.utn.frc.tup.lc.iv.restClient.RestClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EleccionesServiceTest {

    @MockBean
    private RestClient restClient;

    @SpyBean
    private EleccionesService eleccionesService;

    private static final List<Resultado> resultadoList = new ArrayList<>();
    private static final List<Seccion> seccionList = new ArrayList<>();
    private static final List<Distrito> distritoList = new ArrayList<>();
    private static final List<Cargo> cargoList = new ArrayList<>();

    private static final Resultado RESULTADO = new Resultado(
            1L,
            4L,
            "Córdoba",
            26L,
            "Unión",
            135L,
            "LA LIBERTAD AVANZA",
            "POSITIVO",
            84L
    );
    private static final Resultado RESULTADO1 = new Resultado(
            1L,
            4L,
            "Córdoba",
            26L,
            "Unión",
            135L,
            "JUNTOS POR EL CAMBIO",
            "POSITIVO",
            84L
    );
    private static final Resultado RESULTADO2 = new Resultado(
            1L,
            4L,
            "Córdoba",
            26L,
            "Unión",
            135L,
            "HACEMOS POR NUESTRO PAIS",
            "POSITIVO",
            84L
    );
    private static final Resultado RESULTADO3 = new Resultado(
            1L,
            4L,
            "Córdoba",
            26L,
            "Unión",
            135L,
            "UNION POR LA PATRIA",
            "POSITIVO",
            84L
    );
    private static final Resultado RESULTADO4 = new Resultado(
            1L,
            4L,
            "Córdoba",
            26L,
            "Unión",
            135L,
            "FRENTE DE IZQUIERDA Y DE TRABAJADORES - UNIDAD",
            "POSITIVO",
            84L
    );
    private static final Resultado RESULTADO5 = new Resultado(
            1L,
            4L,
            "Córdoba",
            26L,
            "Unión",
            135L,
            "",
            "EN BLANCO",
            84L
    );
    private static final Resultado RESULTADO6 = new Resultado(
            1L,
            4L,
            "Córdoba",
            26L,
            "Unión",
            135L,
            "",
            "NULO",
            84L
    );
    private static final Resultado RESULTADO7 = new Resultado(
            1L,
            4L,
            "Córdoba",
            26L,
            "Unión",
            135L,
            "",
            "IMPUGNADO",
            84L
    );
    private static final Resultado RESULTADO8 = new Resultado(
            1L,
            4L,
            "Córdoba",
            26L,
            "Unión",
            135L,
            "",
            "RECURRIDO",
            84L
    );

    private static final Seccion SECCION = new Seccion(1L, "Cordoba", 4L);
    private static final Distrito DISTRITO = new Distrito(4L, "Cordoba");
    private static final Cargo CARGO = new Cargo(1L, "Cargo", 4L);

    @BeforeEach
    void setUp() {
        resultadoList.add(RESULTADO);
        resultadoList.add(RESULTADO1);
        resultadoList.add(RESULTADO2);
        resultadoList.add(RESULTADO3);
        resultadoList.add(RESULTADO4);
        resultadoList.add(RESULTADO5);
        resultadoList.add(RESULTADO6);
        resultadoList.add(RESULTADO7);
        resultadoList.add(RESULTADO8);
        seccionList.add(SECCION);
        distritoList.add(DISTRITO);
        cargoList.add(CARGO);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllAgrupaciones() {
    }

    @Test
    void getAllOtrosVotos() {
    }

    @Test
    void getAllDistritos() {
    }

    @Test
    void getDistritoByNombre() {
    }

    @Test
    void getCargoPorDistrito() {
        when(restClient.getDistritoById(4L)).thenReturn(ResponseEntity.ok(distritoList));
        when(restClient.getCargosByDistrito(4L)).thenReturn(ResponseEntity.ok(cargoList));
        assertNotNull(eleccionesService.getCargoPorDistrito(4L));
    }

    @Test
    void setDistritoResponse() {
    }

    @Test
    void setCargosResponse() {
    }

    @Test
    void getSeccion() {
        when(restClient.getSeccionByDistritoBySeccion(4L, 1L)).thenReturn(ResponseEntity.ok(seccionList));
        assertNotNull(eleccionesService.getSeccion(4L, 1L));
    }

    @Test
    void getSeccionNull() {
        when(restClient.getSeccionByDistrito(4L)).thenReturn(ResponseEntity.ok(seccionList));
        assertNotNull(eleccionesService.getSeccion(4L, null));
    }

    @Test
    void getSeccionByDistrito() {
    }

    @Test
    void getSeccionByDistritoBySeccion() {
    }

    @Test
    void getResultadoByDistritoBySeccion() {
    }

    @Test
    void getResultado() {
        when(restClient.getResultadosBySeccion(26L)).thenReturn(ResponseEntity.ok(resultadoList));
        assertNotNull(eleccionesService.getResultado(4L, 26L));
    }
}