package application.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Role")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "role_code", nullable = false, unique = true)
    private String roleCode;

    @Basic
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;
}
