package application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChartHome {

    private List<String> categories;
    private List<ChartData> series;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChartData {
        String name;
        List<Long> data;
    }
}
