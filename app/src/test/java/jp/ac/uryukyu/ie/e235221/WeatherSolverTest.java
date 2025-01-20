package jp.ac.uryukyu.ie.e235221;

// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// class WeatherSolverTest {
// 	@Test
// 	void testParseWeatherData() {
// 		WeatherSolver solver = new WeatherSolver();
// 		String mockJson = "{\"temp_c\":25,\"condition\":{\"text\":\"Sunny\"}}";
// 		String result = solver.parseWeatherData(mockJson);
// 		assertEquals("現在の気温: 25°C, 天気: Sunny", result, "天気情報の解析に失敗しました。");
// 	}
// }

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class WeatherSolverTest {

	@Test
	public void testParseWeatherData() throws Exception { // 例外を宣言
		// WeatherSolverのインスタンスを作成
		WeatherSolver solver = new WeatherSolver();

		// モックしたfetchWeatherDataメソッドを定義
		WeatherSolver spySolver = spy(solver);
		String mockJsonData = "{\"current\": {\"temp_c\": 22.5, \"condition\": {\"text\": \"晴れ\"}}}";

		// fetchWeatherDataのモックを設定
		when(spySolver.fetchWeatherData("okinawa")).thenReturn(mockJsonData);

		// テスト対象メソッドを実行
		String result = spySolver.parseWeatherData(mockJsonData);

		// 結果の確認
		assertNotNull(result);
		assertEquals("現在の気温: 22.5°C, 天気: 晴れ", result);
	}
}
