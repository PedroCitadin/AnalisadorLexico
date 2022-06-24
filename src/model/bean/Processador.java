package model.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Pedro Citadin Coelho
 */
public class Processador {

    private static final HashMap<String, Integer> tabela = Token.implementaTabela();
    private static final HashMap<String, Integer> tabelaNaoTerminais = Token.implementaNaoTerminais();
    private static final List<String> palavrasReservadas = Token.palavrasReservadas();
    private static final List<String> simbolosEspeciais = Token.simbolosEspeciais();
    private static final List<String> operadoresAritimeticos = Token.operadoresAritimeticos();
    private static final List<String> operadoresRelacionais = Token.operadoresRelacionais();
    private static Token tokenAnterior = new Token(0, "", 1);

    public static Erro analisadorLexico(Stack<Token> pilhaFinal, List<String> linhas) {
        int linhaAtual = 0;
        boolean auxComent = false;
        Stack<String> pilhaLinhas = new Stack<String>();
        Stack<String> pilhaAuxiliar = new Stack<String>();
        Stack<Token> pilhaAuxiliar2 = new Stack<Token>();
        Queue<Character> filaCaracteres = new LinkedList<>();
        Queue<Character> filaCaracteresAuxiliar = new LinkedList<>();
        for (String l : linhas) {

            l = l.trim();
            pilhaLinhas.add(l);

        }
        for (String s : pilhaLinhas) {
            String aux = "";
            String aux2 = "";
            String aux3 = "";
            for (Character c : s.toCharArray()) {
                filaCaracteres.add(c);
            }
            linhaAtual++;
            filaCaracteresAuxiliar.clear();
            filaCaracteresAuxiliar.addAll(filaCaracteres);
            do {

                if (auxComent) {
                    ///comentário aberto
                    while (!String.valueOf(filaCaracteres.peek()).equalsIgnoreCase("*")) {
                        aux += String.valueOf(filaCaracteres.poll());
                        if (filaCaracteres.peek() == null) {
                            auxComent = true;
                            break;
                        }
                    }
                    if (String.valueOf(filaCaracteres.peek()).equalsIgnoreCase("*")) {
                        auxComent = false;
                    }
                    aux += String.valueOf(filaCaracteres.poll());
                    aux += String.valueOf(filaCaracteres.poll());

                    aux = "";
                } else if (String.valueOf(filaCaracteres.peek()).equalsIgnoreCase("(")) {
                    ////verifica comentário
                    filaCaracteresAuxiliar.clear();
                    filaCaracteresAuxiliar.addAll(filaCaracteres);

                    aux += String.valueOf(filaCaracteresAuxiliar.poll());

                    if (String.valueOf(filaCaracteresAuxiliar.peek()).equalsIgnoreCase("*")) {
                        ///pega comentario
                        aux += filaCaracteres.poll();
                        filaCaracteres.poll();

                        while (!String.valueOf(filaCaracteres.peek()).equalsIgnoreCase("*")) {
                            aux += String.valueOf(filaCaracteres.poll());
                            if (filaCaracteres.peek() == null) {
                                auxComent = true;
                                break;
                            }
                        }
                        ///fecha comentário
                        aux += String.valueOf(filaCaracteres.poll());
                        aux += String.valueOf(filaCaracteres.poll());

                        aux = "";
                    } else {
                        aux = "";
                        if (tabela.containsKey(String.valueOf(filaCaracteres.peek()).toUpperCase())) {
                            aux += String.valueOf(filaCaracteres.poll()).toUpperCase();
                            pilhaFinal.add(new Token(tabela.get(aux.toUpperCase()), aux.toUpperCase(), linhaAtual));
                            aux = "";
                        }
                    }
                    aux = "";
                    filaCaracteresAuxiliar.clear();
                    filaCaracteresAuxiliar.addAll(filaCaracteres);
                } else if (String.valueOf(filaCaracteres.peek()).equalsIgnoreCase("'") || String.valueOf(filaCaracteres.peek()).equalsIgnoreCase("‘")) {
                    ///verifica String
                    aux += String.valueOf(filaCaracteres.poll());
                    while (!String.valueOf(filaCaracteres.peek()).equalsIgnoreCase("'") && !String.valueOf(filaCaracteres.peek()).equalsIgnoreCase("‘")) {
                        aux += String.valueOf(filaCaracteres.poll());

                    }
                    aux += String.valueOf(filaCaracteres.poll());
                    if (aux.length() <= 255) {
                        pilhaFinal.add(new Token(48, aux, linhaAtual));
                    } else {
                        return new Erro(true, "Literal não pode conter mais de 255 caracteres", linhaAtual);
                    }
                    aux = "";
                } else {
                    aux = aux.replaceAll("\\s", "");
                    if (String.valueOf(filaCaracteres.peek()).equalsIgnoreCase(" ")) {
                        filaCaracteres.poll();

                        if (!aux.equalsIgnoreCase(" ") && !aux.equalsIgnoreCase("")) {

                            try {
                                Double.parseDouble(aux);
                                if (Integer.parseInt(aux) >= -32767 && Integer.parseInt(aux) <= 32767) {
                                    pilhaFinal.add(new Token(26, aux, linhaAtual));
                                } else {
                                    return new Erro(true, "Valor inteiro fora da faixa aceita", linhaAtual);
                                }
                            } catch (NumberFormatException e) {
                                aux3 = String.valueOf(aux.charAt(0));

                                try {
                                    Double.parseDouble(aux3);
                                    return new Erro(true, "Identificador não pode começar com um número", linhaAtual);
                                } catch (NumberFormatException ee) {
                                    if (aux.length() <= 30) {
                                        pilhaFinal.add(new Token(25, aux, linhaAtual));
                                    } else {
                                        return new Erro(true, "Identificador não pode conter mais de 30 caracteres", linhaAtual);
                                    }
                                }
                            }
                            aux = "";
                        }
                    } else {
                        if (!filaCaracteres.isEmpty()) {
                            aux2 = filaCaracteres.poll().toString();

                            if (tabela.containsKey(aux2.toString().toUpperCase() + filaCaracteres.peek()) && !Token.palavrasReservadas().contains(aux2.toString().toUpperCase() + filaCaracteres.peek())) {

                                if (!aux.equalsIgnoreCase(" ") && !aux.equalsIgnoreCase("")) {

                                    try {
                                        Double.parseDouble(aux);
                                        if (Integer.parseInt(aux) >= -32767 && Integer.parseInt(aux) <= 32767) {
                                            pilhaFinal.add(new Token(26, aux, linhaAtual));
                                        } else {
                                            return new Erro(true, "Valor inteiro fora da faixa aceita", linhaAtual);
                                        }
                                    } catch (NumberFormatException e) {
                                        aux3 = String.valueOf(aux.charAt(0));

                                        try {
                                            Double.parseDouble(aux3);
                                            return new Erro(true, "Identificador não pode começar com um número", linhaAtual);
                                        } catch (NumberFormatException ee) {
                                            if (aux.length() <= 30) {
                                                pilhaFinal.add(new Token(25, aux, linhaAtual));
                                            } else {
                                                return new Erro(true, "Identificador não pode conter mais de 30 caracteres", linhaAtual);
                                            }
                                        }
                                    }
                                    aux = "";
                                }
                                pilhaFinal.add(new Token(tabela.get(aux2.toString().toUpperCase() + filaCaracteres.peek()), aux2.toString().toUpperCase() + filaCaracteres.peek(), linhaAtual));
                                filaCaracteres.poll();
                            } else if (tabela.containsKey(aux2.toUpperCase()) && !Token.palavrasReservadas().contains(aux2.toString().toUpperCase() + filaCaracteres.peek())) {

                                if (!aux.equalsIgnoreCase(" ") && !aux.equalsIgnoreCase("")) {

                                    try {
                                        Double.parseDouble(aux);
                                        if (Integer.parseInt(aux) >= -32767 && Integer.parseInt(aux) <= 32767) {
                                            pilhaFinal.add(new Token(26, aux, linhaAtual));
                                        } else {
                                            return new Erro(true, "Valor inteiro fora da faixa aceita", linhaAtual);
                                        }
                                    } catch (NumberFormatException e) {
                                        aux3 = String.valueOf(aux.charAt(0));

                                        try {
                                            Double.parseDouble(aux3);
                                            return new Erro(true, "Identificador não pode começar com um número", linhaAtual);
                                        } catch (NumberFormatException ee) {
                                            if (aux.length() <= 30) {
                                                pilhaFinal.add(new Token(25, aux, linhaAtual));
                                            } else {
                                                return new Erro(true, "Identificador não pode conter mais de 30 caracteres", linhaAtual);
                                            }
                                        }
                                    }
                                    aux = "";
                                }
                                if (aux2.equalsIgnoreCase("-") && pilhaFinal.peek().getCod() != 26) {
                                    aux += aux2;
                                } else {
                                    pilhaFinal.add(new Token(tabela.get(aux2.toUpperCase()), aux2.toUpperCase(), linhaAtual));
                                }

                            } else {

                                aux += aux2.toString();

                                aux = aux.replaceAll(" ", "");

                                if (tabela.containsKey(aux.toUpperCase())) {

                                    if (Token.verificaSimboloDelimitadorIgual(String.valueOf(filaCaracteres.peek())) || filaCaracteres.peek() == null) {

                                        pilhaFinal.add(new Token(tabela.get(aux.toUpperCase()), aux.toUpperCase(), linhaAtual));
                                        aux = "";
                                    }

                                } else if (Token.verificaSimboloDelimitadorIgual(String.valueOf(filaCaracteres.peek())) || filaCaracteres.isEmpty()) {
                                    if (!aux.equalsIgnoreCase(" ") && !aux.equalsIgnoreCase("")) {

                                        try {
                                            Double.parseDouble(aux);
                                            if (Integer.parseInt(aux) >= -32767 && Integer.parseInt(aux) <= 32767) {
                                                pilhaFinal.add(new Token(26, aux, linhaAtual));
                                            } else {
                                                return new Erro(true, "Valor inteiro fora da faixa aceita", linhaAtual);
                                            }
                                        } catch (NumberFormatException e) {
                                            aux3 = String.valueOf(aux.charAt(0));

                                            try {
                                                Double.parseDouble(aux3);
                                                return new Erro(true, "Identificador não pode começar com um número", linhaAtual);
                                            } catch (NumberFormatException ee) {
                                                if (aux.length() <= 30) {
                                                    pilhaFinal.add(new Token(25, aux, linhaAtual));
                                                } else {
                                                    return new Erro(true, "Identificador não pode conter mais de 30 caracteres", linhaAtual);
                                                }
                                            }
                                        }
                                        aux = "";
                                    }
                                }

                            }
                        }
                    }
                }

            } while (!filaCaracteres.isEmpty());

            filaCaracteres.clear();
            filaCaracteresAuxiliar.clear();
        }
        String verificador = "";

        for (Token t : pilhaFinal) {

            if (!pilhaFinal.empty()) {

                if (t.getCod() == 26) {
                    if (verificador.equalsIgnoreCase("ab")) {

                        verificador += "c";
                    } else if (verificador.equalsIgnoreCase("")) {
                        verificador += "a";
                    } else {
                        verificador = "";
                    }

                }
                if (t.getCod() == 49) {

                    if (verificador.equalsIgnoreCase("a")) {

                        verificador += "b";
                    } else {
                        verificador = "";
                    }
                }
                if (verificador.equalsIgnoreCase("abc")) {
                    System.out.println("aqui");
                    return new Erro(true, "Apenas números inteiros são aceitos", t.getLinha());
                }

            }
        }
        return new Erro(false);
    }

