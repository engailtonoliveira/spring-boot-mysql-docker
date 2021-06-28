package br.com.agoliveira.springbootcommysql.controller;
import br.com.agoliveira.springbootcommysql.controller.dto.PessoaRq;
import br.com.agoliveira.springbootcommysql.controller.dto.PessoaRs;
import br.com.agoliveira.springbootcommysql.model.Pessoa;
import br.com.agoliveira.springbootcommysql.repository.PessoaCustomRepository;
import br.com.agoliveira.springbootcommysql.repository.PessoaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PessoaController {

    private final PessoaRepository pessoaRepository;
    private final PessoaCustomRepository pessoaCustomRepository;

    public PessoaController(PessoaRepository pessoaRepository, PessoaCustomRepository pessoaCustomRepository) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaCustomRepository= pessoaCustomRepository;
    }


    public List<PessoaRs> findAll() {
        var pessoas = pessoaRepository.findAll();
        return pessoas
                    .stream()
                    .map(PessoaRs::converter)
                    .collect(Collectors.toList());
    }

    public PessoaRs findById(Long id) {
        var pessoa = pessoaRepository.getById(id);
        return PessoaRs.converter(pessoa);
    }


    public void savePerson(PessoaRq pessoa) {
        var p = new Pessoa();
        p.setNome(pessoa.getNome());
        p.setSobrenome(pessoa.getSobrenome());

        pessoaRepository.save(p);
    }

    @PutMapping("/{id}")
    public void updatePerson(Long id, PessoaRq pessoa) throws Exception {
        var p = pessoaRepository.findById(id);

        if(p.isPresent()) {
            var pessoaSave = p.get();
            pessoaSave.setNome(pessoa.getNome());
            pessoaSave.setSobrenome(pessoa.getSobrenome());
            pessoaRepository.save(pessoaSave);
        } else {
            throw new Exception("PessoaService não encontrada!");
        }
    }

    public List<PessoaRs> findPersonByName(String name) {
            return this.pessoaRepository.findByNomeContains(name)
                    .stream()
                    .map(PessoaRs::converter)
                    .collect(Collectors.toList());
    }

    public List<PessoaRs> findPersonByCustom(Long id, String name, String sobrenome
    ) {
        return this.pessoaCustomRepository.find(id,name,sobrenome)
                .stream()
                .map(PessoaRs::converter)
                .collect(Collectors.toList());
    }
}
