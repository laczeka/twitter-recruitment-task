package pl.laczek.adam.task.twitter.domain.model.follow;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "FOLLOWING")
public class FollowingJpa implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String followedUserId;
    @CreatedDate
    private LocalDateTime createTime;

    @Override
    public boolean isNew() {
        return getId() == null;
    }

}
