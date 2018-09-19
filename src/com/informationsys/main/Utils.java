package com.informationsys.main;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

import javax.ws.rs.core.UriBuilder;

public class Utils {
	
	  private Utils() {
		    throw new IllegalAccessError("Utility class");
		  }
	
	// Analisa CPF
	final static int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	
	//Inicia o ID
	private static int ID = 1;
	
	public static boolean validaCPF(String cpf) {
	      if ((cpf==null) || (cpf.length()!=11)) return false;

	      Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
	      Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
	      return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
	}
	
	public static String getLocalIP() throws UnknownHostException{
		return InetAddress.getLocalHost().getHostAddress();
	}
	   
	private static int calcularDigito(String str, int[] peso) {
	      int soma = 0;
	      for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
	         digito = Integer.parseInt(str.substring(indice,indice+1));
	         soma += digito*peso[peso.length-str.length()+indice];
	      }
	      soma = 11 - soma % 11;
	      return soma > 9 ? 0 : soma;
	}
	
	public static int getID(){
		
		return ID++;
	}
	
	//////////////////
	
	// Retorna a URI base da API
	
	public static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/APITeste").build();
	}
}
