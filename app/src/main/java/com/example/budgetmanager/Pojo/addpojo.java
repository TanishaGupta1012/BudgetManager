
package com.example.budgetmanager.Pojo;

public class addpojo {
    private String expensename;
    private int id;

    public int getIndex() {
        return id;
    }

    public void setIndex(int index) {
        this.id = index;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String number;




    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getExpensename() {
        return expensename;
    }

    public void setExpensename(String expensename) {
        this.expensename = expensename;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String amount;

    public String getEmailentered() {
        return emailentered;
    }

    public void setEmailentered(String emailentered) {
        this.emailentered = emailentered;
    }

    private String emailentered;

}
