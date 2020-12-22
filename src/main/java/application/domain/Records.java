package application.domain;

import application.entity.PhysicalExamEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Records {
    private List<PhysicalExamEntity> listRecords;
}
