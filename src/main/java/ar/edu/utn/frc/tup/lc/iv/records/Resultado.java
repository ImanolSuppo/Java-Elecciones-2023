package ar.edu.utn.frc.tup.lc.iv.records;

public record Resultado(Long id, Long distritoId, String distritoNombre, Long seccionId, String seccionNombre,
                        Long agrupacionId,
                        String agrupacionNombre,
                        String votosTipo,
                        Long votosCantidad ) {
}
