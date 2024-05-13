package org.omoknoone.ppm.domain.role.aggregate;

import jakarta.persistence.*;
import lombok.*;

@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Integer id;

    @Column(name = "role_name", nullable = false, length = 16)
    private String roleName;

    @Column(name = "role_is_deleted", nullable = false)
    private Boolean roleIsDeleted = false;

    @Column(name = "role_deleted_date", length = 30)
    private String roleDeletedDate;

    @Builder
    public Role(Integer id, String roleName, Boolean roleIsDeleted, String roleDeletedDate) {
        this.id = id;
        this.roleName = roleName;
        this.roleIsDeleted = roleIsDeleted;
        this.roleDeletedDate = roleDeletedDate;
    }
}