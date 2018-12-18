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
public class AuthorRestController {
  private final AuthorRepository authRepository;

  @Autowired
  public AuthorRestController(AuthorRepository authRepository) {
    this.authRepository = authRepository;
  }

  @CrossOrigin(origins = "*")
  @GetMapping(value = "/authors", produces = "application/json")
  // READ points:
  public List<Author> suggestAuth(@RequestParam(value = "s", defaultValue = "") String sampleName) {
    List<Author> sample = null;
    if (sampleName.length() >= 1) {
      sample = authRepository.findAllByNameContaining(sampleName);
    }
    return sample;
  }
}
