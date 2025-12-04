package app;

import view.TelaPrincipal; // Importa a classe da tela que está no pacote view
import javax.swing.SwingUtilities; // Importa utilitário para rodar janelas sem travar

public class Main {
    
    public static void main(String[] args) {
        System.out.println("--- INICIANDO SISTEMA DE LAVANDERIA ---");

        // SwingUtilities.invokeLater é a maneira profissional de abrir janelas Java.
        // Ela garante que a tela abra suavemente sem travar o processamento.
        SwingUtilities.invokeLater(() -> {
            
            // Aqui ele cria e mostra a sua TelaPrincipal
            new TelaPrincipal();
            
        });
        
        System.out.println("Janela carregada. Aguardando interação do usuário...");
    }
}