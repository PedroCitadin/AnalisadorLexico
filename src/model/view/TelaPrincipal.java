package model.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import model.bean.Processador;
import model.bean.Token;
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
    private JButton novoArquivo;
    private JButton carregarArquivo;
    private JButton salvarArquivo;
    private JButton xArquivo;
    private JButton processarArquivo;
    private JFileChooser fc;
    private JFileChooser dc;
    private Stack<Token> pilhaFinal;
    private Token tokens;
    HashMap<String, Integer> map;
    private Arquivo arq;
    private List<String> linhas;

    public TelaPrincipal() {
        this.setSize(1215, 840);
        setResizable(false);
        setTitle("Analisador Léxico");
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        criaComponentes();
    }

    public void criaComponentes() {
        arq = new Arquivo();
        tokens = new Token();
        pilhaFinal = new Stack<Token>();
        map = new HashMap<String, Integer>();
        tokens.implementaTabela(map);
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
        getContentPane().add(txtCodigo);
        TokenTableModel ttm = new TokenTableModel();
        tabelaTokens = new JTable(ttm);
        tabelaTokens.setBounds(605, 62, 590, 740);
        sPane = new JScrollPane(tabelaTokens);
        sPane.setBounds(605, 62, 595, 740);

        getContentPane().add(sPane);
        barraMenu = new JMenuBar();
        barraMenu.setBounds(0, 0, 1215, 60);

        novoArquivo = new JButton();
        novoArquivo.setPreferredSize(new Dimension(50, 50));
        novoArquivo.setIcon(new ImageIcon(getClass().getResource("/model/imagens/novoArquivo.png")));
        novoArquivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
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
        });

        carregarArquivo = new JButton();
        carregarArquivo.setPreferredSize(new Dimension(50, 50));
        carregarArquivo.setIcon(new ImageIcon(getClass().getResource("/model/imagens/carregarArquivo.png")));
        carregarArquivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fc.showDialog(getContentPane(), "Selecionar Arquivo");

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

            }
        });

        salvarArquivo = new JButton();
        salvarArquivo.setPreferredSize(new Dimension(50, 50));
        salvarArquivo.setIcon(new ImageIcon(getClass().getResource("/model/imagens/salvarArquivo.png")));
        salvarArquivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (arq.getNome()!=null) {
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

            }
        });

        xArquivo = new JButton();
        xArquivo.setPreferredSize(new Dimension(50, 50));
        xArquivo.setIcon(new ImageIcon(getClass().getResource("/model/imagens/xArquivo.png")));
        xArquivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ////
            }
        });

        processarArquivo = new JButton();
        processarArquivo.setPreferredSize(new Dimension(50, 50));
        processarArquivo.setIcon(new ImageIcon(getClass().getResource("/model/imagens/processarArquivo.png")));
        processarArquivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String array[] = txtCodigo.getText().split("\n");
                linhas.clear();
                for (int i = 0; i < array.length; i++) {

                    linhas.add(array[i]);
                }
                pilhaFinal.clear();
                Processador.processa(pilhaFinal, linhas, map);
                ttm.limpar();

                for (Token t : pilhaFinal) {
                    ttm.addToken(t);
                }
                tabelaTokens.setModel(ttm);
            }
        });

        barraMenu.add(novoArquivo);
        barraMenu.add(carregarArquivo);
        barraMenu.add(salvarArquivo);
        barraMenu.add(xArquivo);
        barraMenu.add(processarArquivo);
        getContentPane().add(barraMenu);
    }

}
