package com.cibertec.klearning.business.data.repository;

import com.cibertec.klearning.business.data.entity.Produccion;
import com.cibertec.klearning.business.data.entity.enums.EstadoRegistro;
import com.cibertec.klearning.business.data.entity.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProduccionRepository extends JpaRepository<Produccion, String> {

    //Sirve para: Obtener todas las tareas de producción según su estado de eliminación lógica.
    List<Produccion> findByEstado(EstadoRegistro estado);

    //Sirve para: Obtener todas las tareas asignadas a una persona específica mediante su ID interno.
    List<Produccion> findByPersona_IdPersonaAndEstado(String idPersona, EstadoRegistro estado);

    //Sirve para: Buscar las tareas que hizo una persona en un proyecto específico.

    List<Produccion> findByPersona_DniCeAndProyecto_IdProyectoAndEstado(String dniCe, String idProyecto, EstadoRegistro estado);

    //Sirve para: Obtener el historial de tareas de un trabajador buscándolo por su DNI.

    Page<Produccion> findByPersona_DniCeAndEstado(String dniCe, EstadoRegistro estado, Pageable pageable);

    //Sirve para: Auditoría de rendimiento. Encuentra las tareas que tomaron más tiempo del esperado.
    @Query("SELECT p FROM Produccion p WHERE p.horasReales > p.horasEstimadas AND p.estado = '1'")
    Page<Produccion> findTareasConExcesoDeTiempo(Pageable pageable);

    //Sirve para: Filtrar tareas que iniciaron dentro de un mes, semana o año específico.
    Page<Produccion> findByFechaHoraInicioBetweenAndEstado(LocalDateTime inicio, LocalDateTime fin, EstadoRegistro estado, Pageable pageable);


    //Sirve para: La grilla de búsqueda principal (Frontend). Permite al usuario buscar por proyecto o cortes, o sin filtros.
    @Query(
            value = """
                    SELECT p.*
                    FROM produccion p
                    WHERE (
                        :idProyecto IS NULL
                        OR p.idProyecto = :idProyecto
                    )
                    AND (
                        :huboCorte IS NULL
                        OR p.huboCorte = :huboCorte
                    )
                    AND p.estado = '1'
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM produccion p
                    WHERE (
                        :idProyecto IS NULL
                        OR p.idProyecto = :idProyecto
                    )
                    AND (
                        :huboCorte IS NULL
                        OR p.huboCorte = :huboCorte
                    )
                    AND p.estado = '1'
                    """,
            nativeQuery = true
    )
    Page<Produccion> buscarProduccionPaginada(
            @Param("idProyecto") String idProyecto,
            @Param("huboCorte") Boolean huboCorte,
            Pageable pageable
    );

    //Sirve para: Saber en cuántos proyectos diferentes ha participado una persona.

    @Query("SELECT DISTINCT p.proyecto FROM Produccion p WHERE p.persona.dniCe = :dni AND p.estado = '1'")
    List<Proyecto> findProyectosDistintosPorPersonaDni(@Param("dni") String dni);

    //Sirve para: Calcular cuántas horas reales en total se han invertido en cada Lección OVA.

    @Query("SELECT p.leccionOva.idLeccionOva AS idLeccionOva, SUM(p.horasReales) AS totalHoras " +
            "FROM Produccion p WHERE p.persona.dniCe = :dni AND p.estado = '1' " +
            "GROUP BY p.leccionOva.idLeccionOva")
    List<TiempoLeccionProjection> calcularTiempoPorLeccionOva(@Param("dni") String dni);


    interface TiempoLeccionProjection {
        String getIdLeccionOva();
        BigDecimal getTotalHoras();
    }


    //Sirve para: Ejecutar lógica pesada directamente en la base de datos MySQL (ej. Cierre masivo).
    @Procedure(name = "Produccion.cerrarPorProyecto")
    void cerrarProduccionPorProyectoSP(
            @Param("p_id_proyecto") String idProyecto
    );
}