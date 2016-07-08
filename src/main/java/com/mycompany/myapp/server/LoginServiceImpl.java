package com.mycompany.myapp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.myapp.client.application.login.LoginService;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
    public void loginUser(String login, String password) {
    }
}