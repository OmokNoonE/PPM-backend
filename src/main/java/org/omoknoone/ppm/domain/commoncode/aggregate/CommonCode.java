package org.omoknoone.ppm.domain.commoncode.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "common_code")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CommonCode {

    @Id
    @Column(name = "code_id")
    private Long codeId;

    @Column(name = "code_name", nullable = false)
    private String codeName;

    @Column(name = "code_description", nullable = false)
    private String codeDescription;

    @Column(name = "code_is_active", nullable = false)
    private boolean codeIsActive = true;

    @Column(name = "code_is_deleted", nullable = false)
    private boolean codeIsDeleted = false;

    @CreationTimestamp
    @Column(name = "code_deleted_date")
    private LocalDateTime codeDeletedDate;

    @JoinColumn(name = "code_group_id")
    private Long codeGroupId;

}
