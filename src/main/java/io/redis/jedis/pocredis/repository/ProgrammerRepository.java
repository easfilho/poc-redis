package io.redis.jedis.pocredis.repository;

import com.google.gson.Gson;
import io.redis.jedis.pocredis.model.Programmer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ProgrammerRepository {

    private static final String PROGRAMMER_LIST = "programmer_list";
    private static final String PROGRAMMER_SET = "programmer_set";
    private static final String PROGRAMMER_HASH = "programmer_hash";
    private static final int TIMEOUT = 25;

    private RedisTemplate<String, Serializable> redisTemplate;

    public ProgrammerRepository(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setProgrammerAsString(String idKey, Programmer programmer) {
        redisTemplate.opsForValue().set(idKey, toJson(programmer), Duration.ofSeconds(TIMEOUT));
    }

    public Optional<Programmer> getProgrammer(String idKey) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(idKey))
                .map(programmer -> fromJson(programmer.toString()));
    }

    public void addProgrammerToList(Programmer programmer) {
        redisTemplate.opsForList().leftPush(PROGRAMMER_LIST, programmer);
    }

    public List<Programmer> getProgrammersList() {
        return Optional.ofNullable(redisTemplate.opsForList().range(PROGRAMMER_LIST, 0, -1))
                .map(listProgrammer -> listProgrammer
                        .stream()
                        .map(programmer  -> (Programmer)programmer)
                        .collect(Collectors.toList())
                )
                .orElse(new ArrayList<>());
    }

    public Long getSizeProgrammersList() {
        return redisTemplate.opsForList().size(PROGRAMMER_LIST);
    }

    public void addProgrammerToSet(Programmer... programmers) {
        redisTemplate.opsForSet().add(PROGRAMMER_SET, programmers);
    }

    public Set<Programmer> getProgrammersSet() {
        return Optional.ofNullable(redisTemplate.opsForSet().members(PROGRAMMER_SET))
                .map(listProgrammer -> listProgrammer
                        .stream()
                        .map(programmer  -> (Programmer)programmer)
                        .collect(Collectors.toSet())
                )
                .orElse(new HashSet<>());
    }

    public Boolean isSetMember(Programmer programmer) {
        return redisTemplate.opsForSet().isMember(PROGRAMMER_SET, programmer);
    }

    public void saveInProgrammerHash(String hashKey, Programmer programmer) {
        redisTemplate.opsForHash().put(PROGRAMMER_HASH, hashKey, toJson(programmer));
    }

    public void updateInProgrammerHash(String hashKey, Programmer programmer) {
        redisTemplate.opsForHash().put(PROGRAMMER_HASH, hashKey, toJson(programmer));
    }

    public Optional<Programmer> getProgrammerInHash(String hashKey) {
        return Optional.ofNullable(redisTemplate.opsForHash().get(PROGRAMMER_HASH, hashKey))
                .map(programmer -> fromJson(programmer.toString()));
    }

    public void deleteProgrammerInHash(String hashKey) {
        redisTemplate.opsForHash().delete(PROGRAMMER_HASH, hashKey);
    }

    public Map<String, Programmer> getProgrammerHash() {
        return redisTemplate.opsForHash().entries(PROGRAMMER_HASH)
                .entrySet()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(entry -> entry.getKey().toString(),
                                          entry -> fromJson(entry.getValue().toString())));
    }

    private String toJson(Programmer programmer) {
        return new Gson().toJson(programmer);
    }

    private Programmer fromJson(String json) {
        return new Gson().fromJson(json, Programmer.class);
    }
}
