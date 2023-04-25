package learn.field_agent.data;

import learn.field_agent.models.Alias;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AliasJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 4;

    @Autowired
    AliasJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindByName() {
        List<Alias> aliases = repository.findByName("Shadow");
        assertNotNull(aliases);
        assertEquals(1, aliases.size());

        aliases = repository.findByName("Unknown");
        assertEquals(0, aliases.size());
    }

    @Test
    void shouldAdd() {
        Alias alias = makeAlias();
        Alias actual = repository.add(alias);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getAliasId());
    }

    @Test
    void shouldUpdate() {
        Alias alias = new Alias();
        alias.setAliasId(1);
        alias.setName("Dark Shadow");
        alias.setAgentId(1);
        assertTrue(repository.update(alias));

        alias.setAliasId(NEXT_ID + 1);
        assertFalse(repository.update(alias));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(3));
    }

    private Alias makeAlias() {
        Alias alias = new Alias();
        alias.setName("Winny");
        alias.setAgentId(3);
        return alias;
    }
}