package pl.laczek.adam.task.twitter.domain.model.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.laczek.adam.task.twitter.api.exception.UserNotExistsException;
import pl.laczek.adam.task.twitter.domain.ports.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    public void createAuthorIfNotExists(Author author) {
        if (!repository.existsById(author.getUserId())) {
            repository.save(mapper.toJpa(author));
        }
    }
    public AuthorJpa findAuthorOrThrow(Author author) {
        return repository.findById(author.getUserId()).orElseThrow(() -> new UserNotExistsException(author.getUserId()));
    }

    public void validateIfAuthorExists(String normalizedAuthor){
        repository.findById(normalizedAuthor).orElseThrow(() -> new UserNotExistsException(normalizedAuthor));
    }

}
