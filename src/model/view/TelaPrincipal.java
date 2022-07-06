package model.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.bean.Arquivo;
import model.bean.Erro;
import model.bean.Processador;
import model.bean.Token;
import model.util.NumeredBorder;
import model.util.TokenTableModel;

/**
 *
 * @author Pedro Citadin Coelho
 */
public class TelaPrincipal extends JFrame {

    private JMenuBar barraMenu;
    private JTextArea txtCodigo;
    private JTable tabelaTokens;
    private JScrollPane sPane;
    private JTable tabelaTokensNaoTerminais;
    private JScrollPane sPaneNaoTerminal;
    private JScrollPane sPaneCodigo;
    private JButton novoArquivo;
    private JButton carregarArquivo;
    private JButton salvarArquivo;
    private JButton xArquivo;
    private JButton processarLexico;
    private JButton processarSintatico;
    private JButton processarSemantico;
    private int contador;
    private JFileChooser fc;
    private JFileChooser dc;
    private Stack<Token> pilhaFinal;

    private Token tokens;
    private boolean statusLexico;
    private boolean statusSintatico;
    private Arquivo arq;
    private List<String> linhas;
    private Queue<Token> filaFinal;
    private Queue<Token> filaFinalBackup;
    private Queue<Token> filaNTerminais;

    public TelaPrincipal() {
        this.setSize(1215, 930);
        setResizable(false);
        setTitle("Analisador Léxico");
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        criaComponentes();
    }

