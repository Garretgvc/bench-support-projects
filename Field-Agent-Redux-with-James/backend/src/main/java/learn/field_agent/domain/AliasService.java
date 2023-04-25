package learn.field_agent.domain;

import learn.field_agent.data.AgentRepository;
import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Alias;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AliasService {

    private final AliasRepository repository;
    private final AgentRepository agentRepository;

    public AliasService(AliasRepository repository, AgentRepository agentRepository) {
        this.repository = repository;
        this.agentRepository = agentRepository;
    }

    public Result<Alias> add(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() != 0) {
            result.addMessage("aliasId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        alias = repository.add(alias);
        result.setPayload(alias);
        return result;
    }

    public Result<Alias> update(Alias alias) {
        Result<Alias> result = validate(alias);
        if (!result.isSuccess()) {
            return result;
        }

        if (alias.getAliasId() <= 0) {
            result.addMessage("aliasId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(alias)) {
            String msg = String.format("aliasId: %s, not found",
                    alias.getAliasId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int aliasId) {
        return repository.deleteById(aliasId);
    }

    private Result<Alias> validate(Alias alias) {
        Result<Alias> result = new Result<>();

        if (alias == null) {
            result.addMessage("alias cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(alias.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        if (alias.getAgentId() == 0) {
            result.addMessage("agentId is required", ResultType.INVALID);
        } else if (agentRepository.findById(alias.getAgentId()) == null) {
            result.addMessage("agent must exist", ResultType.INVALID);
        }

        List<Alias> existingAliases = repository.findByName(alias.getName());

        // Filter out the alias ID that we're validating.
        existingAliases = existingAliases.stream()
                .filter(a -> a.getAliasId() != alias.getAliasId())
                .collect(Collectors.toList());

        if (existingAliases.size() > 0) {
            if (Validations.isNullOrBlank(alias.getPersona())) {
                result.addMessage("persona is required if the alias name is already in use", ResultType.INVALID);
            } else if (existingAliases.stream().anyMatch(a -> a.getPersona() != null
                    && a.getPersona().equalsIgnoreCase(alias.getPersona()))) {
                result.addMessage("persona must be unique if the alias name and person are already in use", ResultType.INVALID);
            }
        }

        return result;
    }
}
