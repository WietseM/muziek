package be.vdab.muziek.albums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql({"/artiesten.sql","/labels.sql", "/albums.sql"})
@AutoConfigureMockMvc
class AlbumControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final static String ALBUMS_TABLE = "albums";
    private final static Path TEST_RESOURCES = Path.of("src/test/resources");
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;


    public AlbumControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

    private long idVanTestAlbum1(){
        return jdbcClient.sql("select id from albums where naam = 'test'").query(Long.class).single();
    }

    @Test
    void findAllVindtAlleRecords() throws Exception{
        mockMvc.perform(get("/albums"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()").value(JdbcTestUtils.countRowsInTable(jdbcClient, ALBUMS_TABLE)));
    }

    @Test
    void findByIdMetBestaandeIdVindtJuisteRecord() throws Exception{
        var id = idVanTestAlbum1();
        mockMvc.perform(get("/albums/{id}", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("naam").value("test"),
                        jsonPath("jaar").value(2000),
                        jsonPath("artiest").value("test"));
    }

    @Test
    void findByIdMetOnbestaandeIdVindtGeenRecord() throws Exception{
        mockMvc.perform(get("/albums/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByJaarVindtJuisteAantalRecords() throws Exception{
        mockMvc.perform(get("/albums")
                .param("jaar", "2000"))
                .andExpectAll(status().isOk(),
                        jsonPath("length()").value(JdbcTestUtils.countRowsInTableWhere(jdbcClient, ALBUMS_TABLE, "jaar = 2000")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"legeScore.json", "teHogeScore.json", "teLageScore.json"})
    void wijzigScoreMetFouteScoreMislukt(String bestandsNaam) throws Exception {
        var id = idVanTestAlbum1();
        var jsonData = Files.readString(TEST_RESOURCES.resolve(bestandsNaam));
        mockMvc.perform(patch("/albums/{id}/score", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isBadRequest());
    }

    @Test
    void wijzigScoreMetOnbestaandeIdMislukt() throws Exception {
        var jsonData = Files.readString(TEST_RESOURCES.resolve("juisteScore.json"));
        mockMvc.perform(patch("/albums/{id}/score", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isNotFound());
    }

    //Werkt niet juist
    @Test
    void wijzigScoreMetJuisteDataLukt() throws Exception {
        var id = idVanTestAlbum1();
        var jsonData = Files.readString(TEST_RESOURCES.resolve("juisteScore.json"));
        mockMvc.perform(patch("/albums/{id}/score", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isOk());
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, ALBUMS_TABLE, "score = 5")).isOne();
    }




}