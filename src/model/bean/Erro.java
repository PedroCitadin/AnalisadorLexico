
package model.bean;

/**
 *
 * @author Pedro Citadin Coelho
 */
public class Erro {
    private boolean status;
    private String causa;
    private int linha;

    public Erro(boolean status) {
        this.status = status;
    }

    public Erro(boolean status, String causa, int linha) {
        this.status = status;
        this.causa = causa;
        this.linha = linha;
    }

    public Erro() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }
    
    
}
