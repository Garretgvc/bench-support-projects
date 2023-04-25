package learn.field_agent.domain;

import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityClearanceServiceTest {

    @Autowired
    SecurityClearanceService service;

    @MockBean
    SecurityClearanceRepository repository;

    @Test
    void shouldFindAll() {
        List<SecurityClearance> expected = List.of(
          new SecurityClearance(1, "Secret"),
          new SecurityClearance(2, "Top Secret")
        );
        when(repository.findAll()).thenReturn(expected);
        List<SecurityClearance> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() {
        SecurityClearance expected = makeSecurityClearance();
        when(repository.findById(1)).thenReturn(expected);
        SecurityClearance actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddWhenInvalid() {
        // Should not add `null`.
        Result<SecurityClearance> result = service.add(null);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if securityClearanceId is greater than 0.
        SecurityClearance securityClearance = makeSecurityClearance();
        result = service.add(securityClearance);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if name is null.
        securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(0);
        securityClearance.setName(null);
        result = service.add(securityClearance);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if name is empty.
        securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(0);
        securityClearance.setName("");
        result = service.add(securityClearance);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if duplicate.
        SecurityClearance secret = makeSecurityClearance();
        when(repository.findByName("Secret")).thenReturn(secret);
        securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(0);
        securityClearance.setName("Secret");
        result = service.add(securityClearance);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldAddWhenValid() {
        SecurityClearance expected = makeSecurityClearance();
        SecurityClearance arg = makeSecurityClearance();
        arg.setSecurityClearanceId(0);

        when(repository.add(arg)).thenReturn(expected);
        Result<SecurityClearance> result = service.add(arg);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        // Should not update `null`.
        Result<SecurityClearance> result = service.update(null);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if securityClearanceId is 0.
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(0);
        result = service.update(securityClearance);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if name is null.
        securityClearance = makeSecurityClearance();
        securityClearance.setName(null);
        result = service.update(securityClearance);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if name is empty.
        securityClearance = makeSecurityClearance();
        securityClearance.setName("");
        result = service.update(securityClearance);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if duplicate.
        SecurityClearance secret = makeSecurityClearance();
        secret.setSecurityClearanceId(1);
        when(repository.findByName("Secret")).thenReturn(secret);
        securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(3);
        securityClearance.setName("Secret");
        result = service.update(securityClearance);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if not found.
        securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(3);
        securityClearance.setName("Extreme Secret");
        // Mocking the repository update method to return false is not strictly necessary
        // as the default mock implementation for that method will return false.
        // Adding here for clarity on what our expectations are.
        when(repository.update(securityClearance)).thenReturn(false);
        result = service.update(securityClearance);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }

    @Test
    void shouldUpdateWhenValid() {
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setName("Extreme Secret");

        when(repository.update(securityClearance)).thenReturn(true);
        Result<SecurityClearance> result = service.update(securityClearance);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteWhenInUse() {
        when(repository.getUsageCount(1)).thenReturn(1);
        Result<SecurityClearance> result = service.deleteById(1);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotDeleteWhenNotFound() {
        // Mocking the repository deleteById method to return false is not strictly necessary
        // as the default mock implementation for that method will return false.
        // Adding here for clarity on what our expectations are.
        when(repository.deleteById(3)).thenReturn(false);
        Result<SecurityClearance> result = service.deleteById(3);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }

    @Test
    void shouldDeleteWhenNotInUse() {
        when(repository.getUsageCount(2)).thenReturn(0);
        when(repository.deleteById(2)).thenReturn(true);
        Result<SecurityClearance> result = service.deleteById(2);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    private SecurityClearance makeSecurityClearance() {
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setSecurityClearanceId(1);
        securityClearance.setName("Secret");
        return securityClearance;
    }
}