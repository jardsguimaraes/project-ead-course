package com.ead.authuser.especifications;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;

import jakarta.persistence.criteria.Join;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public class EspecificationTemplate {

    @And({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "username", spec = Like.class),
            @Spec(path = "fullName", spec = LikeIgnoreCase.class)
    })
    public interface UserSpec extends Specification<UserModel> {
    }

    public static Specification<UserModel> userCourseId(final UUID courseId) {
        return (root, query, cb) -> {
            if (query != null) {
                query.distinct(true);
            }
            Join<UserModel, UserCourseModel> userJoin = root.join("usersCourses");
            return cb.equal(userJoin.get("courseId"), courseId);
        };
    }
}
