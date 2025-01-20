package jp.ac.uryukyu.ie.e235221;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WeatherSolverTest {
	@Test
	void testParseWeatherData() {
		WeatherSolver solver = new WeatherSolver();
		String mockJson = "{\"temp_c\":25,\"condition\":{\"text\":\"Sunny\"}}";
		String result = solver.parseWeatherData(mockJson);
		assertEquals("現在の気温: 25°C, 天気: Sunny", result, "天気情報の解析に失敗しました。");
	}
}
