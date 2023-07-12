package actiOn.business.mapper;

import actiOn.business.dto.BusinessDto;
import actiOn.business.entity.Business;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface BusinessMapper {
    Business BusinessPostDtoToBusiness(BusinessDto.Post requestBody);
}
