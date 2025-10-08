package model;

public class Customer extends User{

    public Customer(){
        super();
    }

    public Customer(int id, String username, String password, String phone, String email, String fullname, String role){
        super(id, username, password, phone, email, fullname, role);
    }
}
