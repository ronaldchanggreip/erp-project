package com.greip.core.service;

import com.greip.core.dto.GeCorreoDto;
import com.greip.core.exception.ServiceException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * Created by esvr on 29/03/17.
 */
public interface GeCorreoElectronicoService {

    public int enviar(GeCorreoDto correoDto) throws ServiceException, UnsupportedEncodingException, MessagingException;
}