    public static Erro analisadorSintatico(Queue<Token> filaFinal, Queue<Token> filaNterminais) {

        if (!filaNterminais.isEmpty()) {

            HashMap<String, String> tabelaParsing = new HashMap<String, String>();

            Token.implementaTabelaParsing(tabelaParsing);
            if (filaNterminais.peek().getCod() < 52) {
                if (filaNterminais.peek().getCod() == filaFinal.peek().getCod()) {
                    filaNterminais.poll();
                    tokenAnterior = filaFinal.peek();
                    filaFinal.poll();

                } else {

                }
            } else {
                try {

                    if (!tabelaParsing.get("" + filaNterminais.peek().getCod() + "," + filaFinal.peek().getCod()).equalsIgnoreCase("null")) {
                        List<Token> aux = Token.analisaTabelaParsing(tabelaParsing, "" + filaNterminais.peek().getCod() + "," + filaFinal.peek().getCod(), tabelaNaoTerminais, tabela);
                        filaNterminais.poll();
                        aux.addAll(filaNterminais);
                        filaNterminais.clear();
                        filaNterminais.addAll(aux);

                    } else if (tabelaParsing.get("" + filaNterminais.peek().getCod() + "," + filaFinal.peek().getCod()).equalsIgnoreCase("null")) {
                        filaNterminais.poll();

                    } else {
                        //return new Erro(true, "Erro Sintático", filaFinal.peek().getLinha());
                    }
                } catch (Exception e) {
                    return new Erro(true, "Erro Sintático", tokenAnterior.getLinha());
                }

            }

        }

        return new Erro(false);
    }

