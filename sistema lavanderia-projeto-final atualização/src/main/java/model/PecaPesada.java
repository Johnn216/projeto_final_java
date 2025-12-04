package model;

import jakarta.persistence.Entity; // <--- IMPORTANTE

@Entity // <--- SE FALTAR ISSO, DÁ O ERRO "Unable to locate persister"
public class PecaPesada extends ItemLavanderia {
    
    private double pesoKg;

    // CONSTRUTOR VAZIO (OBRIGATÓRIO)
    public PecaPesada() {}

    public PecaPesada(String nomeDono, double pesoKg) {
        super(nomeDono);
        this.pesoKg = pesoKg;
    }

    @Override
    public double calcularPreco() {
        return pesoKg * 10.0; 
    }

    @Override
    public String getDescricao() {
        return "Pesado: " + pesoKg + "kg";
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(double pesoKg) {
        this.pesoKg = pesoKg;
    }
}