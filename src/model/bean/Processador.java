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
        String pcAtual = "";
        int dclCategoria = 0;
        int nivel = 0;
        boolean temProcedure = false;
        int vrfCategoria = 0;
        Queue<Token> filaAux = new LinkedList<>();
        Queue<Token> filaAux2 = new LinkedList<>();
        Stack<Simbolo> pilhaAux = new Stack<Simbolo>();
        int tipo = 0;
        filaAux.addAll(filaFinal);

        /////pega os identificadores declarados
        for (Token token : filaFinal) {
            tipo = 0;
            filaAux2.clear();
            filaAux2.addAll(filaAux);
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
                        if (tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), "LABEL", 0, nivel, pcAtual).toString())) {
                            return new Erro(true, "Erro Semantico, simbolo"+"( "+token.getSimbolo() +" ) já foi declarado", token.getLinha());
                        }
                        tabelaSimbolos.put(new Simbolo(token.getSimbolo(), "LABEL", 0, nivel).toString(), new Simbolo(token.getSimbolo(), "LABEL", 0, nivel, pcAtual));
                        break;
                    case 2:

                        if (tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), "CONST", 0, nivel).toString())) {
                            return new Erro(true, "Erro Semantico, simbolo"+"( "+token.getSimbolo() +" ) já foi declarado", token.getLinha());
                        }
                        filaAux2.poll();
                        if (filaAux2.peek().getCod() == 40) {
                            filaAux2.poll();
                            if (filaAux2.peek().getCod() == 26) {
                                tipo = 8;
                            } else if (filaAux2.peek().getCod() == 48) {
                                tipo = 48;
                            }
                        }
                        tabelaSimbolos.put(new Simbolo(token.getSimbolo(), "CONST", tipo, nivel, pcAtual).toString(), new Simbolo(token.getSimbolo(), "CONST", tipo, nivel, pcAtual));
                        break;
                    case 3:
                        if (tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), "VAR", tipo, nivel, pcAtual).toString())) {
                            return new Erro(true, "Erro Semantico, simbolo"+"( "+token.getSimbolo() +" ) já foi declarado", token.getLinha());
                        }
                        filaAux2.poll();
                        if (filaAux2.peek().getCod() == 39) {
                            filaAux2.poll();
                            if (filaAux2.peek().getCod() != 9) {
                                tipo = filaAux2.peek().getCod();
                            } else {
                                while (filaAux2.peek().getCod() != 10) {
                                    filaAux2.poll();
                                }
                                if (filaAux2.peek().getCod() == 10) {
                                    filaAux2.poll();
                                    tipo = filaAux2.peek().getCod();
                                }
                            }
                        } else {
                            while (filaAux2.peek().getCod() != 39) {
                                filaAux2.poll().getCod();

                            }
                            if (filaAux2.peek().getCod() == 39) {
                                filaAux2.poll();
                                if (filaAux2.peek().getCod() != 9) {
                                    tipo = filaAux2.peek().getCod();
                                } else {
                                    while (filaAux2.peek().getCod() != 10) {
                                        filaAux2.poll();
                                    }
                                    if (filaAux2.peek().getCod() == 10) {
                                        filaAux2.poll();
                                        tipo = filaAux2.peek().getCod();
                                    }
                                }

                            }
                        }

                        tabelaSimbolos.put(new Simbolo(token.getSimbolo(), "VAR", tipo, nivel, pcAtual).toString(), new Simbolo(token.getSimbolo(), "VAR", tipo, nivel, pcAtual));
                        break;
                    case 4:
                        if (tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), "PROCEDURE", 0, nivel).toString())) {
                            return new Erro(true, "Erro Semantico, simbolo"+"( "+token.getSimbolo() +" ) já foi declarado", token.getLinha());
                        }
                        temProcedure = true;
                        tabelaSimbolos.put(new Simbolo(token.getSimbolo(), "PROCEDURE", 0, nivel).toString(), new Simbolo(token.getSimbolo(), "PROCEDURE", 0, nivel));
                        pcAtual = token.getSimbolo();
                        dclCategoria = 5;
                        nivel = 1;
                        break;
                    case 5:
                        if (tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), "PARAMETRO", tipo, nivel, pcAtual).toString())) {
                            return new Erro(true, "Erro Semantico, simbolo"+"( "+token.getSimbolo() +" ) já foi declarado", token.getLinha());
                        }
                        filaAux2.poll();
                        if (filaAux2.peek().getCod() == 39) {
                            filaAux2.poll();
                            if (filaAux2.peek().getCod() != 9) {
                                tipo = filaAux2.peek().getCod();
                            } else {
                                while (filaAux2.peek().getCod() != 10) {
                                    filaAux2.poll();
                                }
                                if (filaAux2.peek().getCod() == 10) {
                                    filaAux2.poll();
                                    tipo = filaAux2.peek().getCod();
                                }
                            }
                        } else {
                            while (filaAux2.peek().getCod() != 39) {
                                filaAux2.poll().getCod();

                            }
                            if (filaAux2.peek().getCod() == 39) {
                                filaAux2.poll();
                                if (filaAux2.peek().getCod() != 9) {
                                    tipo = filaAux2.peek().getCod();
                                } else {
                                    while (filaAux2.peek().getCod() != 10) {
                                        filaAux2.poll();
                                    }
                                    if (filaAux2.peek().getCod() == 10) {
                                        filaAux2.poll();
                                        tipo = filaAux2.peek().getCod();
                                    }
                                }

                            }
                        }
                        tabelaSimbolos.put(new Simbolo(token.getSimbolo(), "PARAMETRO", tipo, nivel, pcAtual).toString(), new Simbolo(token.getSimbolo(), "PARAMETRO", tipo, nivel, pcAtual));
                        break;
                    case 6:
                        tabelaSimbolos.put(new Simbolo(token.getSimbolo(), "PROGRAMA", 0, nivel).toString(), new Simbolo(token.getSimbolo(), "PROGRAMA", 0, nivel));
                        break;

                }
            }
            if (token.getCod() == 6) {
                dclCategoria = 0;
            }
            if (token.getCod() == 7) {
                nivel = 0;
                pcAtual = "";
            }
            filaAux.poll();
        }
        for (Simbolo s : tabelaSimbolos.values()) {
            System.out.println(s.toString());
        }
        //////valida as variaveis
        nivel = 0;
        List<String> listaFeitos = new ArrayList<String>();
        for (Token token : filaFinal) {
            ///verifica a existencia de um procedure no código
            if (!temProcedure) {

                //caso não exista o programa vai direto para a categoria 4
                vrfCategoria = 4;
            }

            ////verifica se o token é um procedure
            if (token.getCod() == 5) {
                //////o programa vai a para a categoria 1 e para o nivel 1
                
                vrfCategoria = 22;
                nivel = 1;
            }
            ///pega o identificado do procedure atual
            if (vrfCategoria==22) {
                if (token.getCod() == 25) {
                    pcAtual=token.getSimbolo();
                    vrfCategoria =1;
                }
            }
            if (vrfCategoria == 1) {
                ////verifica o inicio do procedure
                if (token.getCod() == 6) {
                    
                    vrfCategoria = 2;
                }
            }
            if (vrfCategoria == 2) {
                if (token.getCod() == 25) {
                    ///verifica se os identificadores foram declarados corretamente dentro do procedure
                    if (!tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), nivel, pcAtual).toString())) {
                        return new Erro(true, "Erro Semantico, simbolo não declarado( " + token.getSimbolo() + " )", token.getLinha());

                    }
                }

                if (token.getCod() == 7) {
                    ///// quando o end do procedure for identificado o programa vai para a categoria 4 e o nivel retorna para 0
                    vrfCategoria = 4;
                    nivel = 0;
                    pcAtual = "";
                   
                }
            }
            if (vrfCategoria == 4) {
                if (token.getCod() == 25) {
                    //// verifica se os identificadores foram declarados corretamente dentro do classe principal
                        
                    if (!tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), nivel, pcAtual).toString())) {
                        
                        return new Erro(true, "Erro Semantico, simbolo não declarado( " + token.getSimbolo() + " )", token.getLinha());

                    }
                }

                ////quando um procedimento é chamado com call a categoria 5 é acionada 
                if (token.getCod() == 11) {
                    vrfCategoria = 5;
                }

            }
            if (vrfCategoria == 5) {
                ///// verifica se o procedimento foi chamado corretamente
                if (token.getCod() == 25) {

                    if (!tabelaSimbolos.containsKey(new Simbolo(token.getSimbolo(), nivel).toString())) {
                        return new Erro(true, "Erro Semantico, simbolo não declarado( " + token.getSimbolo() + " )", token.getLinha());

                    } else {
                        pcAtual = token.getSimbolo();
                    }
                }
                if (token.getCod() == 36) {
                    nivel = 1;
                    vrfCategoria = 6;
                }

            }
            if (vrfCategoria == 6) {
                
                ///verifica os se os parametros são do tipo dos que foram declarados
                if (token.getCod() == 26 || token.getCod() == 25 || token.getCod() == 48) {

                    for (Simbolo sim : tabelaSimbolos.values()) {
                        if (sim.getPai().equalsIgnoreCase(pcAtual) && !listaFeitos.contains(new Simbolo(token.getSimbolo(), nivel).toString())) {
                            
                            pilhaAux.add(sim);
                        }
                    }
                    listaFeitos.add(new Simbolo(token.getSimbolo(), nivel).toString());
                    if (token.getCod()==25) {
                        if (pilhaAux.peek().getTipo() != tabelaSimbolos.get(new Simbolo(token.getSimbolo(), 0).toString()).getTipo()) {
                        System.out.println(Simbolo.converte(token.getCod()));
                        return new Erro(true, "Erro Semantico, parametro não corresponde ao tipo( " + token.getSimbolo() + " )", token.getLinha());
                    }
                    
                    }else {
                        if (pilhaAux.peek().getTipo() != Simbolo.converte(token.getCod())) {
                        System.out.println(Simbolo.converte(token.getCod()));
                        return new Erro(true, "Erro Semantico, parametro não corresponde ao tipo( " + token.getSimbolo() + " )", token.getLinha());
                    }

                    }  
                }
                if (token.getCod() == 37) {
                        
                        listaFeitos.clear();
                        nivel = 0;
                        vrfCategoria = 4;
                        pilhaAux.clear();
                        pcAtual = "";
                    }
            }
        }
        /////valida atribuições e operações
        filaAux.clear();
        filaAux.addAll(filaFinal);
        Stack<Token> pilhaAuxiliar = new Stack<Token>();
        for (Token token : filaFinal) {
            pilhaAuxiliar.clear();
            filaAux2.clear();
            filaAux2.addAll(filaAux);
            if (token.getCod() == 5) {
                nivel = 1;
            }
            if (nivel == 1) {
                if (token.getCod() == 7) {
                    nivel = 0;
                }
            }
            if (token.getCod() == 25) {

                if (tabelaSimbolos.getOrDefault(new Simbolo(token.getSimbolo(), nivel).toString(), new Simbolo(null, 0)).getCategoria().equalsIgnoreCase("VAR")) {

                    if (filaAux2.peek().getCod() == 38) {
                        filaAux2.poll();
                        while (filaAux2.peek().getCod() != 47 && filaAux2.peek().getCod() != 28) {
                            pilhaAuxiliar.add(filaAux2.poll());
                        }
                        for (Token t : pilhaAuxiliar) {

                            if (t.getCod() == 25) {
                                if (tabelaSimbolos.get(new Simbolo(token.getSimbolo(), nivel).toString()).getTipo() != tabelaSimbolos.get(new Simbolo(t.getSimbolo(), nivel).toString()).getTipo()) {
                                    return new Erro(true, "Erro Semantico, simbolo não corresponde ao tipo( " + token.getSimbolo() + " )", token.getLinha());
                                }
                            } else if (!Token.operadoresAritimeticos().contains(t.getSimbolo()) && !Token.operadoresRelacionais().contains(t.getSimbolo())) {
                                if (tabelaSimbolos.get(new Simbolo(token.getSimbolo(), nivel).toString()).getTipo() != Simbolo.converte(t.getCod())) {
                                    return new Erro(true, "Erro Semantico, simbolo não corresponde ao tipo( " + token.getSimbolo() + " )", token.getLinha());
                                }
                            }
                        }
                    } else {
                        if (Token.operadoresRelacionais().contains(filaAux2.peek().getSimbolo()) || Token.operadoresAritimeticos().contains(filaAux2.peek().getSimbolo())) {
                            while (!Token.simbolosDelimitadores().contains(filaAux2.peek().getSimbolo()) && !Token.palavrasReservadas().contains(filaAux2.peek().getSimbolo())) {
                                pilhaAuxiliar.add(filaAux2.poll());
                            }
                            for (Token t : pilhaAuxiliar) {
                                if (t.getCod() == 25) {
                                    if (tabelaSimbolos.get(new Simbolo(token.getSimbolo(), nivel).toString()).getTipo() != tabelaSimbolos.get(new Simbolo(t.getSimbolo(), nivel).toString()).getTipo()) {
                                        return new Erro(true, "Erro Semantico, simbolo não corresponde ao tipo( " + token.getSimbolo() + " )", token.getLinha());
                                    }
                                } else if (!Token.operadoresAritimeticos().contains(t.getSimbolo()) && !Token.operadoresRelacionais().contains(t.getSimbolo())) {
                                    if (tabelaSimbolos.get(new Simbolo(token.getSimbolo(), nivel).toString()).getTipo() != Simbolo.converte(t.getCod())) {
                                        return new Erro(true, "Erro Semantico, simbolo não corresponde ao tipo( " + token.getSimbolo() + " )", token.getLinha());
                                    }
                                }
                            }
                        }
                    }
                } else if (tabelaSimbolos.getOrDefault(new Simbolo(token.getSimbolo(), nivel).toString(), new Simbolo(null, 0)).getCategoria().equalsIgnoreCase("CONST")) {
                    filaAux2.poll();
                    if (Token.operadoresRelacionais().contains(filaAux2.peek().getSimbolo()) || Token.operadoresAritimeticos().contains(filaAux2.peek().getSimbolo())) {
                        while (!Token.simbolosDelimitadores().contains(filaAux2.peek().getSimbolo()) && !Token.palavrasReservadas().contains(filaAux2.peek().getSimbolo())) {
                            pilhaAuxiliar.add(filaAux2.poll());
                        }
                        for (Token t : pilhaAuxiliar) {
                            if (t.getCod() == 25) {
                                if (tabelaSimbolos.get(new Simbolo(token.getSimbolo(), nivel).toString()).getTipo() != tabelaSimbolos.get(new Simbolo(t.getSimbolo(), nivel).toString()).getTipo()) {
                                    return new Erro(true, "Erro Semantico, simbolo não corresponde ao tipo( " + token.getSimbolo() + " )", token.getLinha());
                                }
                            } else if (!Token.operadoresAritimeticos().contains(t.getSimbolo()) && !Token.operadoresRelacionais().contains(t.getSimbolo())) {
                                if (tabelaSimbolos.get(new Simbolo(token.getSimbolo(), nivel).toString()).getTipo() != Simbolo.converte(t.getCod())) {
                                    return new Erro(true, "Erro Semantico, simbolo não corresponde ao tipo( " + token.getSimbolo() + " )", token.getLinha());
                                }
                            }
                        }
                    }
                }
            }

            filaAux.poll();
        }

        ///lista os tokens
        return new Erro();

    }

}
