package model.bean;

/**
 *
 * @author Pedro Citadin Coelho <pedro_citadin@outlook.com>
 */
public class Simbolo {

    private String nome;
    private String Categoria;
    private int tipo;
    private int nivel;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Simbolo(String nome, String Categoria, int tipo, int nivel) {
        this.nome = nome;
        this.Categoria = Categoria;
        this.tipo = tipo;
        this.nivel = nivel;
    }

    public Simbolo(String nome, int nivel) {
        this.nome = nome;
        this.nivel = nivel;
        this.Categoria = "nula";
        this.tipo = 0;
    }

    public static int converte(int cod) {
        if (cod == 26) {
            return 8;
        } else {
            return cod;
        }

    }
  
    
    
    @Override
    public String toString() {
        return "Simbolo{" + "nome=" + nome + ", nivel=" + nivel + '}';
    }

    public String retorno() {
        return "Simbolo{" + "nome=" + nome + ", nivel=" + nivel + ", tipo=" + tipo + '}';
    }

}
