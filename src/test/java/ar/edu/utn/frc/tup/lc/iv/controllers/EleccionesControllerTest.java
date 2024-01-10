package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.records.Distrito;
import ar.edu.utn.frc.tup.lc.iv.services.EleccionesService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EleccionesControllerTest {

    @MockBean
    private EleccionesService eleccionesService;

    @SpyBean
    private EleccionesController eleccionesController;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getDistritoByNombre() {
        List<Distrito> distritoList = new ArrayList<>();
        when(eleccionesService.getDistritoByNombre("nombre")).thenReturn(ResponseEntity.ok(distritoList));
        assertNotNull(eleccionesController.getDistritoByNombre("nombre").getBody());
    }


    @Test
    void getCargoByDistrito() {
        when(eleccionesService.getCargoPorDistrito(1L)).thenReturn(null);
        assertNull(eleccionesController.getCargoByDistrito(1L).getBody());
    }

    @Test
    void testGetCargoByDistrito() {
        when(eleccionesService.getSeccion(1L, 1L)).thenReturn(null);
        assertNull(eleccionesController.getSeccionByDistrito(1L, 1L).getBody());
    }

    @Test
    void getResultado() {
        when(eleccionesService.getResultado(1L, 1L)).thenReturn(null);
        assertNull(eleccionesController.getResultado(1L, 1L).getBody());
    }
}