package actiOn.store.dto.mainrep;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MainPageResponseDto {

    private List<RecommendDto> recommend;

    private List<DataDto> data;
}
