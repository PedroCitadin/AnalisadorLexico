
package model.util;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.bean.Token;

/**
 *
 * @author Pedro Citadin Coelho
 */
public class TokenTableModel extends AbstractTableModel {
        private List<Token> tokens;
     private String[] colunas = new String[]{
        "Código","Palavra"};

    /** Creates a new instance of DevmediaTableModel */
    public TokenTableModel(List<Token> tokens) {
        this.tokens = tokens;
    }

    public TokenTableModel(){
     this.tokens = new ArrayList<Token>();
    }

    public int getRowCount() {
        return tokens.size();
    }

    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int columnIndex){
      return colunas[columnIndex];
    }

     @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }


    public void setValueAt(Token aValue, int rowIndex) {
        Token token = tokens.get(rowIndex);

        token.setCod(aValue.getCod());
        token.setSimbolo(aValue.getSimbolo());
        

        fireTableCellUpdated(rowIndex, 0);
        fireTableCellUpdated(rowIndex, 1);
        

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      Token token = tokens.get(rowIndex);

     switch (columnIndex) {
         case 0:
             token.setCod(Integer.parseInt( aValue.toString()));
         case 1:
             token.setSimbolo(aValue.toString());
         

         default:
             System.err.println("Índice da coluna inválido");
     }
     fireTableCellUpdated(rowIndex, columnIndex);
     }


    public Object getValueAt(int rowIndex, int columnIndex) {
        Token tokenSelecionado = tokens.get(rowIndex);
        String valueObject = null;
        switch(columnIndex){
            case 0: valueObject = String.valueOf(tokenSelecionado.getCod()); break;
            case 1: valueObject = tokenSelecionado.getSimbolo(); break;
            
            default: System.err.println("Índice inválido para propriedade do bean Usuario.class");
        }

        return valueObject;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }


    public Token getToken(int indiceLinha) {
        return tokens.get(indiceLinha);
    }

    public void addToken(Token u) {
        tokens.add(u);


        int ultimoIndice = getRowCount() - 1;

        fireTableRowsInserted(ultimoIndice, ultimoIndice);
    }


    public void removeUsuario(int indiceLinha) {
        tokens.remove(indiceLinha);

        fireTableRowsDeleted(indiceLinha, indiceLinha);
    }


    public void addListaDeUsuarios(List<Token> novosUsuarios) {

        int tamanhoAntigo = getRowCount();
        tokens.addAll(novosUsuarios);
        fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
    }

    public void limpar() {
        tokens.clear();
        fireTableDataChanged();
    }

    public boolean isEmpty() {
        return tokens.isEmpty();
    }

}
