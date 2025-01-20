package jp.ac.uryukyu.ie.e235221;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherSolver {
	private final String apiKey = "95fd222d42414788aa8152259242610"; // WeatherAPIのAPIキーを設定

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

	public String parseWeatherData(String jsonData) {
		int tempIndex = jsonData.indexOf("\"temp_c\":") + 9;
		int tempEnd = jsonData.indexOf(",", tempIndex);
		String temperature = jsonData.substring(tempIndex, tempEnd);

		int conditionIndex = jsonData.indexOf("\"text\":\"") + 8;
		int conditionEnd = jsonData.indexOf("\"", conditionIndex);
		String condition = jsonData.substring(conditionIndex, conditionEnd);

		return String.format("現在の気温: %s°C, 天気: %s", temperature, condition);
	}

	// mainメソッドを追加
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("都市名を入力してください: ");
		String cityName = scanner.nextLine();

		WeatherSolver weatherSolver = new WeatherSolver();
		try {
			String jsonData = weatherSolver.fetchWeatherData(cityName);
			String weather = weatherSolver.parseWeatherData(jsonData);
			System.out.println(weather);
		} catch (Exception e) {
			System.out.println("エラーが発生しました: " + e.getMessage());
		}
		scanner.close();
	}
}
