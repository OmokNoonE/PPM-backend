package org.omoknoone.ppm.commoncode.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "common_code_group")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CommonCodeGroup {

    @Id
    @Column(name = "code_group_id")
    private Long codeGroupId;

    @Column(name = "code_group_name")
    private String codeGroupName;

    @Column(name = "code_group_description")
    private String codeGroupDescription;

    @Column(name = "code_group_is_active")
    private boolean codeGroupIsActive;

    @Column(name = "code_group_is_deleted")
    private boolean codeGroupIsDeleted;

    @CreationTimestamp
    @Column(name = "code_group_deleted_date")
    private LocalDateTime codeGroupDeletedDate;


}
