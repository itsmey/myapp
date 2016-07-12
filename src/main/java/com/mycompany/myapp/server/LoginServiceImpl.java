package com.mycompany.myapp.server;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.myapp.client.application.login.LoginService;
import javax.security.auth.Subject;
import javax.servlet.ServletException;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
    final private static String contentEngineServerURI = "http://172.19.215.15:9080/wsi/FNCEWS40MTOM/";
    final private static String objectStoreID = "TAD";
    private static LoginServiceImpl instance;
    private Connection connection;
    private String login;
    private String password;
    private ObjectStore objectStore;

    public static LoginServiceImpl getInstance() {
        return instance;
    }

    public ObjectStore getObjectStore() {
        return objectStore;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        instance = this;
    }

    public void loginUser(String login, String password) {
        Connection connection = Factory.Connection.getConnection(contentEngineServerURI);
        this.login = login;
        this.password = password;
        this.connection = connection;
        pushSubject();
        try {
            Domain domain = Factory.Domain.getInstance(connection, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, objectStoreID, null);
            this.objectStore = objectStore;
        } finally {
            popSubject();
        }
    }

    public void pushSubject() {
        Subject subject = UserContext.createSubject(connection, login, password, null);
        UserContext.get().pushSubject(subject);
    }

    public void popSubject() {
        UserContext.get().popSubject();
    }
}