
package com.vangasuthalam.service;

import com.vangasuthalam.dao.CustomerLoginDAO;

public class CustomerLoginService {

    private CustomerLoginDAO customerLoginDAO;

    public CustomerLoginService() {

        customerLoginDAO = new CustomerLoginDAO();
    }

    public int loginCustomer(String email, String password) {

        if (email == null || email.trim().isEmpty()) {
            return 0;
        }

        if (password == null || password.trim().isEmpty()) {
            return 0;
        }

        return customerLoginDAO.loginCustomer(email, password);
    }
}

