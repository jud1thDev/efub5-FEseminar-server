package efub.assignment.community.global.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false)  //UPDATE문에서 해당 칼럼을 제외
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)  //INSERT문에서 해당 칼럼을 제외
    private LocalDateTime modifiedDate;
}