    public void criaComponentes() {
        contador = 0;
        arq = new Arquivo();
        tokens = new Token();
        pilhaFinal = new Stack<Token>();
        filaFinal = new LinkedList<>();
        filaFinalBackup = new LinkedList<>();
        filaNTerminais = new LinkedList<>();
        statusLexico = false;
        statusSintatico = false;
        linhas = new ArrayList<String>();
        fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.setCurrentDirectory(new File(""));
        dc = new JFileChooser();
        dc.setMultiSelectionEnabled(false);
        dc.setCurrentDirectory(new File(""));
        dc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        txtCodigo = new JTextArea();
        txtCodigo.setBounds(5, 62, 590, 740);
        txtCodigo.setBorder(new NumeredBorder());

        sPaneCodigo = new JScrollPane(txtCodigo);
        sPaneCodigo.setBounds(5, 62, 595, 740);

        getContentPane().add(sPaneCodigo);

        TokenTableModel ttm = new TokenTableModel();
        tabelaTokens = new JTable(ttm);
        tabelaTokens.setBounds(605, 62, 590, 400);
        sPane = new JScrollPane(tabelaTokens);
        sPane.setBounds(605, 62, 595, 400);

        getContentPane().add(sPane);

        TokenTableModel ttm2 = new TokenTableModel();
        tabelaTokensNaoTerminais = new JTable(ttm2);
        tabelaTokensNaoTerminais.setBounds(605, 480, 590, 400);
        sPaneNaoTerminal = new JScrollPane(tabelaTokensNaoTerminais);
        sPaneNaoTerminal.setBounds(605, 480, 595, 400);

        getContentPane().add(sPaneNaoTerminal);
        barraMenu = new JMenuBar();
        barraMenu.setBounds(0, 0, 1215, 50);

        novoArquivo = new JButton();
        novoArquivo.setPreferredSize(new Dimension(50, 50));
        novoArquivo.setIcon(new ImageIcon(getClass().getResource("/model/imagens/novoArquivo.png")));
        novoArquivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String aux = JOptionPane.showInputDialog("Informe o nome do arquivo");
                if (aux != null && !aux.equalsIgnoreCase("")) {
                    arq.setNome(aux);

                    dc.showDialog(getContentPane(), "Selecionar Diretório");
                    if (!dc.getSelectedFile().getParent().isEmpty()) {
                        arq.setEndereco(dc.getSelectedFile().getPath());
                        try {
                            arq.criaArquivo(arq);
                        } catch (IOException ex) {
                            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        txtCodigo.setText("");
                    }
                }
            }
        });

        carregarArquivo = new JButton();
        carregarArquivo.setPreferredSize(new Dimension(50, 50));
        carregarArquivo.setIcon(new ImageIcon(getClass().getResource("/model/imagens/carregarArquivo.png")));
        carregarArquivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fc.showDialog(getContentPane(), "Selecionar Arquivo");
                try {
                    arq.setEndereco(fc.getSelectedFile().getParent());
                    arq.setNome(fc.getSelectedFile().getName());
                    try {
                        List<String> linhas = arq.pegalistaLinhas(arq);
                        String texto = "";
                        txtCodigo.setRows(linhas.size());
                        for (String s : linhas) {
                            texto += s + "\n";
                        }

                        txtCodigo.setText(texto);
                    } catch (IOException ex) {
                        Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (java.lang.NullPointerException e) {

                }
            }
        });

        salvarArquivo = new JButton();
        salvarArquivo.setPreferredSize(new Dimension(50, 50));
        salvarArquivo.setIcon(new ImageIcon(getClass().getResource("/model/imagens/salvarArquivo.png")));
        salvarArquivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {

                    if (arq.getNome() != null) {
                        try {
                            arq.reescreveNoArquivo(arq, txtCodigo.getText());
                        } catch (IOException ex) {
                            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        String aux = JOptionPane.showInputDialog("Informe o nome do arquivo");
                        if (!aux.equalsIgnoreCase("")) {
                            arq.setNome(aux);

                            dc.showDialog(getContentPane(), "Selecionar Diretório");
                            if (!dc.getSelectedFile().getParent().isEmpty()) {
                                arq.setEndereco(dc.getSelectedFile().getPath());
                                try {
                                    arq.criaArquivo(arq);
                                } catch (IOException ex) {
                                    Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                txtCodigo.setText("");
                            }
                        }
                    }
                } catch (java.lang.NullPointerException e) {

                }

            }
        });

        xArquivo = new JButton();
        xArquivo.setPreferredSize(new Dimension(50, 50));
        xArquivo.setIcon(new ImageIcon(getClass().getResource("/model/imagens/xArquivo.png")));
        xArquivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ttm.limpar();
                ttm2.limpar();
                filaFinal.clear();
                filaNTerminais.clear();
                pilhaFinal.clear();
                statusLexico = false;
                filaFinalBackup.clear();
            }
        });

        processarLexico = new JButton();
        processarLexico.setPreferredSize(new Dimension(50, 50));
        processarLexico.setIcon(new ImageIcon(getClass().getResource("/model/imagens/processarLexico.png")));
        processarLexico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                tabelaTokens.setModel(ttm);
                ttm.limpar();

                String array[] = txtCodigo.getText().split("\n");
                linhas.clear();
                for (int i = 0; i < array.length; i++) {

                    linhas.add(array[i]);
                }
                ////correção feita 18/05/2022
                pilhaFinal.clear();
                filaFinal.clear();
                Erro erro = Processador.analisadorLexico(pilhaFinal, linhas);
                if (!erro.isStatus()) {
                    ttm.limpar();
                    filaFinal.addAll(pilhaFinal);
                    for (Token t : filaFinal) {
                        ttm.addToken(t);
                    }

                    tabelaTokens.setModel(ttm);
                    statusLexico = true;
                } else {
                    ttm.limpar();
                    JOptionPane.showMessageDialog(rootPane, erro.getCausa() + " na linha: " + erro.getLinha(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        processarSintatico = new JButton();
        processarSintatico.setPreferredSize(new Dimension(50, 50));
        processarSintatico.setIcon(new ImageIcon(getClass().getResource("/model/imagens/processaPasso.png")));
        processarSintatico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                boolean erroTotal = false;
                if (statusLexico) {

                    if (!filaFinal.isEmpty()) {
                        filaFinalBackup.clear();
                        filaFinalBackup.addAll(filaFinal);
                        while (!filaFinal.isEmpty()) {
                            if (filaNTerminais.isEmpty()) {
                                filaNTerminais.add(new Token(52, "PROGRAMA"));
                            }
                            HashMap<String, Integer> tabelaNT = new HashMap<String, Integer>();

                            Erro erro = Processador.analisadorSintatico(filaFinal, filaNTerminais);
                            if (erro.isStatus()) {
                                JOptionPane.showMessageDialog(rootPane, erro.getCausa() + " na linha: " + erro.getLinha(), "Erro", JOptionPane.ERROR_MESSAGE);
                                erroTotal = true;
                                break;

                            }
                            ttm.limpar();
                            for (Token t : filaFinal) {
                                ttm.addToken(t);
                            }
                            tabelaTokens.setModel(ttm);

                            ttm2.limpar();
                            for (Token t : filaNTerminais) {
                                ttm2.addToken(t);
                            }
                            tabelaTokensNaoTerminais.setModel(ttm2);
                        }

                    } else {
                        JOptionPane.showConfirmDialog(rootPane, "Analise Sintática já feita!");
                    }
                    if (!erroTotal) {
                        statusSintatico = true;
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "É preciso fazer a analise Léxica primeiro!","Erro", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        processarSemantico = new JButton();
        processarSemantico.setPreferredSize(new Dimension(50, 50));
        processarSemantico.setIcon(new ImageIcon(getClass().getResource("/model/imagens/processarSemantico.png")));
        processarSemantico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (statusSintatico) {
                    filaFinal.addAll(filaFinalBackup);
                    Erro erro = Processador.analisadorSemantico(filaFinal);
                    if (erro.isStatus()) {
                        JOptionPane.showMessageDialog(rootPane, erro.getCausa() + " na linha: " + erro.getLinha(), "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Programa OK!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }
                }else{
                     JOptionPane.showMessageDialog(rootPane, "É preciso fazer a analise Sintárica primeiro!", "Erro", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        barraMenu.add(novoArquivo);
        barraMenu.add(carregarArquivo);
        barraMenu.add(salvarArquivo);
        barraMenu.add(xArquivo);
        barraMenu.add(processarLexico);
        barraMenu.add(processarSintatico);
        barraMenu.add(processarSemantico);
        getContentPane().add(barraMenu);
    }

}
