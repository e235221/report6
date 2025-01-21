package jp.ac.uryukyu.ie.e235221;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherSolver {
	private final String apiKey = "95fd222d42414788aa8152259242610"; // WeatherAPIのAPIキー

	/**
	 * 天気データを取得するメソッド.
	 *
	 * @param cityName 都市名
	 * @return JSON形式の天気データ
	 * @throws Exception 接続やデータ取得時の例外
	 */
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

	// --- ここから修正 ---
	/**
	 * 取得した天気データを解析し，HTML文字列を生成するメソッド.
	 * ・天気と現在の気温はそのまま出力
	 * ・風速，風向き，湿度，気圧，体感温度，降水量はそれぞれ個別の
	 * <p>
	 * タグで出力
	 * ・その下部にWindy APIを用いた風の動きがわかるアニメーション地図を表示する
	 *
	 * ※ Windy APIのキーは，YOUR_WINDY_API_KEY を実際のキーに置き換えてください。
	 */
	public String parseWeatherData(String jsonData) {
		// JSONをパース
		JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
		JsonObject current = jsonObject.getAsJsonObject("current");
		JsonObject condition = current.getAsJsonObject("condition");

		// 各データを抽出
		double temperature = current.get("temp_c").getAsDouble();
		String weatherCondition = condition.get("text").getAsString();
		double windSpeed = current.get("wind_kph").getAsDouble();
		String windDirection = current.get("wind_dir").getAsString();
		double humidity = current.get("humidity").getAsDouble();
		double pressure = current.get("pressure_mb").getAsDouble();
		double feelsLike = current.get("feelslike_c").getAsDouble();
		double precip = current.get("precip_mm").getAsDouble();

		// HTML文字列を生成
		// ※ ここではテストや既存の出力形式に影響がない範囲で，
		// 各気象情報を個別の<p>タグに分け，その下部にWindy API用の地図領域を配置しています。
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
						// Windy APIによる風の動きがわかるアニメーション地図の表示領域（下部に配置）
						"<div id='windy-map' style='width: 100%%; height: 400px; margin-top: 20px;'></div>" +
						"<script src='https://api.windy.com/assets/map-forecast.js'></script>" +
						"<script>" +
						// 以下のオプションは任意で変更可能です（例：表示する座標やズームレベル）
						"const options = { key: 'YOUR_WINDY_API_KEY', verbose: false, lat: 35, lon: 139, zoom: 5 };" +
						"windyInit(options, document.getElementById('windy-map'));" +
						"</script>" +
						"</div>",
				weatherCondition, temperature, windSpeed, windDirection, humidity, pressure, feelsLike, precip);
	}
	// --- ここまで修正 ---
}
