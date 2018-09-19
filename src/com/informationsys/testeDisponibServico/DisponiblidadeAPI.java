package com.informationsys.testeDisponibServico;

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
public class DisponiblidadeAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(DisponiblidadeAPI.class);
	private static final String ACCESS_CONTROL = "Access-Control-Allow-Origin";
	
	@GET
	@Path("/segmentation/DispServManoel")
	@Consumes("application/x-www-form-urlencoded")
	public Response consulta(@Context HttpServletRequest req,	@QueryParam("x") String x) {

		Response resposta = null;

		String uniqueID = UUID.randomUUID().toString();
		logger.info("{} - Solicitacao de consultar cpf ou cnpj pelo Usuario: {}", uniqueID, x);

		DisponibilidadeServicoManoel123 disponibilidadeServicoManoel123 = DisponibilidadeServicoManoel123.getInstance();
		JSONObject retorno = disponibilidadeServicoManoel123.prodDispServ(x);
		logger.info("{} - Response obtido: {}", uniqueID, retorno);
		resposta = Response.status(200).header(ACCESS_CONTROL, "*").entity(retorno.toString()).encoding("utf-8").build();

		return resposta;
	}
}
