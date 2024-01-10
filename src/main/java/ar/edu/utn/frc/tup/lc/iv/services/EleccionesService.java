package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.*;
import ar.edu.utn.frc.tup.lc.iv.records.Cargo;
import ar.edu.utn.frc.tup.lc.iv.records.Distrito;
import ar.edu.utn.frc.tup.lc.iv.records.Resultado;
import ar.edu.utn.frc.tup.lc.iv.records.Seccion;
import ar.edu.utn.frc.tup.lc.iv.restClient.RestClient;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class EleccionesService {

    @Autowired
    private RestClient restClient;



    public void getAllAgrupaciones(List<String> agrupaciones){
        agrupaciones.add("LA LIBERTAD AVANZA");
        agrupaciones.add("JUNTOS POR EL CAMBIO");
        agrupaciones.add("HACEMOS POR NUESTRO PAIS");
        agrupaciones.add("UNION POR LA PATRIA");
        agrupaciones.add("FRENTE DE IZQUIERDA Y DE TRABAJADORES - UNIDAD");
    }

    public void getAllOtrosVotos(List<String> list){
        list.add("EN BLANCO");
        list.add("NULO");
        list.add("IMPUGNADO");
        list.add("RECURRIDO");
    }

    public ResponseEntity<List<Distrito>> getAllDistritos(){
        return restClient.getAllDistritos();
    }

    public ResponseEntity<List<Distrito>> getDistritoByNombre(String nombre){
        return restClient.getDistritoByNombre(nombre);
    }

    public CargosPorDistritoDTO getCargoPorDistrito(Long id){
        CargosPorDistritoDTO cargosPorDistritoDTO = new CargosPorDistritoDTO();
        cargosPorDistritoDTO.setDistrito(setDistritoResponse(id));
        cargosPorDistritoDTO.setCargos(setCargosResponse(id));
        return cargosPorDistritoDTO;
    }

    public DistritoResponseDTO setDistritoResponse(Long distritoId){
        DistritoResponseDTO distritoResponseDTO = new DistritoResponseDTO();
        ResponseEntity<List<Distrito>> distritoResponseEntity = restClient.getDistritoById(distritoId);
        if(distritoResponseEntity.getStatusCode().is2xxSuccessful() && distritoResponseEntity.getBody() != null){
            distritoResponseDTO.setId(distritoResponseEntity.getBody().get(0).distritoId());
            distritoResponseDTO.setNombre(distritoResponseEntity.getBody().get(0).distritoNombre());
            return distritoResponseDTO;
        }
        return null;
    }

    public List<CargoResponseDTO> setCargosResponse(Long distritoId){
        List<CargoResponseDTO> cargoResponseDTOList = new ArrayList<>();
        ResponseEntity<List<Cargo>> cargoResponseEntity = restClient.getCargosByDistrito(distritoId);
        if(cargoResponseEntity.getStatusCode().is2xxSuccessful() && cargoResponseEntity.getBody() != null) {
            for (Cargo cargo: cargoResponseEntity.getBody()) {
                CargoResponseDTO cargoResponseDTO = new CargoResponseDTO();
                cargoResponseDTO.setId(cargo.cargoId());
                cargoResponseDTO.setNombre(cargo.cargoNombre());
                cargoResponseDTOList.add(cargoResponseDTO);
            }
            return cargoResponseDTOList;
        }
        return null;
    }

    public List<Seccion> getSeccion(Long distritoId, @Nullable Long seccionId){
        if(seccionId == null){
            return getSeccionByDistrito(distritoId);
        } else {
            return getSeccionByDistritoBySeccion(distritoId, seccionId);
        }
    }

    public List<Seccion> getSeccionByDistrito(Long id){
        ResponseEntity<List<Seccion>> responseEntity = restClient.getSeccionByDistrito(id);
        if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null){
            return responseEntity.getBody();
        }
        return null;
    }

    public List<Seccion> getSeccionByDistritoBySeccion(Long distritoId, Long seccionId){
        ResponseEntity<List<Seccion>> responseEntity = restClient.getSeccionByDistritoBySeccion(distritoId, seccionId);
        if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null){
            return responseEntity.getBody();
        }
        return null;
    }

    public List<Resultado> getResultadoByDistritoBySeccion(Long distritoId, Long seccionId){
        ResponseEntity<List<Resultado>> resultadoBySeccion = restClient.getResultadosBySeccion(seccionId);
        if(resultadoBySeccion.getStatusCode().is2xxSuccessful() && resultadoBySeccion.getBody() != null){
            return resultadoBySeccion.getBody().stream().filter(x -> !x.votosTipo().equals("COMANDO")).toList();
        }
        return null;
    }

    public ResultadoPorDistritoYSeccionDTO getResultado(Long distritoId, Long seccionId) {
        List<String> AGRUPACIONES = new ArrayList<>();
        List<String> OTROS_VOTOS = new ArrayList<>();
        ResultadoPorDistritoYSeccionDTO data = new ResultadoPorDistritoYSeccionDTO();
        data.setResultados(new ArrayList<>());
        List<Resultado> resultadoList = getResultadoByDistritoBySeccion(distritoId, seccionId);
        if(!resultadoList.isEmpty()){
            data.setSeccion(resultadoList.get(0).seccionNombre());
            data.setDistrito(resultadoList.get(0).distritoNombre());
            getAllAgrupaciones(AGRUPACIONES);
            for (String agrupacion : AGRUPACIONES) {
                data.getResultados().add(getResultadoPorAgrupacion(resultadoList, agrupacion));
            }
            getAllOtrosVotos(OTROS_VOTOS);
            for (String otrosVoto : OTROS_VOTOS) {
                data.getResultados().add(getResultadoPorTipoVoto(resultadoList, otrosVoto));
            }
            calcularPosiciones(data.getResultados());
            return data;
        }
        return null;
    }

    private ResultadoResponseDTO getResultadoPorTipoVoto(List<Resultado> resultadoList, String tipoVoto) {
        List<Resultado> resultadoPorTipo= new ArrayList<>();
        for (Resultado resultado: resultadoList) {
            if(resultado.votosTipo().equals(tipoVoto)){
                resultadoPorTipo.add(resultado);
            }
        }
        ResultadoResponseDTO resultadoResponseDTO = new ResultadoResponseDTO();
        resultadoResponseDTO.setNombre(resultadoPorTipo.get(0).votosTipo());
        resultadoResponseDTO.setVotos(calcularVotos(resultadoPorTipo));
        resultadoResponseDTO.setPorcentaje(calcularPorcentaje(resultadoList, resultadoPorTipo));
        return resultadoResponseDTO;
    }

    private ResultadoResponseDTO getResultadoPorAgrupacion(List<Resultado> resultadoList, String agrupacion) {
        List<Resultado> resultadoPorAgrupacion = new ArrayList<>();
        for (Resultado resultado: resultadoList) {
            if(resultado.agrupacionNombre().equals(agrupacion)){
                resultadoPorAgrupacion.add(resultado);
            }
        }
        ResultadoResponseDTO resultadoResponseDTO = new ResultadoResponseDTO();

        resultadoResponseDTO.setNombre(resultadoPorAgrupacion.get(0).agrupacionNombre());
        resultadoResponseDTO.setVotos(calcularVotos(resultadoPorAgrupacion));
        resultadoResponseDTO.setPorcentaje(calcularPorcentaje(resultadoList, resultadoPorAgrupacion));
        return resultadoResponseDTO;
    }

    private Double calcularPorcentaje(List<Resultado> allResultados, List<Resultado> resultadoPorAgrupacion) {
        Long allVotos = 0L;
        for (Resultado resultado: allResultados) {
            allVotos += resultado.votosCantidad();
        }
        Long votoPorAsignacion = 0L;
        for (Resultado resultado: resultadoPorAgrupacion) {
            votoPorAsignacion += resultado.votosCantidad();
        }
        double respuesta = (double) votoPorAsignacion  / allVotos;
        String formato = "#0." + "0".repeat(4);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat(formato, symbols);
        String numeroFormateado = df.format(respuesta);
        return Double.parseDouble(numeroFormateado);
    }


    private Long calcularVotos(List<Resultado> resultadoPorAgrupacion) {
        Long votos = 0L;
        for (Resultado resultado: resultadoPorAgrupacion) {
            votos += resultado.votosCantidad();
        }
        return votos;
    }

    private void calcularPosiciones(List<ResultadoResponseDTO> resultados) {
        resultados.sort(Comparator.comparingLong(ResultadoResponseDTO::getVotos).reversed());
        asignarPosiciones(resultados);
    }


    private static void asignarPosiciones(List<ResultadoResponseDTO> resultados) {
        Long posicion = 1L;
        for (ResultadoResponseDTO resultado : resultados) {
            resultado.setOrden( posicion++);
        }
    }
}
