package pl.laczek.adam.task.twitter.domain.model.author;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.laczek.adam.task.twitter.domain.model.follow.FollowingJpa;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "AUTHOR")
public class AuthorJpa implements Persistable<String> {

    @Id
    private String id;
    @OneToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "userId")
    private Set<FollowingJpa> followings;
    @CreatedDate
    private LocalDateTime createTime;

    @Override
    public boolean isNew() {
        return getId() == null;
    }

}
