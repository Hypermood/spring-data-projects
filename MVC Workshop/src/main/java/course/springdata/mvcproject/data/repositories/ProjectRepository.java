package course.springdata.mvcproject.data.repositories;

import course.springdata.mvcproject.data.entities.Project;
import course.springdata.mvcproject.data.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    //TODO
}
