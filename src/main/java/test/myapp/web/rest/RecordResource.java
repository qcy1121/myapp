package test.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import test.myapp.service.RecordService;
import test.myapp.web.rest.errors.BadRequestAlertException;
import test.myapp.web.rest.util.HeaderUtil;
import test.myapp.service.dto.RecordDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Record.
 */
@RestController
@RequestMapping("/api")
public class RecordResource {

    private final Logger log = LoggerFactory.getLogger(RecordResource.class);

    private static final String ENTITY_NAME = "record";

    private final RecordService recordService;

    public RecordResource(RecordService recordService) {
        this.recordService = recordService;
    }

    /**
     * POST  /records : Create a new record.
     *
     * @param recordDTO the recordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recordDTO, or with status 400 (Bad Request) if the record has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/records")
    @Timed
    public ResponseEntity<RecordDTO> createRecord(@Valid @RequestBody RecordDTO recordDTO) throws URISyntaxException {
        log.debug("REST request to save Record : {}", recordDTO);
        if (recordDTO.getId() != null) {
            throw new BadRequestAlertException("A new record cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecordDTO result = recordService.save(recordDTO);
        return ResponseEntity.created(new URI("/api/records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /records : Updates an existing record.
     *
     * @param recordDTO the recordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recordDTO,
     * or with status 400 (Bad Request) if the recordDTO is not valid,
     * or with status 500 (Internal Server Error) if the recordDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/records")
    @Timed
    public ResponseEntity<RecordDTO> updateRecord(@Valid @RequestBody RecordDTO recordDTO) throws URISyntaxException {
        log.debug("REST request to update Record : {}", recordDTO);
        if (recordDTO.getId() == null) {
            return createRecord(recordDTO);
        }
        RecordDTO result = recordService.save(recordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /records : get all the records.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of records in body
     */
    @GetMapping("/records")
    @Timed
    public List<RecordDTO> getAllRecords() {
        log.debug("REST request to get all Records");
        return recordService.findAll();
        }

    /**
     * GET  /records/:id : get the "id" record.
     *
     * @param id the id of the recordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/records/{id}")
    @Timed
    public ResponseEntity<RecordDTO> getRecord(@PathVariable Long id) {
        log.debug("REST request to get Record : {}", id);
        RecordDTO recordDTO = recordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(recordDTO));
    }

    /**
     * DELETE  /records/:id : delete the "id" record.
     *
     * @param id the id of the recordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/records/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        log.debug("REST request to delete Record : {}", id);
        recordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
