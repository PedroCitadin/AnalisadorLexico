
package model.bean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Pedro Citadin Coelho
 * 
 * 
 * Classe de manipulação de arquivos feita por Pedro Citadin Coelho
 * 
 */
public class Arquivo {

    private String nome;
    private String endereco;

   
    public Arquivo(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
    }

    public Arquivo() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean verificaExistencia() {
        File f = new File(endereco + "\\" + nome);
        if (f.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public void criaArquivo(Arquivo a) throws IOException {
        File f = new File(a.getEndereco() + "\\" + a.getNome());
        if (a.verificaExistencia()) {
            System.out.println("Arquivo já existe");
        } else {
            f.createNewFile();
            System.out.println("Arquivo criado");
        }
    }

    public void leArquivoCompleto(Arquivo a) throws FileNotFoundException, IOException {
        InputStream is;
        if (a.getEndereco().equalsIgnoreCase("")) {
             is = new FileInputStream(a.getNome());
        }else{
           is = new FileInputStream(a.getEndereco() + "\\" + a.getNome());  
        }
        
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        while(s!=null){
            System.out.println(s);
            s = br.readLine();
        }
        br.close();
    }
    public void reescreveNoArquivo(Arquivo a, String texto) throws FileNotFoundException, IOException{
        OutputStream os = new FileOutputStream(a.getEndereco()+"\\"+a.getNome());
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(texto);
        bw.close();
    }public void escreveNoArquivo(Arquivo a, String texto) throws FileNotFoundException, IOException{
        OutputStream os = new FileOutputStream(a.getEndereco()+"\\"+a.getNome(), true);
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        
        bw.write(texto);
        bw.close();
    }
    public void escreveNaProxiLinhaDoArquivo(Arquivo a, String texto) throws FileNotFoundException, IOException{
        OutputStream os = new FileOutputStream(a.getEndereco()+"\\"+a.getNome(), true);
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.newLine();
        bw.write(texto);
        bw.close();
    }
    public void moveArquivo(Arquivo a, String destino){
        
        File f = new File(a.getEndereco()+"\\"+a.getNome());
        
        File des = new File(destino+"\\"+a.getNome());
        
        f.renameTo(des);
        
    }
    public List<String> pegalistaLinhas(Arquivo a) throws FileNotFoundException, IOException{
        List<String> linhas = new ArrayList();
        InputStream is;
        if (a.getEndereco().equalsIgnoreCase("")) {
             is = new FileInputStream(a.getNome());
        }else{
           is = new FileInputStream(a.getEndereco() + "\\" + a.getNome());  
        }
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        while(s!=null){
            linhas.add(s);
            s = br.readLine();
        }
        br.close();
        return linhas;
    }
    public void criaDiretorio(String a){
        File f = new File(a);
        if (!f.exists()) {
            f.mkdirs();
        }
    }
    public boolean verificaSeDiretorioExiste(String a){
        File f = new File(a);
        return f.exists();
    }
    public int verificaQuantosArquivosTem(String a){
        File f = new File(a);
        int c =0;
        for (File f2: f.listFiles()) {
            if(f2.isFile()){
                c++;
            }
        }
        return c;
    }
    public List<File> pegaTodosOsArquivos(String a){
        File f = new File(a);
        List<File> f3 = new ArrayList();
        for (File f2: f.listFiles()) {
            if(f2.isFile()){
                f3.add(f2);
            }
        }
        return f3;
    }
public void deletaArquivo(Arquivo arq){
        File f = new File(arq.getNome(), arq.getEndereco());
        f.delete();
    }
}