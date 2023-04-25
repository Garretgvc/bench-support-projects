package learn.field_agent.domain;

import learn.field_agent.data.AgentRepository;
import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AliasServiceTest {
    @Autowired
    AliasService service;

    @MockBean
    AliasRepository repository;

    @MockBean
    AgentRepository agentRepository;

    @Test
    void shouldNotAddWhenInvalid() {
        // Should not add `null`.
        Result<Alias> result = service.add(null);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if agent doesn't exist.
        Alias alias = makeAlias();
        alias.setAliasId(0);
        // Mocking the agent repository findById method to return null is not strictly necessary
        // as the default mock implementation for that method will return null.
        // Adding here for clarity on what our expectations are.
        when(agentRepository.findById(1)).thenReturn(null);
        result = service.add(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Make sure that an agent is found by default for the remaining test cases.
        Agent agent = new Agent();
        when(agentRepository.findById(1)).thenReturn(agent);

        // Should not add if aliasId is greater than 0.
        alias = makeAlias();
        result = service.add(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if name is null.
        alias = makeAlias();
        alias.setAliasId(0);
        alias.setName(null);
        result = service.add(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if name is empty.
        alias = makeAlias();
        alias.setAliasId(0);
        alias.setName("");
        result = service.add(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if agent ID is not set.
        alias = makeAlias();
        alias.setAliasId(0);
        alias.setAgentId(0);
        result = service.add(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Make sure that an existing alias with the same name is found.
        Alias shadow = makeAlias();
        when(repository.findByName("Shadow")).thenReturn(List.of(shadow));

        // Should not add if a duplicate alias name is found and the persona is null.
        alias = makeAlias();
        alias.setAliasId(0);
        result = service.add(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if a duplicate alias name is found and the persona is empty.
        alias = makeAlias();
        alias.setAliasId(0);
        alias.setPersona("");
        result = service.add(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if a duplicate alias name is found and the persona is not unique.
        shadow.setPersona("A shadow in the night.");
        alias = makeAlias();
        alias.setAliasId(0);
        alias.setPersona("A shadow in the night.");
        result = service.add(alias);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldAddWhenValid() {
        // Make sure that an agent is found for all happy path test cases.
        Agent agent = new Agent();
        when(agentRepository.findById(1)).thenReturn(agent);

        // Should add.

        Alias expected = makeAlias();
        Alias arg = makeAlias();
        arg.setAliasId(0);

        when(repository.add(arg)).thenReturn(expected);
        Result<Alias> result = service.add(arg);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(expected, result.getPayload());

        // Should add even if an alias with a duplicate name is found
        // provided that a person is set and unique.

        // Make sure that an existing alias with the same name is found.
        Alias shadow = makeAlias();
        when(repository.findByName("Shadow")).thenReturn(List.of(shadow));

        expected = makeAlias();
        arg = makeAlias();
        arg.setAliasId(0);
        arg.setPersona("A shadow in the night.");

        when(repository.add(arg)).thenReturn(expected);
        result = service.add(arg);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        // Should not update `null`.
        Result<Alias> result = service.update(null);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if agent doesn't exist.
        Alias alias = makeAlias();
        // Mocking the agent repository findById method to return null is not strictly necessary
        // as the default mock implementation for that method will return null.
        // Adding here for clarity on what our expectations are.
        when(agentRepository.findById(1)).thenReturn(null);
        result = service.update(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Make sure that an agent is found by default for the remaining test cases.
        Agent agent = new Agent();
        when(agentRepository.findById(1)).thenReturn(agent);

        // Should not update if name is null.
        alias = makeAlias();
        alias.setName(null);
        result = service.update(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if name is empty.
        alias = makeAlias();
        alias.setName("");
        result = service.update(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if agent ID is not set.
        alias = makeAlias();
        alias.setAgentId(0);
        result = service.update(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if aliasId is set to 0.
        alias = makeAlias();
        alias.setAliasId(0);
        result = service.update(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Make sure that an existing alias with the same name is found.
        Alias shadow = makeAlias();
        when(repository.findByName("Shadow")).thenReturn(List.of(shadow));

        // Should not update if a duplicate alias name is found and the persona is null.
        alias = makeAlias();
        alias.setAliasId(4);
        result = service.update(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if a duplicate alias name is found and the persona is empty.
        alias = makeAlias();
        alias.setAliasId(4);
        alias.setPersona("");
        result = service.update(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if a duplicate alias name is found and the persona is not unique.
        shadow.setPersona("A shadow in the night.");
        alias = makeAlias();
        alias.setAliasId(4);
        alias.setPersona("A shadow in the night.");
        result = service.update(alias);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if not found.
        alias = makeAlias();
        alias.setAliasId(4);
        alias.setPersona("The darkest shadow in the night.");
        // Mocking the repository update method to return false is not strictly necessary
        // as the default mock implementation for that method will return false.
        // Adding here for clarity on what our expectations are.
        when(repository.update(alias)).thenReturn(false);
        result = service.update(alias);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }

    @Test
    void shouldUpdateWhenValid() {
        // Make sure that an agent is found for all happy path test cases.
        Agent agent = new Agent();
        when(agentRepository.findById(1)).thenReturn(agent);

        // Should update.

        Alias alias = makeAlias();
        alias.setName("Dark Shadow");

        when(repository.update(alias)).thenReturn(true);
        Result<Alias> result = service.update(alias);
        assertEquals(ResultType.SUCCESS, result.getType());

        // Should update even if an alias with a duplicate name is found
        // provided that a person is set and unique.

        // Make sure that an existing alias with the same name is found.
        Alias shadow = makeAlias();
        when(repository.findByName("Shadow")).thenReturn(List.of(shadow));

        alias = makeAlias();
        alias.setAliasId(4);
        alias.setPersona("A shadow in the night.");

        when(repository.update(alias)).thenReturn(true);
        result = service.update(alias);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteWhenNotFound() {
        // Mocking the repository deleteById method to return false is not strictly necessary
        // as the default mock implementation for that method will return false.
        // Adding here for clarity on what our expectations are.
        when(repository.deleteById(4)).thenReturn(false);
        assertFalse(service.deleteById(4));
    }

    @Test
    void shouldDelete() {
        when(repository.deleteById(1)).thenReturn(true);
        assertTrue(service.deleteById(1));
    }

    private Alias makeAlias() {
        Alias alias = new Alias();
        alias.setAliasId(1);
        alias.setName("Shadow");
        alias.setAgentId(1);
        return alias;
    }
}