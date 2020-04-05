package io.redis.jedis.pocredis.service;

import io.redis.jedis.pocredis.model.Programmer;
import io.redis.jedis.pocredis.repository.ProgrammerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class ProgrammerService {

    private ProgrammerRepository programmerRepository;

    public ProgrammerService(ProgrammerRepository programmerRepository) {
        this.programmerRepository = programmerRepository;
    }

    public String setProgrammerAsString(Programmer programmer) {
        String idKey = getProgrammerKey(programmer);
        programmerRepository.setProgrammerAsString(idKey, programmer);
        return idKey;
    }

    public Optional<Programmer> getProgrammer(String idKey) {
        return programmerRepository.getProgrammer(idKey);
    }

    public void addProgrammerToList(Programmer programmer) {
        programmerRepository.addProgrammerToList(programmer);
    }

    public List<Programmer> getProgrammersList() {
        return programmerRepository.getProgrammersList();
    }

    public Long getSizeProgrammersList() {
        return programmerRepository.getSizeProgrammersList();
    }

    public Set<Programmer> getProgrammersSet() {
        return programmerRepository.getProgrammersSet();
    }

    public void addProgrammerToSet(Programmer programmer) {
        programmerRepository.addProgrammerToSet(programmer);
    }

    public Boolean isSetMember(Programmer programmer) {
        return programmerRepository.isSetMember(programmer);
    }

    public void addInProgrammerHash(Programmer programmer)  {
        String hashKey = getProgrammerKey(programmer);
        programmerRepository.saveInProgrammerHash(hashKey, programmer);
    }

    public void updateInProgrammerHash(Programmer programmer) {
        String hashKey = getProgrammerKey(programmer);
        programmerRepository.updateInProgrammerHash(hashKey, programmer);
    }

    public Map<String, Programmer> getProgrammerHash() {
        return programmerRepository.getProgrammerHash();
    }

    public Optional<Programmer> getProgrammerInHash(String hashKey) {
        return programmerRepository.getProgrammerInHash(hashKey);
    }

    public void deleteProgrammerInHash(String hashKey) {
        programmerRepository.deleteProgrammerInHash(hashKey);
    }

    private String getProgrammerKey(Programmer programmer) {
        return "programmer:" + programmer.getId();
    }
}
