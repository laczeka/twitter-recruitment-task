package pl.laczek.adam.task.twitter.domain.ports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.laczek.adam.task.twitter.domain.model.author.AuthorJpa;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorJpa, String> {

}
