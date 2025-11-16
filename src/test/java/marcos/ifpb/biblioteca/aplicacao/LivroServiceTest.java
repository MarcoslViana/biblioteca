package marcos.ifpb.biblioteca.aplicacao;

import marcos.ifpb.biblioteca.dominio.excecao.IsbnJaExistenteException;
import marcos.ifpb.biblioteca.dominio.excecao.LivroNaoEncontradoException;
import marcos.ifpb.biblioteca.dominio.modelo.Livro;
import marcos.ifpb.biblioteca.dominio.repositorio.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private LivroRepository repository;

    @InjectMocks
    private LivroService service;

    private Livro livro;

    @BeforeEach
    void setUp() {
        livro = new Livro();
        livro.setId(1L);
        livro.setIsbn(1234567890L);
        livro.setTitulo("Dom Casmurro");
        livro.setAutor("Machado de Assis");
        livro.setAnoDePublicacao(1899);
        livro.setQuantidadeEstoque(10);
    }

    @Test
    void testCriar_LivroValido_DeveRetornarLivroCriado() {
        // Arrange
        when(repository.existsByIsbn(livro.getIsbn())).thenReturn(false);
        when(repository.save(any(Livro.class))).thenReturn(livro);

        // Act
        Livro resultado = service.criar(livro);

        // Assert
        assertNotNull(resultado);
        assertEquals(livro.getIsbn(), resultado.getIsbn());
        assertEquals(livro.getTitulo(), resultado.getTitulo());
        verify(repository, times(1)).existsByIsbn(livro.getIsbn());
        verify(repository, times(1)).save(livro);
    }

    @Test
    void testCriar_IsbnJaExistente_DeveLancarExcecao() {
        // Arrange
        when(repository.existsByIsbn(livro.getIsbn())).thenReturn(true);

        // Act & Assert
        IsbnJaExistenteException excecao = assertThrows(IsbnJaExistenteException.class, () -> {
            service.criar(livro);
        });

        assertEquals("ISBN " + livro.getIsbn() + " já está cadastrado", excecao.getMessage());
        verify(repository, times(1)).existsByIsbn(livro.getIsbn());
        verify(repository, never()).save(any(Livro.class));
    }

    @Test
    void testBuscarPorId_LivroExistente_DeveRetornarLivro() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(livro));

        // Act
        Livro resultado = service.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(livro.getId(), resultado.getId());
        assertEquals(livro.getTitulo(), resultado.getTitulo());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testBuscarPorId_LivroNaoExistente_DeveLancarExcecao() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        LivroNaoEncontradoException excecao = assertThrows(LivroNaoEncontradoException.class, () -> {
            service.buscarPorId(999L);
        });

        assertEquals("Livro com ID 999 não encontrado", excecao.getMessage());
        verify(repository, times(1)).findById(999L);
    }

    @Test
    void testBuscarPorIsbn_LivroExistente_DeveRetornarLivro() {
        // Arrange
        when(repository.findByIsbn(1234567890L)).thenReturn(Optional.of(livro));

        // Act
        Livro resultado = service.buscarPorIsbn(1234567890L);

        // Assert
        assertNotNull(resultado);
        assertEquals(livro.getIsbn(), resultado.getIsbn());
        verify(repository, times(1)).findByIsbn(1234567890L);
    }

    @Test
    void testBuscarPorIsbn_LivroNaoExistente_DeveLancarExcecao() {
        // Arrange
        when(repository.findByIsbn(9999999999L)).thenReturn(Optional.empty());

        // Act & Assert
        LivroNaoEncontradoException excecao = assertThrows(LivroNaoEncontradoException.class, () -> {
            service.buscarPorIsbn(9999999999L);
        });

        assertEquals("Livro com ISBN 9999999999 não encontrado", excecao.getMessage());
        verify(repository, times(1)).findByIsbn(9999999999L);
    }

    @Test
    void testListarTodos_DeveRetornarListaDeLivros() {
        // Arrange
        Livro livro2 = new Livro();
        livro2.setId(2L);
        livro2.setIsbn(9876543210L);
        livro2.setTitulo("O Guarani");
        livro2.setAutor("José de Alencar");
        livro2.setAnoDePublicacao(1857);
        livro2.setQuantidadeEstoque(5);

        List<Livro> livros = Arrays.asList(livro, livro2);
        when(repository.findAll()).thenReturn(livros);

        // Act
        List<Livro> resultado = service.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testAtualizar_LivroExistente_DeveRetornarLivroAtualizado() {
        // Arrange
        Livro livroAtualizado = new Livro();
        livroAtualizado.setIsbn(1234567890L);
        livroAtualizado.setTitulo("Dom Casmurro - Edição Especial");
        livroAtualizado.setAutor("Machado de Assis");
        livroAtualizado.setAnoDePublicacao(1899);
        livroAtualizado.setQuantidadeEstoque(15);

        when(repository.findById(1L)).thenReturn(Optional.of(livro));
        when(repository.existsByIsbn(1234567890L)).thenReturn(true);
        when(repository.save(any(Livro.class))).thenReturn(livro);

        // Act
        Livro resultado = service.atualizar(1L, livroAtualizado);

        // Assert
        assertNotNull(resultado);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Livro.class));
    }

    @Test
    void testAtualizar_LivroNaoExistente_DeveLancarExcecao() {
        // Arrange
        Livro livroAtualizado = new Livro();
        livroAtualizado.setIsbn(1234567890L);
        livroAtualizado.setTitulo("Novo Título");

        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        LivroNaoEncontradoException excecao = assertThrows(LivroNaoEncontradoException.class, () -> {
            service.atualizar(999L, livroAtualizado);
        });

        assertEquals("Livro com ID 999 não encontrado", excecao.getMessage());
        verify(repository, times(1)).findById(999L);
        verify(repository, never()).save(any(Livro.class));
    }

    @Test
    void testAtualizar_IsbnAlteradoParaJaExistente_DeveLancarExcecao() {
        // Arrange
        Livro livroAtualizado = new Livro();
        livroAtualizado.setIsbn(9999999999L);
        livroAtualizado.setTitulo("Novo Título");

        when(repository.findById(1L)).thenReturn(Optional.of(livro));
        when(repository.existsByIsbn(9999999999L)).thenReturn(true);

        // Act & Assert
        IsbnJaExistenteException excecao = assertThrows(IsbnJaExistenteException.class, () -> {
            service.atualizar(1L, livroAtualizado);
        });

        assertEquals("ISBN 9999999999 já está cadastrado", excecao.getMessage());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).existsByIsbn(9999999999L);
        verify(repository, never()).save(any(Livro.class));
    }

    @Test
    void testRemover_LivroExistente_DeveRemoverComSucesso() {
        // Arrange
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        // Act
        service.remover(1L);

        // Assert
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testRemover_LivroNaoExistente_DeveLancarExcecao() {
        // Arrange
        when(repository.existsById(999L)).thenReturn(false);

        // Act & Assert
        LivroNaoEncontradoException excecao = assertThrows(LivroNaoEncontradoException.class, () -> {
            service.remover(999L);
        });

        assertEquals("Livro com ID 999 não encontrado", excecao.getMessage());
        verify(repository, times(1)).existsById(999L);
        verify(repository, never()).deleteById(anyLong());
    }
}

