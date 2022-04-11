package br.com.agricolab.controller;

import br.com.agricolab.core.produtor.processors.ProdutorProcessor;
import br.com.agricolab.core.produtor.dto.ProdutorRequestDto;
import br.com.agricolab.core.produtor.mapper.ProdutorRequestDtoMapper;
import br.com.agricolab.domain.Produtor;
import br.com.agricolab.domain.Produto;
import br.com.agricolab.repository.adapter.ProdutorRepository;
import br.com.agricolab.repository.mapper.ProdutorEntityMapper;
import br.com.agricolab.repository.model.ProdutorEntity;
import br.com.agricolab.service.ProdutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtor")
public class ProdutorController {

    @Autowired
    private ProdutorRepository produtorRepository;

    @Autowired
    private ProdutorProcessor produtorProcessor;

    @Autowired
    ProdutorService produtorService;

    ProdutorController(ProdutorRepository produtorRepository, ProdutorProcessor produtorProcessor) {
        this.produtorRepository = produtorRepository;
        this.produtorProcessor = produtorProcessor;
    }

    @GetMapping(path = "/all")
    public List findAll(){
        return produtorRepository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ProdutorRequestDto findById(@PathVariable Integer id){
        Optional<ProdutorEntity> produtorEntity = produtorRepository.findById(id);

        if(produtorEntity.isPresent()){
            Produtor produtor = ProdutorEntityMapper.INSTANCE.produtorToEntity(produtorEntity.get());
            return ProdutorRequestDtoMapper.INSTANCE.produtorToDto(produtor);
        }
        return new ProdutorRequestDto();
    }

    @PostMapping
    public ProdutorRequestDto create(@RequestBody ProdutorRequestDto produtorResquest){

        Produtor produtor = ProdutorRequestDtoMapper.INSTANCE.produtorToDto(produtorResquest);

        produtor = produtorProcessor.createProdutor(produtor);

        return ProdutorRequestDtoMapper.INSTANCE.produtorToDto(produtor);
    }

    @PatchMapping("/{id}")
    ProdutorRequestDto modificaProdutor(@RequestBody ProdutorRequestDto novoProdutor, @PathVariable Integer id) {

        Produtor produtorRequest = ProdutorRequestDtoMapper.INSTANCE.produtorToDto(novoProdutor);

        Produtor produtor = produtorProcessor.modificaProdutor(produtorRequest,id);

        return ProdutorRequestDtoMapper.INSTANCE.produtorToDto(produtor);
    }

    @DeleteMapping("/{id}")
    void deleteProdutor(@PathVariable Integer id) {
        produtorRepository.deleteById(id);
    }


    @PostMapping("/cadastro/produto/{id}")
    public ResponseEntity<ProdutorEntity> cadastroProdutos(@RequestBody Produto produtos, @PathVariable Integer id){
        return ResponseEntity.ok(produtorService.cadastro(id, produtos));

    }

    @DeleteMapping("/delete/produto/{id}")
    public ResponseEntity<Void> deleteProdutos(@PathVariable Integer id){
        produtorService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/alterar/produto/{id}")
    public ResponseEntity<Void> alterarProduto(@RequestBody Produto produtoNovo, @PathVariable Integer id){
        produtorService.replace(produtoNovo, id);
        return ResponseEntity.noContent().build();

    }


}

