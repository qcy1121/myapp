package test.myapp.service;

import test.myapp.service.dto.WxUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing WxUser.
 */
public interface WxUserService {

    /**
     * Save a wxUser.
     *
     * @param wxUserDTO the entity to save
     * @return the persisted entity
     */
    WxUserDTO save(WxUserDTO wxUserDTO);

    /**
     *  Get all the wxUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<WxUserDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" wxUser.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WxUserDTO findOne(Long id);

    /**
     *  Delete the "id" wxUser.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
