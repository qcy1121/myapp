package test.myapp.service.mapper;

import test.myapp.domain.*;
import test.myapp.service.dto.WxUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WxUser and its DTO WxUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WxUserMapper extends EntityMapper<WxUserDTO, WxUser> {

    

    @Mapping(target = "openids", ignore = true)
    WxUser toEntity(WxUserDTO wxUserDTO);

    default WxUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        WxUser wxUser = new WxUser();
        wxUser.setId(id);
        return wxUser;
    }
}
