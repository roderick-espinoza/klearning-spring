package com.cibertec.klearning.service;

import com.cibertec.klearning.entity.Rol;
import com.cibertec.klearning.repository.RolRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;
    private final EntityManager entityManager;

    /**
     * Guarda un rol individual con flush automático
     * El flush se ejecuta automáticamente al finalizar la transacción
     */
    @Transactional
    public Rol guardar(Rol rol) {
        return rolRepository.save(rol);
    }

    /**
     * Guarda múltiples roles en batch con flush manual para optimizar rendimiento
     * 
     * ESTRATEGIA DE FLUSH:
     * - Se usa flush() y clear() cada N registros para evitar el problema de memoria
     * - flush(): Sincroniza el contexto de persistencia con la base de datos
     * - clear(): Limpia el contexto de persistencia para liberar memoria
     * - Esto evita que Hibernate acumule demasiados objetos en memoria
     * 
     * @param roles Lista de roles a guardar
     * @param batchSize Tamaño del batch (recomendado: 20-50)
     */
    @Transactional
    public void guardarEnBatch(List<Rol> roles, int batchSize) {
        for (int i = 0; i < roles.size(); i++) {
            rolRepository.save(roles.get(i));
            
            // Flush y clear cada batchSize registros
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        
        // Flush final para los registros restantes
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Actualiza un rol con flush explícito para sincronizar inmediatamente
     * Útil cuando se necesita que los cambios estén disponibles inmediatamente
     */
    @Transactional
    public Rol actualizarConFlush(Rol rol) {
        Rol rolActualizado = rolRepository.save(rol);
        entityManager.flush(); // Flush explícito para sincronizar con BD
        return rolActualizado;
    }

    /**
     * Elimina un rol con flush explícito
     */
    @Transactional
    public void eliminarConFlush(String idRol) {
        rolRepository.deleteById(idRol);
        entityManager.flush(); // Flush explícito para asegurar eliminación inmediata
    }

    /**
     * Obtiene un rol con sus usuarios usando FETCH JOIN
     * Evita el problema N+1 queries
     */
    @Transactional
    public Rol obtenerConUsuarios(String idRol) {
        return rolRepository.obtenerConUsuarios(idRol).orElse(null);
    }

    /**
     * Lista todos los roles activos sin cargar usuarios (LAZY)
     * Optimizado para rendimiento cuando no se necesitan los usuarios
     */
    @Transactional
    public List<Rol> listarActivos() {
        return rolRepository.listarActivos();
    }
}
