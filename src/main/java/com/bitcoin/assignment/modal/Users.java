package com.bitcoin.assignment.modal;

import javax.persistence.*;

@Entity
@Table()
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String Name;
    public String Email;
    public String Password;

    public Users() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
