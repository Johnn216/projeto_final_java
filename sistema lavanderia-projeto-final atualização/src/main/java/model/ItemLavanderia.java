package model;

import jakarta.persistence.*;

@Entity // <--- OBRIGATÓRIO
@Inheritance(strategy = InheritanceType.JOINED) // <--- CRIA TABELAS PARA OS FILHOS
public abstract class ItemLavanderia implements Financeiro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeDono;
    
    @Enumerated(EnumType.STRING)
    private Status status;

    // CONSTRUTOR VAZIO (OBRIGATÓRIO PRO HIBERNATE)
    public ItemLavanderia() {}

    public ItemLavanderia(String nomeDono) {
        this.nomeDono = nomeDono;
        this.status = Status.RECEBIDO;
    }

    public abstract String getDescricao();

    // --- GETTERS E SETTERS ---
    // (O erro 'cannot find symbol getId' some com isso aqui)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDono() {
        return nomeDono;
    }
    
    public void setNomeDono(String nomeDono) {
        this.nomeDono = nomeDono;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}