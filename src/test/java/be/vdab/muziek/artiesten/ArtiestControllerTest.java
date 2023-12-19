package be.vdab.muziek.artiesten;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Sql({"/artiesten.sql", "/labels.sql", "/albums.sql"})
@AutoConfigureMockMvc
class ArtiestControllerTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final static String ALBUMS_TABLE = "albums";
    private final MockMvc mockMvc;
    private final JdbcClient jdbcClient;

    public ArtiestControllerTest(MockMvc mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

    private long idVanTestArtiest1() {
        return jdbcClient.sql("select id from artiesten where naam = 'test'").query(Long.class).single();
    }

    @Test
    void findByIdMetBestaandeIdVindtJuisteRecord() throws Exception {
        var id = idVanTestArtiest1();
        mockMvc.perform(get("/artiesten/{id}/albums", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()").value(JdbcTestUtils.countRowsInTableWhere(jdbcClient, ALBUMS_TABLE, "artiestId =" + id)),
                        jsonPath("[0].naam").value("test"),
                        jsonPath("[0].jaar").value(2000));
    }

    @Test
    void findByIdMetOnbestaandeIdVindtGeenRecord() throws Exception {
        mockMvc.perform(get("/artiesten/{id}/albums", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }
}