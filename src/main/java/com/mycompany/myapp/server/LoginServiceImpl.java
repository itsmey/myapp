package com.mycompany.myapp.server;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.myapp.client.application.login.LoginService;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
    final private String contentEngineServerURI = "http://172.19.215.6:9080/wsi/FNCEWS40MTOM/";
    final private String objectStoreID = "OSMI_OLD";

    public void loginUser(String login, String password) {
        Connection connection = Factory.Connection.getConnection(contentEngineServerURI);
        UserContext userContext = UserContext.get();
        userContext.pushSubject(UserContext.createSubject(connection, login, password, null));
        try {
            Domain domain = Factory.Domain.getInstance(connection, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, objectStoreID, null);
        } finally {
            userContext.popSubject(); //TODO: decide when to close the user session
        }
    }
}