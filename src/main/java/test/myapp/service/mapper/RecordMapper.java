package test.myapp.service.mapper;

import test.myapp.domain.*;
import test.myapp.service.dto.RecordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Record and its DTO RecordDTO.
 */
@Mapper(componentModel = "spring", uses = {WxUserMapper.class})
public interface RecordMapper extends EntityMapper<RecordDTO, Record> {

    @Mapping(source = "openid.id", target = "openidId")
    RecordDTO toDto(Record record); 

    @Mapping(source = "openidId", target = "openid")
    Record toEntity(RecordDTO recordDTO);

    default Record fromId(Long id) {
        if (id == null) {
            return null;
        }
        Record record = new Record();
        record.setId(id);
        return record;
    }
}
