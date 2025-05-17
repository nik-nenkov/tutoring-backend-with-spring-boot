package blib.controller;

import blib.dao.AuthorRepository;
import blib.model.Author;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorsController {
  private final AuthorRepository authRepository;

  @Autowired
  public AuthorsController(AuthorRepository authRepository) {
    this.authRepository = authRepository;
  }

  @CrossOrigin(origins = "*")
  @GetMapping(value = "/authors", produces = "application/json")
  public List<Author> suggestAuth(@RequestParam(value = "s", defaultValue = "") String sampleName) {
    List<Author> sample = null;
    if (!sampleName.isEmpty()) {
      sample = authRepository.findAllByNameContaining(sampleName);
    }
    return sample;
  }
}
