package marcos.ifpb.biblioteca.dominio.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_livro", uniqueConstraints = @UniqueConstraint(columnNames = "isbn"))
public class Livro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "ISBN é obrigatório")
    @Column(unique = true, nullable = false)
    private Long isbn;
    
    @NotBlank(message = "Título é obrigatório")
    @Column(nullable = false)
    private String titulo;
    
    @NotBlank(message = "Autor é obrigatório")
    @Column(nullable = false)
    private String autor;
    
    @NotNull(message = "Ano de publicação é obrigatório")
    @Positive(message = "Ano de publicação deve ser positivo")
    @Column(nullable = false)
    private Integer anoDePublicacao;
    
    @NotNull(message = "Quantidade em estoque é obrigatória")
    @Positive(message = "Quantidade em estoque deve ser positiva")
    @Column(nullable = false)
    private Integer quantidadeEstoque;
}

