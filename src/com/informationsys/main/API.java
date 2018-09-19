package com.informationsys.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/")
public class API {
	
	// Loggers
	private static final Logger logger = LoggerFactory.getLogger(API.class);
	private static final String SWAGGER_LOCALE = "Docs/swagger.json";
	private static final String ACCESS_CONTROL = "Access-Control-Allow-Origin";
	
	@GET
	@Path("/info")
	public Response getVersion(@Context HttpServletRequest req){

		//Para testar em sua maquina, altere o arquivo swagger para ter o seu IP em WebContent/WEB-INF/classes/Docs

		StringBuilder teste = new StringBuilder();
		Scanner input;
		JSONObject json = new JSONObject();
		try {
			File file = new File(this.getClass().getResource("/").toURI().getPath() + SWAGGER_LOCALE);
			input = new Scanner(file,"UTF-8");
			logger.info("Arquivo obtido: {}", file.getAbsolutePath());
			while (input.hasNextLine())
			{
			   teste = teste.append(input.nextLine());
			}
			input.close();
			json = new JSONObject(teste.toString());
			json.put("host", Utils.getLocalIP() + ":" + req.getLocalPort());
		} catch (FileNotFoundException e) {
			logger.error("Erro ao obter a versao:" + e);
		} catch (UnknownHostException he) {
			logger.error("Erro ao obter o ip local:" + he);
		} catch (URISyntaxException e) {
			logger.error("Erro ao obter o caminho local:" + e);
		}
		//return Response.status(200).header(ACCESS_CONTROL, "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, OPTIONS").header("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization, *").entity(teste.substring(0, teste.length())).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
		return Response.status(200).header(ACCESS_CONTROL, "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, OPTIONS").header("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization, *").entity(json.toString()).encoding("UTF-8").type(MediaType.APPLICATION_JSON).build();
	}
}