    //////////////////Analise Semantica 
    public static Erro analisadorSemantico(Queue<Token> filaFinal) {
        HashMap<String, Simbolo> tabelaSimbolos = new HashMap<String, Simbolo>();
        int dclCategoria = 0;
        int nivel = 0;
        boolean temProcedure = false;
        int vrfCategoria = 0;
        
        /////pega os identificadores declarados
        for (Token token : filaFinal) {
            switch (token.getCod()) {
                case 2:
                    dclCategoria = 1;
                    break;
                case 3:
                    dclCategoria = 2;
                    break;
                case 4:
                    dclCategoria = 3;
                    break;
                case 5:
                    dclCategoria = 4;
                    break;
                case 1:
                    dclCategoria = 6;
                    break;

            }
            if (token.getCod() == 25) {

                switch (dclCategoria) {
                    case 1:
                        if (tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), "LABEL", "", nivel).toString())) {
                            return new Erro(true, "Erro Semantico, simbolo já foi declarado", token.getLinha());
                        }
                        tabelaSimbolos.put(new Simbolo(token.getSimbolo(), "LABEL", "", nivel).toString(), new Simbolo(token.getSimbolo(), "LABEL", "", nivel));
                        break;
                    case 2:
                        if (tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), "CONST", "", nivel).toString())) {
                            return new Erro(true, "Erro Semantico, simbolo já foi declarado", token.getLinha());
                        }
                        tabelaSimbolos.put(new Simbolo(token.getSimbolo(), "CONST", "", nivel).toString(), new Simbolo(token.getSimbolo(), "CONST", "", nivel));
                        break;
                    case 3:
                        if (tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), "VAR", "INTEIRO", nivel).toString())) {
                            return new Erro(true, "Erro Semantico, simbolo já foi declarado", token.getLinha());
                        }
                        tabelaSimbolos.put(new Simbolo(token.getSimbolo(), "VAR", "INTEIRO", nivel).toString(), new Simbolo(token.getSimbolo(), "VAR", "INTEIRO", nivel));
                        break;
                    case 4:
                        if (tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), "PROCEDURE", "", nivel).toString())) {
                            return new Erro(true, "Erro Semantico, simbolo já foi declarado", token.getLinha());
                        }
                        temProcedure = true;
                        tabelaSimbolos.put(new Simbolo(token.getSimbolo(), "PROCEDURE", "", nivel).toString(), new Simbolo(token.getSimbolo(), "PROCEDURE", "", nivel));
                        dclCategoria = 5;
                        nivel = 1;
                        break;
                    case 5:
                        if (tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), "PARAMETRO", "INTEIRO", nivel).toString())) {
                            return new Erro(true, "Erro Semantico, simbolo já foi declarado", token.getLinha());
                        }
                        tabelaSimbolos.put(new Simbolo(token.getSimbolo(), "PARAMETRO", "INTEIRO", nivel).toString(), new Simbolo(token.getSimbolo(), "PARAMETRO", "INTEIRO", nivel));
                        break;
                    case 6:
                        tabelaSimbolos.put(new Simbolo(token.getSimbolo(), "PROGRAMA", "", nivel).toString(), new Simbolo(token.getSimbolo(), "PROGRAMA", "", nivel));
                        break;
                        
                }
            }
            if (token.getCod() == 6) {
                dclCategoria = 0;
            }
            if (token.getCod() == 7) {
                nivel = 0;
            }
        }
        
        //////valida as variaveis
        nivel = 0;
        for (Token token : filaFinal) {
            if (!temProcedure) {
                vrfCategoria = 4;
            }
            if (token.getCod()==5) {
                vrfCategoria = 1;
                nivel = 1;
            }
            if (vrfCategoria == 1) {
                if (token.getCod()==6) {
                    vrfCategoria =2;
                }
            }
            if (vrfCategoria ==2) {
                if (token.getCod()==25) {
                    
                    if (!tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), nivel).toString())) {
                        return new Erro(true, "Erro Semantico, simbolo não declarado( "+token.getSimbolo()+" )", token.getLinha());
                        
                    }
                }
                if (token.getCod()==7) {
                    vrfCategoria = 4;
                    nivel = 0;
                }
            }
            if (vrfCategoria==4) {
                if (token.getCod()==25) {
                    
                    if (!tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), nivel).toString())) {
                        return new Erro(true, "Erro Semantico, simbolo não declarado( "+token.getSimbolo()+" )", token.getLinha());
                        
                    }
                }
                if (token.getCod()==11) {
                    vrfCategoria = 5;
                }
                if (token.getCod()==49) {
                    
                }
            }
            if (vrfCategoria == 5) {
                if (token.getCod()==25) {
                    
                    if (!tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), nivel).toString())) {
                        return new Erro(true, "Erro Semantico, simbolo não declarado( "+token.getSimbolo()+" )", token.getLinha());
                        
                    }
                }
                if (token.getCod()==36) {
                    nivel=1;
                }
                if(token.getCod()==37){
                    nivel=0;
                    vrfCategoria=4;
                }
            }
            
            

        }

        ///lista os tokens
        for (String s : tabelaSimbolos.keySet()) {
            System.out.println(s);
        }
        return new Erro();

    }

}
