package model.bean;

import java.util.ArrayList;
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

    public static void processa(Stack<Token> pilhaFinal, List<String> linhas, HashMap<String, Integer> tabela) {
        Stack<String> pilhaLinhas = new Stack<String>();
        Stack<String> pilhaAuxiliar = new Stack<String>();
        Queue<Character> filaCaracteres = new LinkedList<>();
        Queue<Character> filaCaracteresAuxiliar = new LinkedList<>();
        for (String l : linhas) {
            pilhaLinhas.add(l);

        }
        for (String s : pilhaLinhas) {
            String aux = "";
            String aux2 = "";
            for (Character c : s.toCharArray()) {
                filaCaracteres.add(c);
            }
            filaCaracteresAuxiliar.clear();
            filaCaracteresAuxiliar.addAll(filaCaracteres);
            do {
                if (String.valueOf(filaCaracteres.peek()).equalsIgnoreCase("(")) {
                    filaCaracteresAuxiliar.clear();
                    filaCaracteresAuxiliar.addAll(filaCaracteres);
                    System.out.println("aqui!!!!");
                    aux += String.valueOf(filaCaracteresAuxiliar.poll());
                    System.out.println(aux);
                    if (String.valueOf(filaCaracteresAuxiliar.peek()).equalsIgnoreCase("*")) {
                        aux+=filaCaracteres.poll();
                        System.out.println(filaCaracteres.poll());
                        
                        
                        
                        
                        while(!String.valueOf(filaCaracteres.peek()).equalsIgnoreCase("*")){
                        aux+=String.valueOf(filaCaracteres.poll());
                        
                        }
                        aux+=String.valueOf(filaCaracteres.poll());
                        aux+=String.valueOf(filaCaracteres.poll());
                        
                        System.out.println("aqui: "+aux);
                        aux="";
                    }else{
                        aux="";
                        if (tabela.containsKey(String.valueOf(filaCaracteres.peek()).toUpperCase())) {
                            aux+=String.valueOf(filaCaracteres.poll()).toUpperCase();
                            pilhaFinal.add(new Token(tabela.get(aux.toUpperCase()), aux.toUpperCase()));
                            aux="";
                        }
                    }
                    aux = "";
                    filaCaracteresAuxiliar.clear();
                    filaCaracteresAuxiliar.addAll(filaCaracteres);
                }else if (String.valueOf(filaCaracteres.peek()).equalsIgnoreCase("'")) {
                    aux += String.valueOf(filaCaracteres.poll());
                    while(!String.valueOf(filaCaracteres.peek()).equalsIgnoreCase("'")){
                        aux+=String.valueOf(filaCaracteres.poll());
                        
                    }
                    aux+=String.valueOf(filaCaracteres.poll());
                    pilhaFinal.add(new Token(48, aux));
                    aux="";
                } else {
                    if (String.valueOf(filaCaracteres.peek()).equalsIgnoreCase(" ")) {
                        filaCaracteres.poll();
                        if (!aux.equalsIgnoreCase(" ") && !aux.equalsIgnoreCase("")) {
                                
                                try {
                                    Double.parseDouble(aux);
                                    pilhaFinal.add(new Token(26, aux));
                                } catch (NumberFormatException e) {
                                    pilhaFinal.add(new Token(25, aux));
                                }
                                aux = "";
                            }
                    } else {
                        if (!filaCaracteres.isEmpty()) {
                            aux2 = filaCaracteres.poll().toString();

                        if (tabela.containsKey(aux2.toString().toUpperCase() + filaCaracteres.peek())) {
                            if (!aux.equalsIgnoreCase(" ") && !aux.equalsIgnoreCase("")) {

                                try {
                                    Double.parseDouble(aux);
                                    pilhaFinal.add(new Token(26, aux));
                                } catch (NumberFormatException e) {
                                    pilhaFinal.add(new Token(25, aux));
                                }
                                aux = "";
                            }
                            pilhaFinal.add(new Token(tabela.get(aux2.toString().toUpperCase() + filaCaracteres.peek()), aux2.toString().toUpperCase() + filaCaracteres.peek()));
                            filaCaracteres.poll();
                        } else if (tabela.containsKey(aux2.toUpperCase())) {
                            
                            if (!aux.equalsIgnoreCase(" ") && !aux.equalsIgnoreCase("")) {
                                
                                try {
                                    Double.parseDouble(aux);
                                    pilhaFinal.add(new Token(26, aux));
                                } catch (NumberFormatException e) {
                                    pilhaFinal.add(new Token(25, aux));
                                }
                                aux = "";
                            }
                            pilhaFinal.add(new Token(tabela.get(aux2.toUpperCase()), aux2.toUpperCase()));

                        } else {
                            aux += aux2.toString();
                            if (tabela.containsKey(aux.toUpperCase())) {
                                pilhaFinal.add(new Token(tabela.get(aux.toUpperCase()), aux.toUpperCase()));
                                aux = "";
                            }else if(filaCaracteres.isEmpty()){
                                if (!aux.equalsIgnoreCase(" ") && !aux.equalsIgnoreCase("")) {
                                
                                try {
                                    Double.parseDouble(aux);
                                    pilhaFinal.add(new Token(26, aux));
                                } catch (NumberFormatException e) {
                                    pilhaFinal.add(new Token(25, aux));
                                }
                                aux = "";
                            }
                            }

                        }
                        }
                    }
                }

            }while(!filaCaracteres.isEmpty());

            filaCaracteres.clear();
            filaCaracteresAuxiliar.clear();
        }

    }

}
