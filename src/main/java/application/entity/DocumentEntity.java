package application.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Document")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "is_active")
    private Long isActive;

    @Basic
    @Column(name = "object_id")
    private Long objectId;

    @Basic
    @Column(name = "path")
    private String path;

    @Basic
    @Column(name = "type")
    private Long type;

}
