package com.ead.course.specifications;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.ead.course.model.CourseModel;
import com.ead.course.model.CourseUserModel;
import com.ead.course.model.LessonModel;
import com.ead.course.model.ModuleModel;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public class SpecificationTemplate {

    @And({
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = LikeIgnoreCase.class),
            @Spec(path = "userInstructor", spec = Equal.class)
    })
    public interface CourseSpec extends Specification<CourseModel> {
    }

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface ModuleSpec extends Specification<ModuleModel> {
    }

    public static Specification<ModuleModel> moduleCourseId(final UUID courseId) {
        return (root, query, cb) -> {
            Objects.requireNonNull(query, "query cannot be null");
            query.distinct(true);
            Root<ModuleModel> module = root;
            Root<CourseModel> course = query.from(CourseModel.class);
            Expression<Collection<ModuleModel>> courseModules = course.get("modules");
            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(module, courseModules));
        };
    }

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface LessonSpec extends Specification<LessonModel> {
    }

    public static Specification<LessonModel> lessonModuleId(final UUID moduleId) {
        return (root, query, cb) -> {
            Objects.requireNonNull(query, "query connot be null");
            query.distinct(true);
            Root<LessonModel> lesson = root;
            Root<ModuleModel> module = query.from(ModuleModel.class);
            Expression<Collection<LessonModel>> lessonModules = module.get("lessons");
            return cb.and(cb.equal(module.get("moduleId"), moduleId), cb.isMember(lesson, lessonModules));
        };
    }

    public static Specification<CourseModel> courseModelId(final UUID userId) {
        return(root, query, cb) -> {
            if (query != null) {
                query.distinct(true);
            }
            Join<CourseModel, CourseUserModel> courseJoin = root.join("usersCourses");
            return cb.equal(courseJoin.get("userId"), userId);
        };
    }
}
