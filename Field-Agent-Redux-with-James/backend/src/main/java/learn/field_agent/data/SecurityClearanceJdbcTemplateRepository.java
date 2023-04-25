package learn.field_agent.data;

import learn.field_agent.data.mappers.SecurityClearanceMapper;
import learn.field_agent.models.SecurityClearance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class SecurityClearanceJdbcTemplateRepository implements SecurityClearanceRepository {

    private final JdbcTemplate jdbcTemplate;

    public SecurityClearanceJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SecurityClearance> findAll() {
        final String sql = "select security_clearance_id, `name` security_clearance_name "
                + "from security_clearance;";
        return jdbcTemplate.query(sql, new SecurityClearanceMapper());
    }

    @Override
    public SecurityClearance findById(int securityClearanceId) {

        final String sql = "select security_clearance_id, `name` security_clearance_name "
                + "from security_clearance "
                + "where security_clearance_id = ?;";

        return jdbcTemplate.query(sql, new SecurityClearanceMapper(), securityClearanceId)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    public SecurityClearance findByName(String name) {

        final String sql = "select security_clearance_id, `name` security_clearance_name "
                + "from security_clearance "
                + "where `name` = ?;";

        return jdbcTemplate.query(sql, new SecurityClearanceMapper(), name)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    public SecurityClearance add(SecurityClearance securityClearance) {

        final String sql = "insert into security_clearance (`name`) "
                + " values (?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, securityClearance.getName());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        securityClearance.setSecurityClearanceId(keyHolder.getKey().intValue());
        return securityClearance;
    }

    @Override
    public boolean update(SecurityClearance securityClearance) {

        final String sql = "update security_clearance set "
                + "`name` = ? "
                + "where security_clearance_id = ?;";

        return jdbcTemplate.update(sql,
                securityClearance.getName(),
                securityClearance.getSecurityClearanceId()) > 0;
    }

    @Override
    public boolean deleteById(int securityClearanceId) {
        return jdbcTemplate.update(
                "delete from security_clearance where security_clearance_id = ?;",
                securityClearanceId) > 0;
    }

    @Override
    public int getUsageCount(int securityClearanceId) {
        return jdbcTemplate.queryForObject(
                "select count(*) from agency_agent where security_clearance_id = ?;", Integer.class, securityClearanceId);
    }
}
