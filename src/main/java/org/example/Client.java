package org.example;

public class Client {
    private final String name;
    private final int age;
    private final String cpf;

    public Client(String name, int age, String cpf) {
        this.name = name;
        this.age = age;
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }
}
