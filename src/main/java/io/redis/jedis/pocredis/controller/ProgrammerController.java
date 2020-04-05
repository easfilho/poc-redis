package io.redis.jedis.pocredis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.redis.jedis.pocredis.model.Programmer;
import io.redis.jedis.pocredis.service.ProgrammerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class ProgrammerController {

    private ProgrammerService programmerService;

    public ProgrammerController(ProgrammerService programmerService) {
        this.programmerService = programmerService;
    }

    @PostMapping(value = "/programmers-string")
    public ResponseEntity<String> save(@RequestBody Programmer programmer) throws JsonProcessingException {
        String idKey = programmerService.setProgrammerAsString(programmer);
        return ResponseEntity.ok(idKey);
    }

    @GetMapping(value = "/programmers-string/{id}")
    public ResponseEntity<Programmer> consult(@PathVariable String id) {
        return programmerService.getProgrammer(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping(value = "/programmers-list")
    public ResponseEntity<Void> saveInProgrammersList(@RequestBody Programmer programmer) throws JsonProcessingException {
        programmerService.addProgrammerToList(programmer);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/programmers-list")
    public ResponseEntity<List<Programmer>> getProgrammersList(@RequestBody Programmer programmer) throws JsonProcessingException {
        List<Programmer> programmersList = programmerService.getProgrammersList();
        return ResponseEntity.ok(programmersList);
    }

    @GetMapping(value = "/programmers-list/size")
    public ResponseEntity<Long> getSizeProgrammersList(@RequestBody Programmer programmer) throws JsonProcessingException {
        Long size = programmerService.getSizeProgrammersList();
        return ResponseEntity.ok(size);
    }

    @PostMapping(value = "/programmers-set")
    public ResponseEntity<Void> saveInProgrammerSet(@RequestBody Programmer programmer) {
        programmerService.addProgrammerToSet(programmer);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/programmers-set")
    public ResponseEntity<Set<Programmer>> getProgrammersSet() {
        Set<Programmer> programmersSet = programmerService.getProgrammersSet();
        return ResponseEntity.ok(programmersSet);
    }

    @PostMapping(value = "/programmers-set/member")
    public ResponseEntity<Boolean> isSetMember(@RequestBody Programmer programmer) {
        Boolean setMember = programmerService.isSetMember(programmer);
        return ResponseEntity.ok(setMember);
    }

    @PostMapping(value = "/programmers-hash")
    public ResponseEntity<Boolean> addInProgrammerHash(@RequestBody Programmer programmer) {
        programmerService.addInProgrammerHash(programmer);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/programmers-hash")
    public ResponseEntity<Boolean> updateInProgrammerHash(@RequestBody Programmer programmer) {
        programmerService.updateInProgrammerHash(programmer);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/programmers-hash/{id}")
    public ResponseEntity<Programmer> getInProgrammerHash(@PathVariable String id) {
        return programmerService.getProgrammerInHash(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping(value = "/programmers-hash/{id}")
    public ResponseEntity<Void> deleteProgrammerInHash(@PathVariable String id) {
        programmerService.deleteProgrammerInHash(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/programmers-hash")
    public ResponseEntity<Map<String, Programmer>> getProgrammerHash() {
        Map<String, Programmer> programmerHash = programmerService.getProgrammerHash();
        return ResponseEntity.ok(programmerHash);
    }
}
