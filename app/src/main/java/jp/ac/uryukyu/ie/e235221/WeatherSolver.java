package jp.ac.uryukyu.ie.e235221;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherSolver {
	private final String apiKey = "95fd222d42414788aa8152259242610"; // WeatherAPIのAPIキーを設定

	// 天気データを取得するメソッド
	public String fetchWeatherData(String cityName) throws Exception {
		String endpoint = String.format("http://api.weatherapi.com/v1/current.json?key=%s&q=%s", apiKey, cityName);
		URL url = new URL(endpoint);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder response = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();
		return response.toString();
	}

	// 取得した天気データを解析するメソッド
	public String parseWeatherData(String jsonData) {
		JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
		double temperature = jsonObject.getAsJsonObject("current").get("temp_c").getAsDouble();
		String condition = jsonObject.getAsJsonObject("current").getAsJsonObject("condition").get("text").getAsString();
		return String.format("現在の気温: %.1f°C, 天気: %s", temperature, condition);
	}

	// mainメソッドを追加
	public static void main(String[] args) {
		try {
			// ユーザーから都市名を入力
			java.util.Scanner scanner = new java.util.Scanner(System.in);
			System.out.print("都市名を入力してください: ");
			String cityName = scanner.nextLine();

			// WeatherSolverインスタンスを作成
			WeatherSolver solver = new WeatherSolver();

			// 天気データを取得
			String weatherData = solver.fetchWeatherData(cityName);

			// 取得したデータを解析
			String weather = solver.parseWeatherData(weatherData);
			System.out.println(weather);

			scanner.close();
		} catch (Exception e) {
			System.out.println("エラーが発生しました: " + e.getMessage());
		}
	}
}
