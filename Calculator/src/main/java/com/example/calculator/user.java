package com.example.calculator;

public class user {
    int id, result;
    String exp;
    public user(int id, String exp, int result) {
        this.id = id;
        this.exp = exp;
        this.result = result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public int getId() {
        return id;
    }

    public int getResult() {
        return result;
    }

    public String getExp() {
        return exp;
    }
}
