package jp.ac.uryukyu.ie.e235221;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * WeatherSolverクラスのテスト。
 * fetchWeatherData(), parseWeatherData(), generateWeatherPage()の動作を検証する。
 */
public class WeatherSolverTest {

	/**
	 * parseWeatherData()メソッドのテスト。
	 */
	@Test
	public void testParseWeatherData() throws Exception {
		// WeatherSolverのインスタンスを作成
		WeatherSolver solver = new WeatherSolver();
		// fetchWeatherData()をモック化する
		WeatherSolver spySolver = spy(solver);

		String mockJsonData = "{\"current\": {\"temp_c\": 22.5, \"condition\": {\"text\": \"晴れ\"}, " +
				"\"wind_kph\": 10.5, \"wind_dir\": \"北\", \"humidity\": 60, \"pressure_mb\": 1012, " +
				"\"feelslike_c\": 20.0, \"precip_mm\": 0.0}}";

		// spyに対し，「okinawa」で呼ばれたらmockJsonDataを返すように
		when(spySolver.fetchWeatherData("okinawa")).thenReturn(mockJsonData);

		// 実際にparseWeatherData()を呼ぶ(ここでは直接呼ぶ or spySolverで呼ぶでもOK)
		String result = spySolver.parseWeatherData(mockJsonData);

		assertNotNull(result);
		// HTML断片に想定の要素が含まれるか
		assertTrue(result.contains("wind-speed")); // 風速
		assertTrue(result.contains("wind-dir")); // 風向き
		assertTrue(result.contains("晴れ")); // condition
	}

	/**
	 * generateWeatherPage()メソッドのテスト。
	 * JSON文字列→ページ全体(<!DOCTYPE html> ... )が生成されるか検証。
	 */
	@Test
	public void testGenerateWeatherPage() {
		WeatherSolver solver = new WeatherSolver();

		// テスト用のJSON(最小限でOK)
		String mockJsonData = "{\"current\": {\"temp_c\": 25.5, \"condition\": {\"text\": \"Sunny\"}, " +
				"\"wind_kph\": 10.0, \"wind_dir\": \"E\", \"humidity\": 50, \"pressure_mb\": 1013, " +
				"\"feelslike_c\": 25.0, \"precip_mm\": 0.0}}";

		// generateWeatherPage()を呼ぶ
		String fullHtml = solver.generateWeatherPage(mockJsonData);

		assertNotNull(fullHtml);
		assertTrue(fullHtml.contains("<!DOCTYPE html>"));
		assertTrue(fullHtml.contains("<link rel='stylesheet' href='/css/style.css'>"));
		assertTrue(fullHtml.contains("Sunny")); // JSONからパースした内容が含まれる
	}
}
