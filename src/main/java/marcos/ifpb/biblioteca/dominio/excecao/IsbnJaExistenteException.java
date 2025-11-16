package marcos.ifpb.biblioteca.dominio.excecao;

public class IsbnJaExistenteException extends RuntimeException {
    public IsbnJaExistenteException(String mensagem) {
        super(mensagem);
    }
}

