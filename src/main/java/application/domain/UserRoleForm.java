package application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleForm implements Serializable {

    private String fullName;

    private String className;

    private String userName;

    private String password;

    private Long roleId;

}
