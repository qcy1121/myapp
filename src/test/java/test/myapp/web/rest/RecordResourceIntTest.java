package test.myapp.web.rest;

import test.myapp.MyApp;

import test.myapp.domain.Record;
import test.myapp.repository.RecordRepository;
import test.myapp.service.RecordService;
import test.myapp.service.dto.RecordDTO;
import test.myapp.service.mapper.RecordMapper;
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
 * Test class for the RecordResource REST controller.
 *
 * @see RecordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApp.class)
public class RecordResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ISDELETED = 1;
    private static final Integer UPDATED_ISDELETED = 2;

    private static final Integer DEFAULT_ISDONE = 1;
    private static final Integer UPDATED_ISDONE = 2;

    private static final Integer DEFAULT_EVENTLEVEL = 1;
    private static final Integer UPDATED_EVENTLEVEL = 2;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private RecordService recordService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecordMockMvc;

    private Record record;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecordResource recordResource = new RecordResource(recordService);
        this.restRecordMockMvc = MockMvcBuilders.standaloneSetup(recordResource)
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
    public static Record createEntity(EntityManager em) {
        Record record = new Record()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .createtime(DEFAULT_CREATETIME)
            .updatetime(DEFAULT_UPDATETIME)
            .isdeleted(DEFAULT_ISDELETED)
            .isdone(DEFAULT_ISDONE)
            .eventlevel(DEFAULT_EVENTLEVEL);
        return record;
    }

    @Before
    public void initTest() {
        record = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecord() throws Exception {
        int databaseSizeBeforeCreate = recordRepository.findAll().size();

        // Create the Record
        RecordDTO recordDTO = recordMapper.toDto(record);
        restRecordMockMvc.perform(post("/api/records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordDTO)))
            .andExpect(status().isCreated());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeCreate + 1);
        Record testRecord = recordList.get(recordList.size() - 1);
        assertThat(testRecord.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testRecord.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testRecord.getCreatetime()).isEqualTo(DEFAULT_CREATETIME);
        assertThat(testRecord.getUpdatetime()).isEqualTo(DEFAULT_UPDATETIME);
        assertThat(testRecord.getIsdeleted()).isEqualTo(DEFAULT_ISDELETED);
        assertThat(testRecord.getIsdone()).isEqualTo(DEFAULT_ISDONE);
        assertThat(testRecord.getEventlevel()).isEqualTo(DEFAULT_EVENTLEVEL);
    }

    @Test
    @Transactional
    public void createRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recordRepository.findAll().size();

        // Create the Record with an existing ID
        record.setId(1L);
        RecordDTO recordDTO = recordMapper.toDto(record);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecordMockMvc.perform(post("/api/records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRecords() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);

        // Get all the recordList
        restRecordMockMvc.perform(get("/api/records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(record.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].createtime").value(hasItem(DEFAULT_CREATETIME.toString())))
            .andExpect(jsonPath("$.[*].updatetime").value(hasItem(DEFAULT_UPDATETIME.toString())))
            .andExpect(jsonPath("$.[*].isdeleted").value(hasItem(DEFAULT_ISDELETED)))
            .andExpect(jsonPath("$.[*].isdone").value(hasItem(DEFAULT_ISDONE)))
            .andExpect(jsonPath("$.[*].eventlevel").value(hasItem(DEFAULT_EVENTLEVEL)));
    }

    @Test
    @Transactional
    public void getRecord() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);

        // Get the record
        restRecordMockMvc.perform(get("/api/records/{id}", record.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(record.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.createtime").value(DEFAULT_CREATETIME.toString()))
            .andExpect(jsonPath("$.updatetime").value(DEFAULT_UPDATETIME.toString()))
            .andExpect(jsonPath("$.isdeleted").value(DEFAULT_ISDELETED))
            .andExpect(jsonPath("$.isdone").value(DEFAULT_ISDONE))
            .andExpect(jsonPath("$.eventlevel").value(DEFAULT_EVENTLEVEL));
    }

    @Test
    @Transactional
    public void getNonExistingRecord() throws Exception {
        // Get the record
        restRecordMockMvc.perform(get("/api/records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecord() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);
        int databaseSizeBeforeUpdate = recordRepository.findAll().size();

        // Update the record
        Record updatedRecord = recordRepository.findOne(record.getId());
        updatedRecord
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .createtime(UPDATED_CREATETIME)
            .updatetime(UPDATED_UPDATETIME)
            .isdeleted(UPDATED_ISDELETED)
            .isdone(UPDATED_ISDONE)
            .eventlevel(UPDATED_EVENTLEVEL);
        RecordDTO recordDTO = recordMapper.toDto(updatedRecord);

        restRecordMockMvc.perform(put("/api/records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordDTO)))
            .andExpect(status().isOk());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeUpdate);
        Record testRecord = recordList.get(recordList.size() - 1);
        assertThat(testRecord.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testRecord.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testRecord.getCreatetime()).isEqualTo(UPDATED_CREATETIME);
        assertThat(testRecord.getUpdatetime()).isEqualTo(UPDATED_UPDATETIME);
        assertThat(testRecord.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testRecord.getIsdone()).isEqualTo(UPDATED_ISDONE);
        assertThat(testRecord.getEventlevel()).isEqualTo(UPDATED_EVENTLEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingRecord() throws Exception {
        int databaseSizeBeforeUpdate = recordRepository.findAll().size();

        // Create the Record
        RecordDTO recordDTO = recordMapper.toDto(record);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRecordMockMvc.perform(put("/api/records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordDTO)))
            .andExpect(status().isCreated());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRecord() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);
        int databaseSizeBeforeDelete = recordRepository.findAll().size();

        // Get the record
        restRecordMockMvc.perform(delete("/api/records/{id}", record.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Record.class);
        Record record1 = new Record();
        record1.setId(1L);
        Record record2 = new Record();
        record2.setId(record1.getId());
        assertThat(record1).isEqualTo(record2);
        record2.setId(2L);
        assertThat(record1).isNotEqualTo(record2);
        record1.setId(null);
        assertThat(record1).isNotEqualTo(record2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecordDTO.class);
        RecordDTO recordDTO1 = new RecordDTO();
        recordDTO1.setId(1L);
        RecordDTO recordDTO2 = new RecordDTO();
        assertThat(recordDTO1).isNotEqualTo(recordDTO2);
        recordDTO2.setId(recordDTO1.getId());
        assertThat(recordDTO1).isEqualTo(recordDTO2);
        recordDTO2.setId(2L);
        assertThat(recordDTO1).isNotEqualTo(recordDTO2);
        recordDTO1.setId(null);
        assertThat(recordDTO1).isNotEqualTo(recordDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recordMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recordMapper.fromId(null)).isNull();
    }
}
