package test.myapp.web.rest;

import test.myapp.MyApp;

import test.myapp.domain.WxUser;
import test.myapp.repository.WxUserRepository;
import test.myapp.service.WxUserService;
import test.myapp.service.dto.WxUserDTO;
import test.myapp.service.mapper.WxUserMapper;
import test.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static test.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WxUserResource REST controller.
 *
 * @see WxUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApp.class)
public class WxUserResourceIntTest {

    private static final String DEFAULT_OPENID = "AAAAAAAAAA";
    private static final String UPDATED_OPENID = "BBBBBBBBBB";

    private static final String DEFAULT_SUBSCRIBE = "AAAAAAAAAA";
    private static final String UPDATED_SUBSCRIBE = "BBBBBBBBBB";

    private static final String DEFAULT_NICK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NICK_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEX = 1;
    private static final Integer UPDATED_SEX = 2;

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Instant DEFAULT_SUBSCRIBETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUBSCRIBETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_HEADIMGURL = "AAAAAAAAAA";
    private static final String UPDATED_HEADIMGURL = "BBBBBBBBBB";

    private static final Integer DEFAULT_ISCANCEL = 1;
    private static final Integer UPDATED_ISCANCEL = 2;

    private static final Instant DEFAULT_CREATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private WxUserRepository wxUserRepository;

    @Autowired
    private WxUserMapper wxUserMapper;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWxUserMockMvc;

