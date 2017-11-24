package test.myapp.service;

import test.myapp.service.dto.RecordDTO;
import java.util.List;

/**
 * Service Interface for managing Record.
 */
public interface RecordService {

    /**
     * Save a record.
     *
     * @param recordDTO the entity to save
     * @return the persisted entity
     */
    RecordDTO save(RecordDTO recordDTO);

    /**
     *  Get all the records.
     *
     *  @return the list of entities
     */
    List<RecordDTO> findAll();

    /**
     *  Get the "id" record.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RecordDTO findOne(Long id);

    /**
     *  Delete the "id" record.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
