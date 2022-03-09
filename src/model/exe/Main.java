package model.exe;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import model.bean.Arquivo;
import model.bean.Processador;
import model.bean.Token;

/**
 *
 * @author Pedro Citadin Coelho
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Arquivo arq = new Arquivo();
        
        Stack<Token> pilhaFinal = new Stack<Token>();
        Token tokens = new Token();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        tokens.implementaTabela(map);
        arq.setEndereco("");
        arq.setNome("arquivo.txt");
        List<String> linhas = arq.pegalistaLinhas(arq);
        Processador.processa(pilhaFinal, linhas, map);
        for (Token t : pilhaFinal) {
            System.out.println(t.getSimbolo()+" = "+t.getCod());
        }
    }
}
