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
    private String pai;

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }
    
   

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
        this.pai = "";
    }
    public Simbolo(String nome, String Categoria, int tipo, int nivel, String pai) {
        this.nome = nome;
        this.Categoria = Categoria;
        this.tipo = tipo;
        this.nivel = nivel;
        this.pai = pai;
    }
    public Simbolo(String nome, int nivel) {
        this.nome = nome;
        this.nivel = nivel;
        this.Categoria = "nula";
        this.tipo = 0;
        this.pai = "";
    }
    public Simbolo(String nome, int nivel, String pai) {
        this.nome = nome;
        this.nivel = nivel;
        this.Categoria = "nula";
        this.tipo = 0;
        this.pai = pai;
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
        return "Simbolo{" + "nome=" + nome + ", nivel=" + nivel +", pai="+pai+ '}';
    }

    public String retorno() {
        return "Simbolo{" + "nome=" + nome + ", nivel=" + nivel + ", tipo=" + tipo + '}';
    }

}
