package jp.ac.uryukyu.ie.e235221;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class WeatherSolverTest {
	@Test
	public void testParseWeatherData() throws Exception {
		// WeatherSolverのインスタンスを作成
		WeatherSolver solver = new WeatherSolver();
		// fetchWeatherDataメソッドをモックするためspyを作成
		WeatherSolver spySolver = spy(solver);
		String mockJsonData = "{\"current\": {\"temp_c\": 22.5, \"condition\": {\"text\": \"晴れ\"}, " +
				"\"wind_kph\": 10.5, \"wind_dir\": \"北\", \"humidity\": 60, \"pressure_mb\": 1012, " +
				"\"feelslike_c\": 20.0, \"precip_mm\": 0.0}}";
		// モック設定
		when(spySolver.fetchWeatherData("okinawa")).thenReturn(mockJsonData);

		// テスト対象メソッドの実行
		String result = spySolver.parseWeatherData(mockJsonData);

		// テストで期待するHTML文字列
		String expected = "<div class='weather-info'>" +
				"<h2>現在の天気: <span class='weather-condition'>晴れ</span></h2>" +
				"<p class='temperature'>現在の気温: <span class='highlight-temp'>22.5°C</span></p>" +
				"<div class='weather-details'>" +
				"<p>風速: <span class='wind-speed'>10.5 km/h</span></p>" +
				"<p>風向き: <span class='wind-dir'>北</span></p>" +
				"<p>湿度: <span class='humidity'>60.0%</span></p>" +
				"<p>気圧: <span class='pressure'>1012.0 hPa</span></p>" +
				"<p>体感温度: <span class='feels-like'>20.0°C</span></p>" +
				"<p>降水量: <span class='precip'>0.0 mm</span></p>" +
				"</div>" +
				"<div id='windy-map' style='width: 100%; height: 400px; margin-top: 20px;'></div>" +
				"<script src='https://api.windy.com/assets/map-forecast.js'></script>" +
				"<script>" +
				"const options = { key: 'iN5NURX99S9qmVv59uaAX7q2NOC47pj3', verbose: false, lat: 35, lon: 139, zoom: 5 };"
				+
				"windyInit(options, document.getElementById('windy-map'));" +
				"</script>" +
				"</div>";

		assertNotNull(result);
		assertEquals(expected, result);
	}
}
