package com.information.todos;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Path("/")
public class TodosAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(TodosAPI.class);
	private static final String ACCESS_CONTROL = "Access-Control-Allow-Origin";
	
	@GET
	@Path("/segmentation/Todos")
	@Consumes("application/x-www-form-urlencoded")
	public Response consulta(@Context HttpServletRequest req,	@QueryParam("x") String x) {

		Response resposta = null;

		String uniqueID = UUID.randomUUID().toString();
		logger.info("{} - Solicitacao de consultar cpf ou cnpj pelo Usuario: {}", uniqueID, x);

		Todos todos = Todos.getinstance();
		JSONObject retorno = todos.consultaTodos(x);
		logger.info("{} - Response obtido: {}", uniqueID, retorno);
		resposta = Response.status(200).header(ACCESS_CONTROL, "*").entity(retorno.toString()).encoding("utf-8").build();

		return resposta;
	}
}
