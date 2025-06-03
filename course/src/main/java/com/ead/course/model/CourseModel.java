package com.ead.course.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.hateoas.RepresentationModel;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseModel extends RepresentationModel<CourseModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "course_id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID courseId;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(name = "created_at", nullable = false)
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
    // "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @Column(name = "updated_at", nullable = false)
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
    // "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;

    @Column(nullable = false)
    private UUID userInstructor;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    // Exemplo de uso do @OnDelete, onde ao deletar o curso, deleta os módulos
    // e as aulas que estão associados a ele. Mas não é uma boa prática
    // porque pode causar problemas de integridade referencial, pois não
    // temos o controle do que está sendo deletado.
    // @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ModuleModel> modules;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<CourseUserModel> usersCourses;
}
