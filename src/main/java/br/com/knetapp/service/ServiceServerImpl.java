package br.com.knetapp.service;

import javax.jws.WebService;

@WebService()
public class ServiceServerImpl implements ServiceInterfaceWeb {
    	
  public String imprimir(String text) {	  
	  
	  /* Chama o mehod interno para impressao */
	  ServiceMethod method = new ServiceMethod();
	  String retorno = method.intimprimir(text);
	  
    return retorno;   
 }
   
 
}