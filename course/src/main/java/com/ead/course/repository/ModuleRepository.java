package com.ead.course.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ead.course.model.ModuleModel;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID> {

    // Exemplo de uso do EntityGraph
    // @EntityGraph(attributePaths = {"course"})
    // ModuleModel findByTitle(String title);

    // Sempre que precisar de um delete ou update, o ideal é usar o @Modifying antes do @Query
    @Query(value = "SELECT * FROM tb_modules WHERE course_course_id = :courseId", nativeQuery = true)
    List<ModuleModel> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

    @Query(value = "SELECT * FROM tb_modules WHERE course_course_id = :courseId AND module_id = :moduleId", nativeQuery = true)
    Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);
}
