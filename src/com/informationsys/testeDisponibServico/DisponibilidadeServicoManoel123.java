package com.informationsys.testeDisponibServico;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.informationsys.main.RespostaHttp;

public class DisponibilidadeServicoManoel123 {

	private static DisponibilidadeServicoManoel123 instance = null;

	public static DisponibilidadeServicoManoel123 getInstance() {
		if (instance == null) {
			instance = new DisponibilidadeServicoManoel123();
		}
		return instance;
	}

	public JSONObject prodDispServ(String x) {
		RespostaHttp resposta = new RespostaHttp();
		int id = 0;
		String title = "";
		int userId = 0;

		int statusCode = 0;

		HttpURLConnection conn = null;

		try {
			
			String url = "https://jsonplaceholder.typicode.com/todos/"+ x;

			URL cURL = new URL(url);

			/* Abre conexão com a URL informada */
			conn = (HttpURLConnection) cURL.openConnection();

			/* Monta o Header da requisicao */
			conn.addRequestProperty("content-type", "application/json");

			/*
			 * Trata retorno HTTP para saber se houve um erro na requisição ou se retornou o
			 * esperado.
			 */
			JSONObject retornoHttp = resposta.respostaHttp(conn);

			/* Fecha conexão */
			conn.disconnect();

			statusCode = (Integer) retornoHttp.get("codigo");

			System.out.println(retornoHttp);

			JSONObject jsonObject = new JSONObject((String) retornoHttp.get("resposta"));

			id = jsonObject.getInt("id");
			title = jsonObject.getString("title");
			userId = jsonObject.getInt("userId");
		

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

		JSONObject result = new JSONObject();
		if (statusCode == 200) {
			DisponibilidadeServicoManoel123_Result response = new DisponibilidadeServicoManoel123_Result(200, id, title, userId);
			result = response.getObject();
		} else {
			DisponibilidadeServicoManoel123_Result response = new DisponibilidadeServicoManoel123_Result(202, id, title, userId);
			result = response.getObject();			
		}

		return result;

	}

	class DisponibilidadeServicoManoel123_Result {

		private int statusCode;
		private int id;
		private String title;
		private int userId;

		DisponibilidadeServicoManoel123_Result(int statusCode, int id, String title, int userId) {
			this.id = id;
			this.title = title;
			this.userId = userId;
		}

		public JSONObject getObject() {

			JSONObject result = new JSONObject();
			result.put("statusCode", statusCode);
			result.put("id", id);
			result.put("title", title);
			result.put("userId", userId);
			return result;
		}
	}
}
