package br.com.knetapp.service;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService()
@SOAPBinding(style = Style.DOCUMENT)
public interface ServiceInterfaceWeb {
	
  @WebMethod String imprimir(String text); // Imprimir

  
}