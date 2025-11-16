package marcos.ifpb.biblioteca.aplicacao;

import marcos.ifpb.biblioteca.dominio.excecao.IsbnJaExistenteException;
import marcos.ifpb.biblioteca.dominio.excecao.LivroNaoEncontradoException;
import marcos.ifpb.biblioteca.dominio.modelo.Livro;
import marcos.ifpb.biblioteca.dominio.repositorio.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    
    @Autowired
    private LivroRepository repository;

    @Transactional
    public Livro criar(Livro livro) {
        // Validação de negócio: ISBN deve ser único
        if (repository.existsByIsbn(livro.getIsbn())) {
            throw new IsbnJaExistenteException("ISBN " + livro.getIsbn() + " já está cadastrado");
        }
        return repository.save(livro);
    }

    @Transactional
    public void remover(Long id) {
        // Validação: verificar se o livro existe antes de remover
        if (!repository.existsById(id)) {
            throw new LivroNaoEncontradoException("Livro com ID " + id + " não encontrado");
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Livro buscarPorId(Long id) {
        Optional<Livro> livro = repository.findById(id);
        if (livro.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro com ID " + id + " não encontrado");
        }
        return livro.get();
    }

    @Transactional(readOnly = true)
    public Livro buscarPorIsbn(Long isbn) {
        Optional<Livro> livro = repository.findByIsbn(isbn);
        if (livro.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro com ISBN " + isbn + " não encontrado");
        }
        return livro.get();
    }

    @Transactional(readOnly = true)
    public List<Livro> listarTodos() {
        return repository.findAll();
    }

    @Transactional
    public Livro atualizar(Long id, Livro livroAtualizado) {
        // Buscar livro existente
        Livro livroExistente = buscarPorId(id);
        
        // Validação: não permitir alterar ISBN para um já existente (exceto se for o mesmo livro)
        if (!livroExistente.getIsbn().equals(livroAtualizado.getIsbn())) {
            if (repository.existsByIsbn(livroAtualizado.getIsbn())) {
                throw new IsbnJaExistenteException("ISBN " + livroAtualizado.getIsbn() + " já está cadastrado");
            }
        }
        
        // Atualizar campos
        livroExistente.setTitulo(livroAtualizado.getTitulo());
        livroExistente.setAutor(livroAtualizado.getAutor());
        livroExistente.setIsbn(livroAtualizado.getIsbn());
        livroExistente.setAnoDePublicacao(livroAtualizado.getAnoDePublicacao());
        livroExistente.setQuantidadeEstoque(livroAtualizado.getQuantidadeEstoque());
        
        return repository.save(livroExistente);
    }
}

