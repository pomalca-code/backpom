package com.pomalca.task.serviceImpl;

import com.pomalca.task.service.TaskService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Map<String, Object>> getTasksForUser(Long user_id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getTasksForUser");

        query.registerStoredProcedureParameter("user_id", Long.class, ParameterMode.IN);
        query.setParameter("user_id", user_id);

        List<Object[]> result = query.getResultList();

        return result.stream()
                .map(objArray -> Map.of(
                        "id", objArray[0] != null ? objArray[0] : "N/A",
                        "completed", objArray[1] != null ? objArray[1] : "N",
                        "description", objArray[2] != null ? objArray[2] : "N",
                        "title", objArray[3] != null ? objArray[3] : "N/A",
                        "user_id", objArray[4] != null ? objArray[4] : "N/A",
                        "shared_with_id", objArray[5] != null ? objArray[5] : "N/A"
                ))
                .toList();
    }

    @Override
    public List<Map<String, Object>> getTasksSharedWithUser(Long shared_with_id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getTasksSharedWithUser");

        // Registrar el parámetro del procedimiento almacenado
        query.registerStoredProcedureParameter("shared_with_id", Long.class, ParameterMode.IN);
        query.setParameter("shared_with_id", shared_with_id);

        // Ejecutar la consulta y obtener los resultados
        List<Object[]> result = query.getResultList();

        // Transformar los resultados en una lista de mapas
        return result.stream()
                .map(objArray -> {
                    // Manejo de posibles valores null
                    return Map.of(
                            "id", objArray[0] != null ? objArray[0] : "N/A",
                            "title", objArray[1] != null ? objArray[1] : "N/A",
                            "completed", objArray[2] != null ? objArray[2] : false
                    );
                })
                .toList();
    }

    @Override
    public List<Map<String, Object>> getSharedTasksByTaskId(Long task_id) {
        // Crear la consulta del procedimiento almacenado
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getSharedTasksByTaskId");

        // Registrar el parámetro de entrada del procedimiento almacenado
        query.registerStoredProcedureParameter("task_id", Long.class, ParameterMode.IN);
        query.setParameter("task_id", task_id);

        // Ejecutar la consulta y obtener los resultados
        List<Object[]> result = query.getResultList();

        // Transformar los resultados en una lista de mapas
        return result.stream()
                .map(objArray -> Map.of(
                        "id", objArray[0] != null ? objArray[0] : "N/A",
                        "shared_with_id", objArray[1] != null ? objArray[1] : "N/A",
                        "task_id", objArray[2] != null ? objArray[2] : "N/A",
                        "user_id", objArray[3] != null ? objArray[3] : "N/A"
                ))
                .toList();
    }
}
