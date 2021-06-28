package br.com.agoliveira.springbootcommysql.service;

import br.com.agoliveira.springbootcommysql.controller.PessoaController;
import br.com.agoliveira.springbootcommysql.controller.dto.PessoaRq;
import br.com.agoliveira.springbootcommysql.controller.dto.PessoaRs;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RequestMapping("/pessoa")
@RestController
public class PessoaService {

    private final PessoaController controller;

    @Inject
    public PessoaService(final PessoaController controller) {
        this.controller = controller;
    }

    @GetMapping("/")
    public List<PessoaRs> findAll() {
        return controller.findAll();
    }

    @GetMapping("/{id}")
    public PessoaRs findById(@PathVariable("id") Long id) {
        return controller.findById(id);
    }

    @PostMapping("/")
    public void savePerson(@RequestBody PessoaRq pessoa) {
        controller.savePerson(pessoa);
    }

    @PutMapping("/{id}")
    public void updatePerson(@PathVariable("id") Long id, @RequestBody PessoaRq pessoa) throws Exception {
        controller.updatePerson(id, pessoa);
    }

    @GetMapping("/filter")
    public List<PessoaRs> findPersonByName(@RequestParam("name") String name) {
        return controller.findPersonByName(name);
    }

    @GetMapping("/filter/custom")
    public List<PessoaRs> findPersonByCustom(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "sobrenome", required = false) String sobrenome
    ) {
        return controller.findPersonByCustom(id, name, sobrenome);
    }



}
