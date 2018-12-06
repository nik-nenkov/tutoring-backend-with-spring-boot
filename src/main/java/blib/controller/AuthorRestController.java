package blib.controller;

import blib.dao.AuthorRepository;
import blib.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class AuthorRestController {
    private final AuthorRepository authRepository;

    @Autowired
    public AuthorRestController(AuthorRepository authRepository) {
        this.authRepository = authRepository;
    }

    @RequestMapping(
            value = "/authors",
            produces = "application/json")
    //READ points:
    public List<Author> suggestAuth(@RequestParam(value = "s", defaultValue = "") String sampleName) {
        List<Author> sample = null;
        if (sampleName.length() >= 1) {
            sample = authRepository.findAllByNameContaining(sampleName);
        }
        return sample;
    }


}
