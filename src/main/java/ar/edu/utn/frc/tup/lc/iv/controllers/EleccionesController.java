package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.CargosPorDistritoDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.ResultadoPorDistritoYSeccionDTO;
import ar.edu.utn.frc.tup.lc.iv.records.Distrito;
import ar.edu.utn.frc.tup.lc.iv.records.Seccion;
import ar.edu.utn.frc.tup.lc.iv.services.EleccionesService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class EleccionesController {

    @Autowired
    private EleccionesService eleccionesService;


    @GetMapping("/distritos")
    public ResponseEntity<List<Distrito>> getDistritoByNombre( @RequestParam(required = false) String distrito_nombre){
        if(distrito_nombre == null){
            return eleccionesService.getAllDistritos();
        }
        return eleccionesService.getDistritoByNombre(distrito_nombre);
    }

    @GetMapping("/cargos")
    public ResponseEntity<CargosPorDistritoDTO> getCargoByDistrito(@RequestParam Long distrito_id){
        return ResponseEntity.ok(eleccionesService.getCargoPorDistrito(distrito_id));
    }

    @GetMapping("/secciones")
    public ResponseEntity<List<Seccion>> getSeccionByDistrito(@RequestParam Long distrito_id,
                                                            @RequestParam(required = false) Long seccion_id ){
        return ResponseEntity.ok(eleccionesService.getSeccion(distrito_id, seccion_id));
    }

    @GetMapping("/resultados")
    public ResponseEntity<ResultadoPorDistritoYSeccionDTO> getResultado(@RequestParam Long distrito_id,
                                                                              @RequestParam Long seccion_id ){
        return ResponseEntity.ok(eleccionesService.getResultado(distrito_id, seccion_id));
    }
}
