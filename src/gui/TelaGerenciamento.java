package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Academia;
import model.Aluno;
import model.Aparelho;
import model.Instrutor;
import model.Treino;
import model.Usuario;

public class TelaGerenciamento extends JFrame implements ActionListener {

    private Academia academia;
    private JComboBox<String> cbItens;
    private JButton btnEditar, btnVoltar;
    private String tipoItem; 
    
    public TelaGerenciamento(Academia academia, String tipoItem) {
        super("Gerenciar " + tipoItem.substring(0, 1).toUpperCase() + tipoItem.substring(1));
        this.academia = academia;
        this.tipoItem = tipoItem;

        setSize(500, 200);
        setLayout(new BorderLayout(10, 10));

        JPanel painelSuperior = new JPanel(new GridLayout(1, 2, 10, 10));
        cbItens = new JComboBox<>();
        

        painelSuperior.add(new JLabel("Selecionar " + tipoItem.substring(0, 1).toUpperCase() + tipoItem.substring(1) + ":"));
        painelSuperior.add(cbItens);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnEditar = new JButton("Editar Selecionado"); 
        btnVoltar = new JButton("Voltar");
        
        btnEditar.addActionListener(this);
        btnVoltar.addActionListener(this);
        
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnVoltar);

        add(painelSuperior, BorderLayout.NORTH);
        add(painelBotoes, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
       
        carregarItens(); 
        
        setVisible(true);
    }
    
