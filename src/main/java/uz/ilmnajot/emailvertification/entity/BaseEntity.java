package uz.ilmnajot.emailvertification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.sql.Timestamp;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private Timestamp createdAt;

    private Timestamp updatedAt;

    @Column(updatable = false)
    private Timestamp createdBy;

    private Timestamp updatedBy;
}
