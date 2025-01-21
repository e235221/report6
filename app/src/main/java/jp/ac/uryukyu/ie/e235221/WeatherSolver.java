package jp.ac.uryukyu.ie.e235221;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 天気情報を取得・解析し，HTMLページを組み立てるクラス。
 */
public class WeatherSolver {
	private final String apiKey = "95fd222d42414788aa8152259242610";

	/**
	 * 指定した都市の天気データ(JSON形式)を取得するメソッド。
	 * 
	 * @param cityName 都市名
	 * @return JSON文字列(天気情報)
	 * @throws Exception 通信時の例外
	 */
	public String fetchWeatherData(String cityName) throws Exception {
		String endpoint = String.format(
				"http://api.weatherapi.com/v1/current.json?key=%s&q=%s",
				apiKey, cityName);
		URL url = new URL(endpoint);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		StringBuilder response = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();
		return response.toString();
	}

	/**
	 * JSON文字列を解析し，天気情報のHTML断片を生成するメソッド。
	 * 
	 * @param jsonData 天気APIから取得したJSON文字列
	 * @return HTMLのbody部分相当(天気情報を表示するdivなど)
	 */
	public String parseWeatherData(String jsonData) {
		// JSONをパース
		JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
		JsonObject current = jsonObject.getAsJsonObject("current");
		JsonObject condition = current.getAsJsonObject("condition");

		double temperature = current.get("temp_c").getAsDouble();
		String weatherCondition = condition.get("text").getAsString();
		double windSpeed = current.get("wind_kph").getAsDouble();
		String windDirection = current.get("wind_dir").getAsString();
		double humidity = current.get("humidity").getAsDouble();
		double pressure = current.get("pressure_mb").getAsDouble();
		double feelsLike = current.get("feelslike_c").getAsDouble();
		double precip = current.get("precip_mm").getAsDouble();

		// アイコン(emoji)はCSSの :before で付与される
		return String.format(
				"<div class='weather-info'>" +
						"<h2>現在の天気: <span class='weather-condition'>%s</span></h2>" +
						"<p class='temperature'>現在の気温: <span class='highlight-temp'>%.1f°C</span></p>" +
						"<div class='weather-details'>" +
						"<p>風速: <span class='wind-speed'>%.1f km/h</span></p>" +
						"<p>風向き: <span class='wind-dir'>%s</span></p>" +
						"<p>湿度: <span class='humidity'>%.1f%%</span></p>" +
						"<p>気圧: <span class='pressure'>%.1f hPa</span></p>" +
						"<p>体感温度: <span class='feels-like'>%.1f°C</span></p>" +
						"<p>降水量: <span class='precip'>%.1f mm</span></p>" +
						"</div>" +
						"<div id='windy-map' style='width: 100%%; height: 400px; margin-top: 20px;'></div>" +
						"<script src='https://api.windy.com/assets/map-forecast.js'></script>" +
						"<script>" +
						"const options = { key: 'YOUR_WINDY_API_KEY', verbose: false, lat: 35, lon: 139, zoom: 5 };" +
						"windyInit(options, document.getElementById('windy-map'));" +
						"</script>" +
						"</div>",
				weatherCondition, temperature, windSpeed, windDirection,
				humidity, pressure, feelsLike, precip);
	}

	/**
	 * JSON文字列を受け取り，HTMLページ全体を生成するメソッド。
	 * parseWeatherData()で生成した内容を，<html>～</html>で包む。
	 * 
	 * @param jsonData 天気APIから取得したJSON文字列
	 * @return 完全なHTMLページ
	 */
	public String generateWeatherPage(String jsonData) {
		// まず天気情報のメイン部分(断片HTML)を得る
		String weatherBody = parseWeatherData(jsonData);

		// ページとして完成させる
		// css/style.css を読み込む<link>を含む<head>を返す
		return String.format(
				"<!DOCTYPE html>" +
						"<html lang='ja'>" +
						"<head>" +
						"  <meta charset='UTF-8'>" +
						"  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
						"  <title>天気予報の結果</title>" +
						"  <link rel='stylesheet' href='/css/style.css'>" +
						"</head>" +
						"<body>" +
						"  <h1>天気予報の結果</h1>" +
						"  %s" +
						"</body>" +
						"</html>",
				weatherBody);
	}
}
