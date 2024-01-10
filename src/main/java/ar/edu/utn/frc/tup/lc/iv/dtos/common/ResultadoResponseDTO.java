package ar.edu.utn.frc.tup.lc.iv.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoResponseDTO {

    private Long orden;

    private String nombre;

    private Long votos;

    private Double porcentaje;
}
