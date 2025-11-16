package marcos.ifpb.biblioteca.dominio.repositorio;

import marcos.ifpb.biblioteca.dominio.modelo.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByIsbn(Long isbn);
    boolean existsByIsbn(Long isbn);
}

