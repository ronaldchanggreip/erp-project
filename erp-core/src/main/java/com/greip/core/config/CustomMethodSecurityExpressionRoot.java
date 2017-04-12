package com.greip.core.config;

import com.greip.core.service.SeUsuarioService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

/**
 * Created by HvivesO on 07/01/2017.
 */
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations{

    private Object filterObject;
    private Object returnObject;
    private Object target;
    private SeUsuarioService daoUser;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean securityExpression(String api) {
        User user = (User) this.getPrincipal();

        //System.out.println("USUARIO : " + user.getUsername() + " - API : " + api);
        return true;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return target;
    }

    public void setDaoUser(SeUsuarioService daoUser) {
        this.daoUser = daoUser;
    }
}
