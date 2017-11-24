package test.myapp.service.impl;

import test.myapp.service.RecordService;
import test.myapp.domain.Record;
import test.myapp.repository.RecordRepository;
import test.myapp.service.dto.RecordDTO;
import test.myapp.service.mapper.RecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Record.
 */
@Service
@Transactional
public class RecordServiceImpl implements RecordService{

    private final Logger log = LoggerFactory.getLogger(RecordServiceImpl.class);

    private final RecordRepository recordRepository;

    private final RecordMapper recordMapper;

    public RecordServiceImpl(RecordRepository recordRepository, RecordMapper recordMapper) {
        this.recordRepository = recordRepository;
        this.recordMapper = recordMapper;
    }

    /**
     * Save a record.
     *
     * @param recordDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RecordDTO save(RecordDTO recordDTO) {
        log.debug("Request to save Record : {}", recordDTO);
        Record record = recordMapper.toEntity(recordDTO);
        record = recordRepository.save(record);
        return recordMapper.toDto(record);
    }

    /**
     *  Get all the records.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RecordDTO> findAll() {
        log.debug("Request to get all Records");
        return recordRepository.findAll().stream()
            .map(recordMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one record by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RecordDTO findOne(Long id) {
        log.debug("Request to get Record : {}", id);
        Record record = recordRepository.findOne(id);
        return recordMapper.toDto(record);
    }

    /**
     *  Delete the  record by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Record : {}", id);
        recordRepository.delete(id);
    }
}
