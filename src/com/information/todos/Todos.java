package com.information.todos;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import com.informationsys.main.RespostaHttp;

public class Todos {

	// Garante que uma classe seja criada uma única vez - getinstance()
	private static Todos instance = null;

	public static Todos getinstance() {
		if (instance == null) {
			instance = new Todos();
		}
		return instance;
	}

	public JSONObject consultaTodos(String x) {
		RespostaHttp resposta = new RespostaHttp();
		int userId = 0;
		int id = 0;
		String title = "";
		Boolean completed = false;

		int statusCode = 0;

		HttpsURLConnection conn = null;


		try {

			String url = "https://jsonplaceholder.typicode.com/todos/" + x;

			URL cURL = new URL(url);

			// Abre conexão com a URL informada.
			conn = (HttpsURLConnection) cURL.openConnection();

			/* Monta o Header da requisicao */
			conn.addRequestProperty("content-type", "application/json");

			// Consulta Metodo que retorna a resposta da pagina consultada.
			JSONObject retornoHttp = resposta.respostaHttp(conn);

			conn.disconnect();

			statusCode = (Integer) retornoHttp.getInt("codigo");

			JSONObject jsonObject = new JSONObject((String) retornoHttp.getString("resposta"));


			userId = jsonObject.getInt("userId");
			id = jsonObject.getInt("id");
			title = jsonObject.getString("title");
			completed = jsonObject.getBoolean("completed");

		} catch (Exception e) {
			System.out.println(e);
		}

		JSONObject result = new JSONObject();
		if (statusCode == 200) {
			Todos_result response = new Todos_result(statusCode,userId, id, title, completed);
			result = response.getObject();
		}

		return result;
	}

	class Todos_result {
		private int statusCode;
		private int userId;
		private int id;
		private String title;
		private Boolean completed;

		Todos_result(int statusCode,int userId, int id, String title, boolean completed) {
			this.statusCode = statusCode;
			this.userId = userId;
			this.id = id;
			this.title = title;
			this.completed = completed;
		}
		
		public JSONObject getObject() {
			
			JSONObject result = new JSONObject();
			result.put("statusCode", statusCode);
			result.put("userId", userId);
			result.put("id", id);
			result.put("title", title);
			result.put("completed", completed);
			return result;
			
		}
	}

}
