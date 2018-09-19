package com.informationsys.main;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import org.json.JSONObject;

public class RespostaHttp {
	
	
	public JSONObject respostaHttp(HttpURLConnection conn){
		
		try{
	        int statusCode = conn.getResponseCode();
	        
	        int nRead;
	        byte[] data = new byte[1024];
	        ByteArrayOutputStream buffer;
	        
	        String respostaServico="";
	        int codigoResposta = statusCode;
	        
	        // verifica se ocorreu algum erro na requisicao
	        if (statusCode != 200){
	            if (statusCode == 404) {
	                // quando o usuario nao existe no sistema, é retornado o
	                // erro Http 404
	            } else {
	                // Qualquer outro erro sera retornado aqui
	                buffer = new ByteArrayOutputStream();
	                
	                while((nRead = conn.getErrorStream().read(data,0,data.length)) != -1){
	                    buffer.write(data, 0, nRead);
	                }
	                
	                buffer.flush();
	                
	                respostaServico = "Ocorreu um erro Http: " + buffer.toString();
	            }
	        }else{
	        
	            buffer = new ByteArrayOutputStream();
	            
	            /*---
	             *  Lê as informações retornadas na requisição e coloca num stream de dados */
	            data = new byte[4096];
	            
	            while((nRead = conn.getInputStream().read(data,0,data.length)) != -1){
	                buffer.write(data, 0, nRead);
	            }
	            
	            buffer.flush();
	            /*
	             ---*/
	            
	            // Lê as informações retornadas no stream
	            respostaServico = buffer.toString();
	        }
	        
	        JSONObject retorno = new JSONObject();
	        retorno.put("resposta",respostaServico);
	        retorno.put("codigo", codigoResposta);
	        
	        return retorno;
	    }catch(Exception ex){
	    	
	        JSONObject ret = new JSONObject();
	        
	        ret.put("resposta", ex.getMessage());
	        ret.put("codigo", 0);
	        
	        return ret;
	    }
	}

}
