package model;

import jakarta.persistence.Entity; // <--- IMPORTANTE

@Entity // <--- SE FALTAR ISSO, DÁ O ERRO "Unable to locate persister"
public class Roupa extends ItemLavanderia {
    
    private String tipoRoupa; 

    // CONSTRUTOR VAZIO (OBRIGATÓRIO)
    public Roupa() {}

    public Roupa(String nomeDono, String tipoRoupa) {
        super(nomeDono); 
        this.tipoRoupa = tipoRoupa;
    }

    @Override
    public double calcularPreco() {
        if ("Terno".equalsIgnoreCase(tipoRoupa)) {
            return 50.00;
        }
        return 20.00;
    }

    @Override
    public String getDescricao() {
        return "Roupa: " + tipoRoupa;
    }

    // Getters e Setters específicos
    public String getTipoRoupa() {
        return tipoRoupa;
    }

    public void setTipoRoupa(String tipoRoupa) {
        this.tipoRoupa = tipoRoupa;
    }
}