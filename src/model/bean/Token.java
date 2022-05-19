package model.bean;

import java.util.ArrayList;
import java.util.Arrays;
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
    private boolean terminal;

    public Token(int cod, String simbolo, int linha, boolean terminal) {
        this.cod = cod;
        this.simbolo = simbolo;
        this.linha = linha;
        this.terminal = terminal;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

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

    public static HashMap<String, Integer> implementaTabela() {
        HashMap<String, Integer> tokens = new HashMap<String, Integer>();
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

        return tokens;
    }

    public static List<String> palavrasReservadas() {
        List<String> palavras = new ArrayList<String>();
        String p[] = {"AND", "ARRAY", "BEGIN", "CALL", "CASE", "CONST", "DO", "ELSE", "END", "FOR", "GOTO",
            "IF", "INTEGER", "LABEL", "NOT", "OF", "OR", "PROCEDURE", "PROGRAM", "READLN",
            "REPEAT", "THEN", "TO", "UNTIL", "VAR", "WHILE", "WRITELN"};
        palavras.addAll(Arrays.asList(p));
        return palavras;
    }
    
    public static List<String> operadoresAritimeticos() {
        List<String> palavras = new ArrayList<String>();
        String p[] = {"+", "-", "*", "/"};
        palavras.addAll(Arrays.asList(p));
        return palavras;
    }
    public static List<String> operadoresRelacionais() {
        List<String> palavras = new ArrayList<String>();
        String p[] = {"=", "<", ">", ":=", ">=", "<="};
        palavras.addAll(Arrays.asList(p));
        return palavras;
    }
    
    public static List<String> simbolosEspeciais() {
        List<String> palavras = new ArrayList<String>();
        String p[] = {":", ",", ".", "(", ")", ":", "..", "[", "]", "´", "_"};
        palavras.addAll(Arrays.asList(p));
        return palavras;
    }
    public static List<String> simbolosDelimitadores() {
        List<String> palavras = new ArrayList<String>();
        String p[] = {":", ",", ".", "(", ")", ":", "..", "[", "]", "´", "\n", " ", null};
        palavras.addAll(Arrays.asList(p));
        return palavras;
    }
    public static boolean verificaSimboloDelimitador(String palavra){
            for (String sim : simbolosDelimitadores()) {
                if (palavra.contains(sim)) {
                    return true;
                }
            }
        return false;
        
    }
    public static boolean verificaSimboloEspecial(String palavra){
            for (String sim : simbolosEspeciais()) {
                if (palavra.contains(sim)) {
                    return true;
                }
            }
        return false;
        
    }
    public static boolean verificaSimboloEspecialIgual(String palavra){
            for (String sim : simbolosEspeciais()) {
                if (palavra.equalsIgnoreCase(sim)) {
                    return true;
                }
            }
        return false;
        
    }
    public static HashMap<String, Integer> implementaNaoTerminais() {
        HashMap<String, Integer> token = new HashMap<String, Integer>();

        token.put("PROGRAMA", 52);
        token.put("BLOCO", 53);
        token.put("DCLROT", 54);
        token.put("LID", 55);
        token.put("REPIDENT", 56);
        token.put("DCLCONST", 57);
        token.put("LDCONST", 58);
        token.put("DCLVAR", 59);
        token.put("LDVAR", 60);
        token.put("TIPO", 61);
        token.put("DCLPROC", 62);
        token.put("DEFPAR", 63);
        token.put("CORPO", 64);
        token.put("REPCOMANDO", 65);
        token.put("COMANDO", 66);
        token.put("RCOMID", 67);
        token.put("RVAR", 68);
        token.put("PARAMETROS", 69);
        token.put("REPPAR", 70);
        token.put("ELSEPARTE", 71);
        token.put("VARIAVEL", 72);
        token.put("VARIAVEL1", 73);
        token.put("REPVARIAVEL", 74);
        token.put("ITEMSAIDA", 75);
        token.put("REPITEM", 76);
        token.put("EXPRESSAO", 77);
        token.put("REPEXPSIMP", 78);
        token.put("EXPSIMP", 79);
        token.put("REPEXP", 80);
        token.put("TERMO", 81);
        token.put("REPTERMO", 82);
        token.put("FATOR", 83);
        token.put("CONDCASE", 84);
        token.put("CONTCASE", 85);
        token.put("RPINTEIRO", 86);
        token.put("SEMEFEITO", 87);

        return token;
    }

    public static void implementaTabelaParsing(HashMap<String, String> token) {
        token.put("52,1", "PROGRAM&&IDENTIFICADOR&&;&&BLOCO&&.");
        token.put("53,2", "DCLROT&&DCLCONST&&DCLVAR&&DCLPROC&&CORPO");
        token.put("53,3", "DCLROT&&DCLCONST&&DCLVAR&&DCLPROC&&CORPO");
        token.put("53,4", "DCLROT&&DCLCONST&&DCLVAR&&DCLPROC&&CORPO");
        token.put("53,5", "DCLROT&&DCLCONST&&DCLVAR&&DCLPROC&&CORPO");
        token.put("53,6", "DCLROT&&DCLCONST&&DCLVAR&&DCLPROC&&CORPO");
        token.put("54,2", "LABEL&&LID&&;");
        token.put("54,3", "NULL");
        token.put("54,4", "NULL");
        token.put("54,5", "NULL");
        token.put("54,6", "NULL");
        token.put("55,25", "IDENTIFICADOR&&REPIDENT");
        token.put("56,39", "NULL");
        token.put("56,46", ",&&IDENTIFICADOR&&REPIDENT");
        token.put("56,47", "NULL");
        token.put("57,3", "CONST&&IDENTIFICADOR&&=&&INTEIRO&&;&&LDCONST");
        token.put("57,4", "NULL");
        token.put("57,5", "NULL");
        token.put("57,6", "NULL");
        token.put("58,4", "NULL");
        token.put("58,5", "NULL");
        token.put("58,6", "NULL");
        token.put("58,25", "IDENTIFICADOR&&=&&INTEIRO&&;&&LDCONST");
        token.put("59,4", "VAR&&LID&&:&&TIPO&&;&&LDVAR");
        token.put("59,5", "NULL");
        token.put("59,6", "NULL");
        token.put("60,5", "NULL");
        token.put("60,6", "NULL");
        token.put("60,25", "LID&&:&&TIPO&&;&&LDVAR");
        token.put("61,8", "INTEGER");
        token.put("61,9", "ARRAY&&[&&INTEIRO&&..&&INTEIRO&&]&&OF&&INTEGER");
        token.put("62,5", "PROCEDURE&&IDENTIFICADOR&&DEFPAR&&;&&BLOCO&&;&&DCLPROC");
        token.put("62,6", "NULL");
        token.put("63,36", "(&&LID&&:&&INTEGER&&)");
        token.put("63,39", "NULL");
        token.put("64,6", "BEGIN&&COMANDO&&REPCOMANDO&&END");
        token.put("65,7", "NULL");
        token.put("65,47", ";&&COMANDO&&REPCOMANDO");
        token.put("66,6", "CORPO");
        token.put("66,7", "NULL");
        token.put("66,11", "CALL&&IDENTIFICADOR&&PARAMETROS");
        token.put("66,12", "GOTO&&IDENTIFICADOR");
        token.put("66,13", "IF&&EXPRESSAO&&THEN&&COMANDO&&ELSEPARTE");
        token.put("66,15", "NULL");
        token.put("66,16", "WHILE&&EXPRESSAO&&DO&&COMANDO");
        token.put("66,18", "REPEAT&&COMANDO&&UNTIL&&EXPRESSAO");
        token.put("66,19", "NULL");
        token.put("66,20", "READLN&&(&&VARIAVEL&&REPVARIAVEL&&)");
        token.put("66,21", "WRITELN&&(&&ITEMSAIDA&&REPITEM&&)");
        token.put("66,25", "IDENTIFICADOR&&RCOMID");
        token.put("66,27", "FOR&&IDENTIFICADOR&&:=&&EXPRESSAO&&TO&&EXPRESSAO&&DO&&COMANDO");
        token.put("66,29", "CASE&&EXPRESSAO&&OF&&CONDCASE&&END");
        token.put("66,47", "NULL");
        token.put("67,34", "RVAR&&:=&&EXPRESSAO");
        token.put("67,38", "RVAR&&:=&&EXPRESSAO");
        token.put("67,39", ":&&COMANDO");
        token.put("68,34", "[&&EXPRESSAO&&]");
        token.put("68,38", "NULL");
        token.put("69,7", "NULL");
        token.put("69,15", "NULL");
        token.put("69,19", "NULL");
        token.put("69,36", "(&&EXPRESSAO&&REPPAR&&)");
        token.put("69,47", "NULL");
        token.put("70,37", "NULL");
        token.put("70,46", ",&&EXPRESSAO&&REPPAR");
        token.put("71,7", "NULL");
        token.put("71,15", "ELSE&&COMANDO");
        token.put("71,19", "NULL");
        token.put("71,47", "NULL");
        token.put("72,25", "IDENTIFICADOR&&VARIAVEL1");
        token.put("73,7", "NULL");
        token.put("73,10", "NULL");
        token.put("73,14", "NULL");
        token.put("73,15", "NULL");
        token.put("73,17", "NULL");
        token.put("73,19", "NULL");
        token.put("73,22", "NULL");
        token.put("73,23", "NULL");
        token.put("73,28", "NULL");
        token.put("73,30", "NULL");
        token.put("73,31", "NULL");
        token.put("73,32", "NULL");
        token.put("73,33", "NULL");
        token.put("73,34", "[&&EXPRESSAO&&]");
        token.put("73,35", "NULL");
        token.put("73,37", "NULL");
        token.put("73,40", "NULL");
        token.put("73,41", "NULL");
        token.put("73,42", "NULL");
        token.put("73,43", "NULL");
        token.put("73,44", "NULL");
        token.put("73,45", "NULL");
        token.put("73,46", "NULL");
        token.put("73,47", "NULL");
        token.put("74,37", "NULL");
        token.put("74,46", ",&&VARIAVEL&&REPVARIAVEL");
        token.put("75,24", "EXPRESSAO");
        token.put("75,25", "EXPRESSAO");
        token.put("75,26", "EXPRESSAO");
        token.put("75,30", "EXPRESSAO");
        token.put("75,31", "EXPRESSAO");
        token.put("75,36", "EXPRESSAO");
        token.put("75,48", "LITERAL");
        token.put("76,37", "NULL");
        token.put("76,46", ",&&ITEMSAIDA&&REPITEM");
        token.put("77,24", "EXPSIMP&&REPEXPSIMP");
        token.put("77,25", "EXPSIMP&&REPEXPSIMP");
        token.put("77,26", "EXPSIMP&&REPEXPSIMP");
        token.put("77,30", "EXPSIMP&&REPEXPSIMP");
        token.put("77,31", "EXPSIMP&&REPEXPSIMP");
        token.put("77,36", "EXPSIMP&&REPEXPSIMP");
        token.put("78,7", "NULL");
        token.put("78,10", "NULL");
        token.put("78,14", "NULL");
        token.put("78,15", "NULL");
        token.put("78,17", "NULL");
        token.put("78,19", "NULL");
        token.put("78,28", "NULL");
        token.put("78,35", "NULL");
        token.put("78,37", "NULL");
        token.put("78,40", "=&&EXPSIMP");
        token.put("78,41", ">&&EXPSIMP");
        token.put("78,42", ">=&&EXPSIMP");
        token.put("78,43", "&lt;&&EXPSIMP");
        token.put("78,44", "&lt;=&&EXPSIMP");
        token.put("78,45", "&lt;&gt;&&EXPSIMP");
        token.put("78,46", "NULL");
        token.put("78,47", "NULL");
        token.put("79,24", "TERMO&&REPEXP");
        token.put("79,25", "TERMO&&REPEXP");
        token.put("79,26", "TERMO&&REPEXP");
        token.put("79,30", "+&&TERMO&&REPEXP");
        token.put("79,31", "-&&TERMO&&REPEXP");
        token.put("79,36", "TERMO&&REPEXP");
        token.put("80,7", "NULL");
        token.put("80,10", "NULL");
        token.put("80,14", "NULL");
        token.put("80,15", "NULL");
        token.put("80,17", "NULL");
        token.put("80,19", "NULL");
        token.put("80,22", "OR&&TERMO&&REPEXP");
        token.put("80,28", "NULL");
        token.put("80,30", "+&&TERMO&&REPEXP");
        token.put("80,31", "-&&TERMO&&REPEXP");
        token.put("80,35", "NULL");
        token.put("80,37", "NULL");
        token.put("80,40", "NULL");
        token.put("80,41", "NULL");
        token.put("80,42", "NULL");
        token.put("80,43", "NULL");
        token.put("80,44", "NULL");
        token.put("80,45", "NULL");
        token.put("80,46", "NULL");
        token.put("80,47", "NULL");
        token.put("81,24", "FATOR&&REPTERMO");
        token.put("81,25", "FATOR&&REPTERMO");
        token.put("81,26", "FATOR&&REPTERMO");
        token.put("81,36", "FATOR&&REPTERMO");
        token.put("82,7", "NULL");
        token.put("82,10", "NULL");
        token.put("82,14", "NULL");
        token.put("82,15", "NULL");
        token.put("82,17", "NULL");
        token.put("82,19", "NULL");
        token.put("82,22", "NULL");
        token.put("82,23", "AND&&FATOR&&REPTERMO");
        token.put("82,28", "NULL");
        token.put("82,30", "NULL");
        token.put("82,31", "NULL");
        token.put("82,32", "*&&FATOR&&REPTERMO");
        token.put("82,33", "/&&FATOR&&REPTERMO");
        token.put("82,35", "NULL");
        token.put("82,37", "NULL");
        token.put("82,40", "NULL");
        token.put("82,41", "NULL");
        token.put("82,42", "NULL");
        token.put("82,43", "NULL");
        token.put("82,44", "NULL");
        token.put("82,45", "NULL");
        token.put("82,46", "NULL");
        token.put("82,47", "NULL");
        token.put("83,24", "NOT&&FATOR");
        token.put("83,25", "VARIAVEL");
        token.put("83,26", "INTEIRO");
        token.put("83,36", "(&&EXPRESSAO&&)");
        token.put("84,26", "INTEIRO&&RPINTEIRO&&:&&COMANDO&&CONTCASE");
        token.put("85,7", "NULL");
        token.put("85,47", ";&&CONDCASE");
        token.put("86,39", "NULL");
        token.put("86,46", ",&&INTEIRO&&RPINTEIRO");

    }

    public static List<Token> analisaTabelaParsing(HashMap<String, String> token, String cods, HashMap<String, Integer> nTerminais, HashMap<String, Integer> terminais) {
        List<Token> lista = new ArrayList<Token>();
        String s = token.get(cods);
        System.out.println(s);
        String aux[] = s.split("&&");

        for (int i = 0; i < aux.length; i++) {
            try {
                lista.add(new Token(nTerminais.get(aux[i]), aux[i]));
            } catch (java.lang.NullPointerException e) {
                if (aux[i].equalsIgnoreCase("IDENTIFICADOR")) {
                    lista.add(new Token(25, "IDENTIFICADOR"));
                } else if (aux[i].equalsIgnoreCase("INTEIRO")) {

                    lista.add(new Token(26, "INTEIRO"));
                } else {
                    lista.add(new Token(terminais.get(aux[i]), aux[i]));
                }

            }

        }

        return lista;
    }

}
