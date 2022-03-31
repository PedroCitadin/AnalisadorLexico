package model.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Pedro Citadin Coelho
 */
public class Token {

    private int cod;
    private String simbolo;
    private int linha;

    public Token(int cod, String simbolo, int linha) {
        this.cod = cod;
        this.simbolo = simbolo;
        this.linha = linha;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }
    
    
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Token(int cod, String simbolo) {
        this.cod = cod;
        this.simbolo = simbolo;
    }

    public Token() {
    }

    public void implementaTabela(HashMap<String, Integer> tokens) {
        tokens.put("PROGRAM", 1);
        tokens.put("THEN", 14);
        tokens.put("FOR", 27);
        tokens.put("=", 40);
        tokens.put("LABEL", 2);
        tokens.put("ELSE", 15);
        tokens.put("TO", 28);
        tokens.put(">", 41);
        tokens.put("CONST", 3);
        tokens.put("WHILE", 16);
        tokens.put("CASE", 29);
        tokens.put(">=", 42);
        tokens.put("VAR", 4);
        tokens.put("DO", 17);
        tokens.put("+", 30);
        tokens.put("<", 43);
        tokens.put("PROCEDURE", 5);
        tokens.put("REPEAT", 18);
        tokens.put("-", 31);
        tokens.put("<=", 44);
        tokens.put("BEGIN", 6);
        tokens.put("UNTIL", 19);
        tokens.put("*", 32);
        tokens.put("<", 45);
        tokens.put("END", 7);
        tokens.put("READLN", 20);
        tokens.put("/", 33);
        tokens.put(",", 46);
        tokens.put("INTEGER", 8);
        tokens.put("WRITELN", 21);
        tokens.put("[", 34);
        tokens.put(";", 47);
        tokens.put("ARRAY", 9);
        tokens.put("OR", 22);
        tokens.put("]", 35);
        tokens.put("OF", 10);
        tokens.put("AND", 23);
        tokens.put("(", 36);
        tokens.put(".", 49);
        tokens.put("CALL", 11);
        tokens.put("NOT", 24);
        tokens.put(")", 37);
        tokens.put("..", 50);
        tokens.put("GOTO", 12);
        tokens.put(":=", 38);
        tokens.put("$", 51);
        tokens.put("IF", 13);
        tokens.put(":", 39);
    }
}
