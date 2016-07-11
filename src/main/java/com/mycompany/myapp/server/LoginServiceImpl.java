package com.mycompany.myapp.server;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.myapp.client.application.login.LoginService;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
    final private String contentEngineServerURI = "http://172.19.215.15:9080/wsi/FNCEWS40MTOM/";
    final private String objectStoreID = "TAD";

    public void loginUser(String login, String password) {
        Connection connection = Factory.Connection.getConnection(contentEngineServerURI);
        UserContext.get().pushSubject(
                UserContext.createSubject(connection, login, password, null) //IDEA complains on direct use of Subject
        );
        try {
            Domain domain = Factory.Domain.getInstance(connection, null);
            ObjectStore objectStore = Factory.ObjectStore.fetchInstance(domain, objectStoreID, null);
        } finally {
            UserContext.get().popSubject();
        }
    }

    public void logoutUser() {
        UserContext.get().popSubject();
    }
}