package com.greip.core.controller;

import com.greip.core.util.UtlDate;
import com.greip.core.constant.ConstantesCore;
import com.greip.core.dto.SeUsuarioDto;
import com.greip.core.service.SeUsuarioService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class BaseController {
    public static Logger log = Logger.getLogger(BaseController.class);

    @Autowired
    protected TokenStore tokenStore;
    @Autowired
    SeUsuarioService userService;

    public BaseController() {
        super();
    }

    //@PreAuthorize("#oauth2.hasScope('read')")
    //@PreAuthorize("hasAuthority('Sistemas')")
    @PreAuthorize("securityExpression('/users/extra')")
    @RequestMapping(method = RequestMethod.GET, value = "/users/extra")
    @ResponseBody
    public Map<String, Object> getExtraInfo(OAuth2Authentication auth) {
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        final OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
        //System.out.println(accessToken);
        //System.out.println("Solicitando Organizacion");
        return accessToken.getAdditionalInformation();
    }

    private static String getRequestRemoteAddr(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        return request.getRemoteAddr();
    }

    protected String baseObtenerLoginDelToken(OAuth2Authentication auth){
        String login = "";
        try {
            final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
            final OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
            login = auth.getName();
        }catch(Exception e){
            log.error(e);
        }
        return login;
    }

    protected void baseObtenerDatosAuditoria(OAuth2Authentication auth, int accion, Object obj){
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        final OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
        SeUsuarioDto usuarioDto = null;
        Class clazz = obj.getClass();
        try {
            usuarioDto = this.baseObtenerUsuarioPorLogin(auth.getName());
            /*Llenamos los datos al objecto enviado*/
            Class<?>[] params_date = new Class<?>[1];
            params_date[0] = Date.class;
            Class<?>[] params_objuser = new Class<?>[1];
            params_objuser[0] = SeUsuarioDto.class;
            Class<?>[] params_str = new Class<?>[1];
            params_str[0] = String.class;

            Object[] arguments_date = new Object[1];
            arguments_date[0] = UtlDate.obtenerFechaActualDate();
            Object[] arguments_user = new Object[1];
            arguments_user[0] = usuarioDto;
            Object[] arguments_str = new Object[1];
            arguments_str[0] = this.getRequestRemoteAddr();


            Method setFecha = clazz.getMethod("setFecha", params_date);
            setFecha.invoke(obj, arguments_date);
            Method setUsuarioDto = clazz.getMethod("setUsuarioDto", params_objuser);
            setUsuarioDto.invoke(obj, arguments_user);
            Method setTerminal = clazz.getMethod("setTerminal", params_str);
            setTerminal.invoke(obj, arguments_str);

            if(accion == ConstantesCore.ValoresPorDefecto.ACCION_NUEVO){
                Method setFechaCreacion = clazz.getMethod("setFechaCreacion", params_date);
                setFechaCreacion.invoke(obj, arguments_date);
                Method setUsuarioCreacionDto = clazz.getMethod("setUsuarioCreacionDto", params_objuser);
                setUsuarioCreacionDto.invoke(obj, arguments_user);
                Method setTerminalCreacion = clazz.getMethod("setTerminalCreacion", params_str);
                setTerminalCreacion.invoke(obj, arguments_str);
            }
        }catch (Exception e){
            log.error(e);
        }
    }

    protected void baseObtenerDatosAuditoriaNoObj(OAuth2Authentication auth, int accion, Object obj){
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        final OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
        SeUsuarioDto usuarioDto = null;
        Class clazz = obj.getClass();
        try {
            usuarioDto = this.baseObtenerUsuarioPorLogin(auth.getName());
            /*Llenamos los datos al objecto enviado*/
            Class<?>[] params_date = new Class<?>[1];
            params_date[0] = Date.class;
            Class<?>[] params_objuser = new Class<?>[1];
            params_objuser[0] = BigInteger.class;
            Class<?>[] params_str = new Class<?>[1];
            params_str[0] = String.class;

            Object[] arguments_date = new Object[1];
            arguments_date[0] = UtlDate.obtenerFechaActualDate();
            Object[] arguments_user = new Object[1];
            arguments_user[0] = usuarioDto.getId();
            Object[] arguments_str = new Object[1];
            arguments_str[0] = this.getRequestRemoteAddr();


            Method setFecha = clazz.getMethod("setFecha", params_date);
            setFecha.invoke(obj, arguments_date);
            Method setUsuarioDto = clazz.getMethod("setUsuario", params_objuser);
            setUsuarioDto.invoke(obj, arguments_user);
            Method setTerminal = clazz.getMethod("setTerminal", params_str);
            setTerminal.invoke(obj, arguments_str);

            if(accion == ConstantesCore.ValoresPorDefecto.ACCION_NUEVO){
                Method setFechaCreacion = clazz.getMethod("setFechaCreacion", params_date);
                setFechaCreacion.invoke(obj, arguments_date);
                Method setUsuarioCreacionDto = clazz.getMethod("setUsuarioCreacion", params_objuser);
                setUsuarioCreacionDto.invoke(obj, arguments_user);
                Method setTerminalCreacion = clazz.getMethod("setTerminalCreacion", params_str);
                setTerminalCreacion.invoke(obj, arguments_str);
            }
        }catch (Exception e){
            log.error(e);
        }
    }

    public SeUsuarioDto baseObtenerUsuarioPorLogin(String login){
        SeUsuarioDto seUsuarioDto = null;
        try {
            List<SeUsuarioDto> lst = userService.getByLoginEmail(login);
            if(lst!= null && !lst.isEmpty()){}
                seUsuarioDto = lst.get(0);
        }catch (Exception e){
            log.error(e);
        }
        return seUsuarioDto;
    }
}
