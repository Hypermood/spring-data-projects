package course.springdata.mvcproject.data.repositories;


import course.springdata.mvcproject.data.entities.Company;
import course.springdata.mvcproject.data.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    //TODO
}