    private WxUser wxUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WxUserResource wxUserResource = new WxUserResource(wxUserService);
        this.restWxUserMockMvc = MockMvcBuilders.standaloneSetup(wxUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WxUser createEntity(EntityManager em) {
        WxUser wxUser = new WxUser()
            .openid(DEFAULT_OPENID)
            .subscribe(DEFAULT_SUBSCRIBE)
            .nickName(DEFAULT_NICK_NAME)
            .sex(DEFAULT_SEX)
            .language(DEFAULT_LANGUAGE)
            .city(DEFAULT_CITY)
            .province(DEFAULT_PROVINCE)
            .country(DEFAULT_COUNTRY)
            .subscribetime(DEFAULT_SUBSCRIBETIME)
            .headimgurl(DEFAULT_HEADIMGURL)
            .iscancel(DEFAULT_ISCANCEL)
            .createtime(DEFAULT_CREATETIME)
            .updatetime(DEFAULT_UPDATETIME);
        return wxUser;
    }

    @Before
    public void initTest() {
        wxUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createWxUser() throws Exception {
        int databaseSizeBeforeCreate = wxUserRepository.findAll().size();

        // Create the WxUser
        WxUserDTO wxUserDTO = wxUserMapper.toDto(wxUser);
        restWxUserMockMvc.perform(post("/api/wx-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wxUserDTO)))
            .andExpect(status().isCreated());

        // Validate the WxUser in the database
        List<WxUser> wxUserList = wxUserRepository.findAll();
        assertThat(wxUserList).hasSize(databaseSizeBeforeCreate + 1);
        WxUser testWxUser = wxUserList.get(wxUserList.size() - 1);
        assertThat(testWxUser.getOpenid()).isEqualTo(DEFAULT_OPENID);
        assertThat(testWxUser.getSubscribe()).isEqualTo(DEFAULT_SUBSCRIBE);
        assertThat(testWxUser.getNickName()).isEqualTo(DEFAULT_NICK_NAME);
        assertThat(testWxUser.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testWxUser.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testWxUser.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testWxUser.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testWxUser.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testWxUser.getSubscribetime()).isEqualTo(DEFAULT_SUBSCRIBETIME);
        assertThat(testWxUser.getHeadimgurl()).isEqualTo(DEFAULT_HEADIMGURL);
        assertThat(testWxUser.getIscancel()).isEqualTo(DEFAULT_ISCANCEL);
        assertThat(testWxUser.getCreatetime()).isEqualTo(DEFAULT_CREATETIME);
        assertThat(testWxUser.getUpdatetime()).isEqualTo(DEFAULT_UPDATETIME);
    }

    @Test
    @Transactional
    public void createWxUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wxUserRepository.findAll().size();

        // Create the WxUser with an existing ID
        wxUser.setId(1L);
        WxUserDTO wxUserDTO = wxUserMapper.toDto(wxUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWxUserMockMvc.perform(post("/api/wx-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wxUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WxUser in the database
        List<WxUser> wxUserList = wxUserRepository.findAll();
        assertThat(wxUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWxUsers() throws Exception {
        // Initialize the database
        wxUserRepository.saveAndFlush(wxUser);

        // Get all the wxUserList
        restWxUserMockMvc.perform(get("/api/wx-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wxUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].openid").value(hasItem(DEFAULT_OPENID.toString())))
            .andExpect(jsonPath("$.[*].subscribe").value(hasItem(DEFAULT_SUBSCRIBE.toString())))
            .andExpect(jsonPath("$.[*].nickName").value(hasItem(DEFAULT_NICK_NAME.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].subscribetime").value(hasItem(DEFAULT_SUBSCRIBETIME.toString())))
            .andExpect(jsonPath("$.[*].headimgurl").value(hasItem(DEFAULT_HEADIMGURL.toString())))
            .andExpect(jsonPath("$.[*].iscancel").value(hasItem(DEFAULT_ISCANCEL)))
            .andExpect(jsonPath("$.[*].createtime").value(hasItem(DEFAULT_CREATETIME.toString())))
            .andExpect(jsonPath("$.[*].updatetime").value(hasItem(DEFAULT_UPDATETIME.toString())));
    }

    @Test
    @Transactional
    public void getWxUser() throws Exception {
        // Initialize the database
        wxUserRepository.saveAndFlush(wxUser);

        // Get the wxUser
        restWxUserMockMvc.perform(get("/api/wx-users/{id}", wxUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wxUser.getId().intValue()))
            .andExpect(jsonPath("$.openid").value(DEFAULT_OPENID.toString()))
            .andExpect(jsonPath("$.subscribe").value(DEFAULT_SUBSCRIBE.toString()))
            .andExpect(jsonPath("$.nickName").value(DEFAULT_NICK_NAME.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.subscribetime").value(DEFAULT_SUBSCRIBETIME.toString()))
            .andExpect(jsonPath("$.headimgurl").value(DEFAULT_HEADIMGURL.toString()))
            .andExpect(jsonPath("$.iscancel").value(DEFAULT_ISCANCEL))
            .andExpect(jsonPath("$.createtime").value(DEFAULT_CREATETIME.toString()))
            .andExpect(jsonPath("$.updatetime").value(DEFAULT_UPDATETIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWxUser() throws Exception {
        // Get the wxUser
        restWxUserMockMvc.perform(get("/api/wx-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWxUser() throws Exception {
        // Initialize the database
        wxUserRepository.saveAndFlush(wxUser);
        int databaseSizeBeforeUpdate = wxUserRepository.findAll().size();

        // Update the wxUser
        WxUser updatedWxUser = wxUserRepository.findOne(wxUser.getId());
        updatedWxUser
            .openid(UPDATED_OPENID)
            .subscribe(UPDATED_SUBSCRIBE)
            .nickName(UPDATED_NICK_NAME)
            .sex(UPDATED_SEX)
            .language(UPDATED_LANGUAGE)
            .city(UPDATED_CITY)
            .province(UPDATED_PROVINCE)
            .country(UPDATED_COUNTRY)
            .subscribetime(UPDATED_SUBSCRIBETIME)
            .headimgurl(UPDATED_HEADIMGURL)
            .iscancel(UPDATED_ISCANCEL)
            .createtime(UPDATED_CREATETIME)
            .updatetime(UPDATED_UPDATETIME);
        WxUserDTO wxUserDTO = wxUserMapper.toDto(updatedWxUser);

        restWxUserMockMvc.perform(put("/api/wx-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wxUserDTO)))
            .andExpect(status().isOk());

        // Validate the WxUser in the database
        List<WxUser> wxUserList = wxUserRepository.findAll();
        assertThat(wxUserList).hasSize(databaseSizeBeforeUpdate);
        WxUser testWxUser = wxUserList.get(wxUserList.size() - 1);
        assertThat(testWxUser.getOpenid()).isEqualTo(UPDATED_OPENID);
        assertThat(testWxUser.getSubscribe()).isEqualTo(UPDATED_SUBSCRIBE);
        assertThat(testWxUser.getNickName()).isEqualTo(UPDATED_NICK_NAME);
        assertThat(testWxUser.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testWxUser.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testWxUser.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testWxUser.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testWxUser.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testWxUser.getSubscribetime()).isEqualTo(UPDATED_SUBSCRIBETIME);
        assertThat(testWxUser.getHeadimgurl()).isEqualTo(UPDATED_HEADIMGURL);
        assertThat(testWxUser.getIscancel()).isEqualTo(UPDATED_ISCANCEL);
        assertThat(testWxUser.getCreatetime()).isEqualTo(UPDATED_CREATETIME);
        assertThat(testWxUser.getUpdatetime()).isEqualTo(UPDATED_UPDATETIME);
    }

    @Test
    @Transactional
    public void updateNonExistingWxUser() throws Exception {
        int databaseSizeBeforeUpdate = wxUserRepository.findAll().size();

        // Create the WxUser
        WxUserDTO wxUserDTO = wxUserMapper.toDto(wxUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWxUserMockMvc.perform(put("/api/wx-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wxUserDTO)))
            .andExpect(status().isCreated());

        // Validate the WxUser in the database
        List<WxUser> wxUserList = wxUserRepository.findAll();
        assertThat(wxUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWxUser() throws Exception {
        // Initialize the database
        wxUserRepository.saveAndFlush(wxUser);
        int databaseSizeBeforeDelete = wxUserRepository.findAll().size();

        // Get the wxUser
        restWxUserMockMvc.perform(delete("/api/wx-users/{id}", wxUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WxUser> wxUserList = wxUserRepository.findAll();
        assertThat(wxUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WxUser.class);
        WxUser wxUser1 = new WxUser();
        wxUser1.setId(1L);
        WxUser wxUser2 = new WxUser();
        wxUser2.setId(wxUser1.getId());
        assertThat(wxUser1).isEqualTo(wxUser2);
        wxUser2.setId(2L);
        assertThat(wxUser1).isNotEqualTo(wxUser2);
        wxUser1.setId(null);
        assertThat(wxUser1).isNotEqualTo(wxUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WxUserDTO.class);
        WxUserDTO wxUserDTO1 = new WxUserDTO();
        wxUserDTO1.setId(1L);
        WxUserDTO wxUserDTO2 = new WxUserDTO();
        assertThat(wxUserDTO1).isNotEqualTo(wxUserDTO2);
        wxUserDTO2.setId(wxUserDTO1.getId());
        assertThat(wxUserDTO1).isEqualTo(wxUserDTO2);
        wxUserDTO2.setId(2L);
        assertThat(wxUserDTO1).isNotEqualTo(wxUserDTO2);
        wxUserDTO1.setId(null);
        assertThat(wxUserDTO1).isNotEqualTo(wxUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(wxUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(wxUserMapper.fromId(null)).isNull();
    }
}
