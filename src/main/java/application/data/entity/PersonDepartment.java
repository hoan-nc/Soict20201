package application.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "person_department", schema = "db", catalog = "")
public class PersonDepartment {
    private int personDepartmentId;
    private Long personId;
    private Long departmentId;

    @Id
    @Column(name = "person_department_id")
    public int getPersonDepartmentId() {
        return personDepartmentId;
    }

    public void setPersonDepartmentId(int personDepartmentId) {
        this.personDepartmentId = personDepartmentId;
    }

    @Basic
    @Column(name = "person_id")
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "department_id")
    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDepartment that = (PersonDepartment) o;
        return personDepartmentId == that.personDepartmentId &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personDepartmentId, personId, departmentId);
    }
}
