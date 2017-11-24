package test.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import test.myapp.service.WxUserService;
import test.myapp.web.rest.errors.BadRequestAlertException;
import test.myapp.web.rest.util.HeaderUtil;
import test.myapp.web.rest.util.PaginationUtil;
import test.myapp.service.dto.WxUserDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing WxUser.
 */
@RestController
@RequestMapping("/api")
public class WxUserResource {

    private final Logger log = LoggerFactory.getLogger(WxUserResource.class);

    private static final String ENTITY_NAME = "wxUser";

    private final WxUserService wxUserService;

    public WxUserResource(WxUserService wxUserService) {
        this.wxUserService = wxUserService;
    }

    /**
     * POST  /wx-users : Create a new wxUser.
     *
     * @param wxUserDTO the wxUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wxUserDTO, or with status 400 (Bad Request) if the wxUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wx-users")
    @Timed
    public ResponseEntity<WxUserDTO> createWxUser(@Valid @RequestBody WxUserDTO wxUserDTO) throws URISyntaxException {
        log.debug("REST request to save WxUser : {}", wxUserDTO);
        if (wxUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new wxUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WxUserDTO result = wxUserService.save(wxUserDTO);
        return ResponseEntity.created(new URI("/api/wx-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wx-users : Updates an existing wxUser.
     *
     * @param wxUserDTO the wxUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wxUserDTO,
     * or with status 400 (Bad Request) if the wxUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the wxUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wx-users")
    @Timed
    public ResponseEntity<WxUserDTO> updateWxUser(@Valid @RequestBody WxUserDTO wxUserDTO) throws URISyntaxException {
        log.debug("REST request to update WxUser : {}", wxUserDTO);
        if (wxUserDTO.getId() == null) {
            return createWxUser(wxUserDTO);
        }
        WxUserDTO result = wxUserService.save(wxUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, wxUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wx-users : get all the wxUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wxUsers in body
     */
    @GetMapping("/wx-users")
    @Timed
    public ResponseEntity<List<WxUserDTO>> getAllWxUsers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of WxUsers");
        Page<WxUserDTO> page = wxUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wx-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wx-users/:id : get the "id" wxUser.
     *
     * @param id the id of the wxUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wxUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/wx-users/{id}")
    @Timed
    public ResponseEntity<WxUserDTO> getWxUser(@PathVariable Long id) {
        log.debug("REST request to get WxUser : {}", id);
        WxUserDTO wxUserDTO = wxUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(wxUserDTO));
    }

    /**
     * DELETE  /wx-users/:id : delete the "id" wxUser.
     *
     * @param id the id of the wxUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wx-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteWxUser(@PathVariable Long id) {
        log.debug("REST request to delete WxUser : {}", id);
        wxUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
