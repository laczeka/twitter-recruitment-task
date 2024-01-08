package pl.laczek.adam.task.twitter.domain.model.tweet;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TWEET")
public class TweetJpa implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authorId;
    private String text;
    @CreatedDate
    private LocalDateTime createTime;

    @Override
    public boolean isNew() {
        return getId() == null;
    }

}