    private void carregarItens() {
        cbItens.removeAllItems();

        if (tipoItem.equalsIgnoreCase("aluno")) {
            for (int i = 0; i < academia.countAluno(); i++) {
                Aluno a = academia.getAluno(i);
                cbItens.addItem(a.getNome() + " (ID: " + a.getId() + ")");
            }
        } else if (tipoItem.equalsIgnoreCase("instrutor")) {
            for (int i = 0; i < academia.countInstrutor(); i++) {
                Instrutor ins = academia.getInstrutor(i);
                cbItens.addItem(ins.getNome() + " (ID: " + ins.getId() + ")");
            }
        } else if (tipoItem.equalsIgnoreCase("aparelho")) {
            for (int i = 0; i < academia.countAparelho(); i++) {
                Aparelho ap = academia.getAparelho(i);
                cbItens.addItem(ap.getNome());
            }
        } else if (tipoItem.equalsIgnoreCase("usuario")) {
            for (int i = 0; i < academia.countUsuario(); i++) {
                Usuario u = academia.getUsuario(i);
                cbItens.addItem(u.getLogin() + " (" + u.getTipo() + ")");
            }
        } else if (tipoItem.equalsIgnoreCase("treino")) {
            int treinoCount = 0;
            for (int i = 0; i < academia.countAluno(); i++) {
                Aluno a = academia.getAluno(i);
                for (int j = 0; j < a.countTreino(); j++) {
                    Treino t = a.getTreino(j);
                    cbItens.addItem(t.getNome() + " (" + a.getNome() + " - ID:" + a.getId() + ")");
                    treinoCount++;
                }
            }
            if (treinoCount == 0 && academia.countAluno() > 0) {
                 cbItens.addItem("Alunos cadastrados, mas sem treinos.");
            }
        }
        
        if (cbItens.getItemCount() == 0 || cbItens.getSelectedItem().toString().contains("Nenhum item") || cbItens.getSelectedItem().toString().contains("sem treinos")) {
            if (cbItens.getItemCount() == 0) {
                 cbItens.addItem("Nenhum item cadastrado.");
            }
         
            btnEditar.setEnabled(false); 
        } else {
            btnEditar.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnVoltar) {
            this.dispose();
            return;
        } else if (e.getSource() == btnEditar) {
            if (cbItens.getSelectedItem() == null || cbItens.getSelectedItem().toString().contains("Nenhum item") || cbItens.getSelectedItem().toString().contains("sem treinos")) {
                JOptionPane.showMessageDialog(this, "Selecione um item válido para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String itemSelecionado = (String) cbItens.getSelectedItem();
            
            this.dispose(); 

            if (tipoItem.equalsIgnoreCase("aluno")) {
                int id = extrairId(itemSelecionado);
                Aluno a = buscarAlunoPorId(id);
                if (a != null) { 
                    new TesteCadastroeEdicaoAluno(academia, a);
                }
            } else if (tipoItem.equalsIgnoreCase("instrutor")) {
                int id = extrairId(itemSelecionado);
                Instrutor ins = buscarInstrutorPorId(id);
                if (ins != null) { 
                    new TelaInstrutor(academia, ins); 
                }

            } else if (tipoItem.equalsIgnoreCase("aparelho")) {
                String nome = itemSelecionado; 
                Aparelho ap = buscarAparelhoPorNome(nome);
                if (ap != null) { 
                    new TelaCadastroAparelho(academia, ap); 
                }

            } else if (tipoItem.equalsIgnoreCase("usuario")) {
                String login = extrairLogin(itemSelecionado);
                Usuario u = buscarUsuarioPorLogin(login);
                if (u != null) { 
                    new TelaCadastroUsuario(academia, u); 
                }
            } else if (tipoItem.equalsIgnoreCase("treino")) {
                String nomeAluno = extrairNomeAlunoDoTreino(itemSelecionado);
                String nomeTreino = extrairNomeTreino(itemSelecionado);
                
                Aluno a = buscarAlunoPorNome(nomeAluno); 
                if (a != null) {
                    Treino t = buscarTreinoPorNome(a, nomeTreino);
                    if (t != null) { 
                        new TelaEdicaoTreino(academia, t); 
                    }
                }
            }
        }
    }
    
    // --- MÉTODOS AUXILIARES DE BUSCA ---
    
    private int extrairId(String item) {
        try {
            int idInicio = item.indexOf("ID:") + 3; 
            int idFim = item.indexOf(")", idInicio);
            if (idInicio < 0 || idFim < 0) {
                 return -1;
            }
            return Integer.parseInt(item.substring(idInicio, idFim).trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private String extrairLogin(String item) {
        try {
            int fim = item.indexOf(" (");
            return item.substring(0, fim).trim();
        } catch (Exception e) {
            return null;
        }
    }
    
    private String extrairNomeAlunoDoTreino(String item) {
         try {
            int inicio = item.indexOf("(") + 1;
            int fim = item.indexOf(" - ID:");
            return item.substring(inicio, fim).trim();
        } catch (Exception e) {
            return null;
        }
    }
    
    private String extrairNomeTreino(String item) {
         try {
            int fim = item.indexOf(" (");
            return item.substring(0, fim).trim();
        } catch (Exception e) {
            return null;
        }
    }

    private Aluno buscarAlunoPorId(int id) {
        for (int i = 0; i < academia.countAluno(); i++) {
            if (academia.getAluno(i).getId() == id) return academia.getAluno(i);
        }
        return null;
    }
    
    private Aluno buscarAlunoPorNome(String nome) {
        for (int i = 0; i < academia.countAluno(); i++) {
            if (academia.getAluno(i).getNome().equals(nome)) return academia.getAluno(i);
        }
        return null;
    }
    
    private Instrutor buscarInstrutorPorId(int id) {
        for (int i = 0; i < academia.countInstrutor(); i++) {
            if (academia.getInstrutor(i).getId() == id) return academia.getInstrutor(i);
        }
        return null;
    }

    private Aparelho buscarAparelhoPorNome(String nome) {
        for (int i = 0; i < academia.countAparelho(); i++) {
            if (academia.getAparelho(i).getNome().equals(nome)) return academia.getAparelho(i);
        }
        return null;
    }
    
    private Usuario buscarUsuarioPorLogin(String login) {
        for (int i = 0; i < academia.countUsuario(); i++) {
            if (academia.getUsuario(i).getLogin().equals(login)) return academia.getUsuario(i);
        }
        return null;
    }

    private Treino buscarTreinoPorNome(Aluno a, String nomeTreino) {
        for (int i = 0; i < a.countTreino(); i++) {
            if (a.getTreino(i).getNome().equals(nomeTreino)) return a.getTreino(i);
        }
        return null;
    }
}