package test.myapp.service.impl;

import test.myapp.service.WxUserService;
import test.myapp.domain.WxUser;
import test.myapp.repository.WxUserRepository;
import test.myapp.service.dto.WxUserDTO;
import test.myapp.service.mapper.WxUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing WxUser.
 */
@Service
@Transactional
public class WxUserServiceImpl implements WxUserService{

    private final Logger log = LoggerFactory.getLogger(WxUserServiceImpl.class);

    private final WxUserRepository wxUserRepository;

    private final WxUserMapper wxUserMapper;

    public WxUserServiceImpl(WxUserRepository wxUserRepository, WxUserMapper wxUserMapper) {
        this.wxUserRepository = wxUserRepository;
        this.wxUserMapper = wxUserMapper;
    }

    /**
     * Save a wxUser.
     *
     * @param wxUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WxUserDTO save(WxUserDTO wxUserDTO) {
        log.debug("Request to save WxUser : {}", wxUserDTO);
        WxUser wxUser = wxUserMapper.toEntity(wxUserDTO);
        wxUser = wxUserRepository.save(wxUser);
        return wxUserMapper.toDto(wxUser);
    }

    /**
     *  Get all the wxUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WxUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WxUsers");
        return wxUserRepository.findAll(pageable)
            .map(wxUserMapper::toDto);
    }

    /**
     *  Get one wxUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WxUserDTO findOne(Long id) {
        log.debug("Request to get WxUser : {}", id);
        WxUser wxUser = wxUserRepository.findOne(id);
        return wxUserMapper.toDto(wxUser);
    }

    /**
     *  Delete the  wxUser by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WxUser : {}", id);
        wxUserRepository.delete(id);
    }
}
