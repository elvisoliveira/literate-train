package br.com.elvisoliveira.literatetrain.Model;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;

public class User implements ModelInterface {
    private Integer id;
    private ArrayList<SingleDigit> singleDigit = new ArrayList<>();

    @NotBlank(message = "Required field: Public key")
    protected String publicKey;

    @NotBlank(message = "Required field: Name")
    protected String name;

    @NotBlank(message = "Required field: Email")
    protected String email;

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setSingleDigit(ArrayList<SingleDigit> singleDigit) {
        this.singleDigit = singleDigit;
    }

    public void addSingleDigit(SingleDigit singleDigit) {
        this.singleDigit.add(singleDigit);
    }

    public ArrayList<SingleDigit> getSingleDigit() {
        return this.singleDigit;
    }
}