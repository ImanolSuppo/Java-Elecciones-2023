package ar.edu.utn.frc.tup.lc.iv.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargosPorDistritoDTO {
    private DistritoResponseDTO distrito;
    private List<CargoResponseDTO> cargos;
}
