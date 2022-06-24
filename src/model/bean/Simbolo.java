
package model.bean;

/**
 *
 * @author Pedro Citadin Coelho <pedro_citadin@outlook.com>
 */
public class Simbolo {
    private String nome;
    private String Categoria;
    private String tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Simbolo(String nome, String Categoria, String tipo, int nivel) {
        this.nome = nome;
        this.Categoria = Categoria;
        this.tipo = tipo;
        this.nivel = nivel;
    }

    public Simbolo(String nome, int nivel) {
        this.nome = nome;
        this.nivel = nivel;
    }
    
   
    
    @Override
    public String toString() {
        return "Simbolo{" + "nome=" + nome + ", nivel=" + nivel + '}';
    }
    
    
    
}
