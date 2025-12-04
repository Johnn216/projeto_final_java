package view;

import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel; // Importante para a tabela
import java.awt.*;
import java.util.List;

public class TelaPrincipal extends JFrame {

    private JTextField txtNome;
    private JComboBox<String> comboTipo;
    private JTextField txtDetalhe;
    
    // TABELA (Substitui o JTextArea)
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    
    private Repositorio repositorio = new Repositorio();

    public TelaPrincipal() {
        setTitle("Sistema Lavanderia Pro - Gestão Completa");
        setSize(600, 500); // Aumentei um pouco
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Layout melhor para tabelas

        // --- PAINEL SUPERIOR (CADASTRO) ---
        JPanel painelCadastro = new JPanel(new GridLayout(4, 2, 5, 5));
        
        painelCadastro.add(new JLabel("  Nome do Cliente:"));
        txtNome = new JTextField();
        painelCadastro.add(txtNome);

        painelCadastro.add(new JLabel("  Tipo de Serviço:"));
        String[] opcoes = {"Roupa Comum", "Peça Pesada"};
        comboTipo = new JComboBox<>(opcoes);
        painelCadastro.add(comboTipo);

        painelCadastro.add(new JLabel("  Detalhe (Tipo/Peso):"));
        txtDetalhe = new JTextField();
        painelCadastro.add(txtDetalhe);

        JButton btnSalvar = new JButton("SALVAR NOVO PEDIDO");
        painelCadastro.add(btnSalvar);
        
        // Botão Limpar
        JButton btnLimpar = new JButton("Limpar Campos");
        painelCadastro.add(btnLimpar);

        add(painelCadastro, BorderLayout.NORTH);

        // --- PAINEL CENTRAL (TABELA) ---
        // Configura as colunas da tabela
        String[] colunas = {"ID", "Cliente", "Descrição", "Status", "Valor (R$)"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);
        
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // --- PAINEL INFERIOR (BOTÕES DE AÇÃO) ---
        JPanel painelAcoes = new JPanel();
        JButton btnExcluir = new JButton("Excluir Selecionado");
        JButton btnConcluir = new JButton("Marcar como PRONTO");
        JButton btnEntregar = new JButton("Marcar como ENTREGUE");
        
        painelAcoes.add(btnConcluir);
        painelAcoes.add(btnEntregar);
        painelAcoes.add(btnExcluir);
        
        add(painelAcoes, BorderLayout.SOUTH);

        // --- AÇÕES DOS BOTÕES ---
        btnSalvar.addActionListener(e -> salvarPedido());
        btnLimpar.addActionListener(e -> limparCampos());
        
        btnExcluir.addActionListener(e -> excluirItem());
        btnConcluir.addActionListener(e -> mudarStatus(Status.PRONTO));
        btnEntregar.addActionListener(e -> mudarStatus(Status.ENTREGUE));

        atualizarTabela();
        setVisible(true);
    }

    private void salvarPedido() {
        try {
            String nome = txtNome.getText();
            String detalhe = txtDetalhe.getText();
            String tipo = (String) comboTipo.getSelectedItem();

            ItemLavanderia item;
            if (tipo.equals("Roupa Comum")) {
                item = new Roupa(nome, detalhe);
            } else {
                double peso = Double.parseDouble(detalhe.replace(",", "."));
                item = new PecaPesada(nome, peso);
            }

            repositorio.salvar(item);
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Pedido Salvo!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }

    private void excluirItem() {
        // Pega a linha selecionada na tabela
        int linhaSelecionada = tabela.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido na tabela para excluir.");
            return;
        }

        // Pega o ID que está na coluna 0 daquela linha
        Long id = (Long) tabela.getValueAt(linhaSelecionada, 0);

        try {
            repositorio.excluir(id);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Item excluído!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
        }
    }

    private void mudarStatus(Status novoStatus) {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido na tabela.");
            return;
        }

        Long id = (Long) tabela.getValueAt(linhaSelecionada, 0);

        try {
            // 1. Busca o item no banco
            ItemLavanderia item = repositorio.buscarPorId(id);
            // 2. Muda o status na memória
            item.setStatus(novoStatus);
            // 3. Atualiza no banco
            repositorio.salvar(item);
            
            atualizarTabela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void atualizarTabela() {
        // Limpa a tabela visual
        modeloTabela.setRowCount(0);
        
        List<ItemLavanderia> lista = repositorio.listarTodos();

        for (ItemLavanderia item : lista) {
            // Adiciona uma linha visual para cada item do banco
            modeloTabela.addRow(new Object[]{
                item.getId(),
                item.getNomeDono(),
                item.getDescricao(),
                item.getStatus(),
                item.calcularPreco()
            });
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtDetalhe.setText("");
    }
}