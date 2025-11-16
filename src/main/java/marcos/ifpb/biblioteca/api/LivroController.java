package marcos.ifpb.biblioteca.api;

import jakarta.validation.Valid;
import marcos.ifpb.biblioteca.aplicacao.LivroService;
import marcos.ifpb.biblioteca.dominio.modelo.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/livros")
public class LivroController {

    @Autowired
    private LivroService service;

    @PostMapping
    public ResponseEntity<Livro> criar(@Valid @RequestBody Livro livro) {
        Livro livroCriado = service.criar(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroCriado);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        Livro livro = service.buscarPorId(id);
        return ResponseEntity.ok().body(livro);
    }

    @GetMapping(value = "/isbn/{isbn}")
    public ResponseEntity<Livro> buscarPorIsbn(@PathVariable Long isbn) {
        Livro livro = service.buscarPorIsbn(isbn);
        return ResponseEntity.ok().body(livro);
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarTodos() {
        List<Livro> livros = service.listarTodos();
        return ResponseEntity.ok().body(livros);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @Valid @RequestBody Livro livro) {
        Livro livroAtualizado = service.atualizar(id, livro);
        return ResponseEntity.ok().body(livroAtualizado);
    }
}

