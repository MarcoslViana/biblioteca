package marcos.ifpb.biblioteca.infraestrutura.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuração da camada de infraestrutura para JPA.
 * Habilita o escaneamento de repositórios e o gerenciamento de transações.
 */
@Configuration
@EnableJpaRepositories(basePackages = "marcos.ifpb.biblioteca.dominio.repositorio")
@EnableTransactionManagement
public class JpaConfig {
    // Spring Data JPA fornece automaticamente a implementação dos repositórios
    // definidos na camada de domínio
}

