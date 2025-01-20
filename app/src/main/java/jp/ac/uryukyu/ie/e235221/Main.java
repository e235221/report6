package jp.ac.uryukyu.ie.e235221;

// /**

// * 天気予報アプリのエントリーポイント。
// */

import static spark.Spark.*;

public class Main {
	public static void main(String[] args) {
		WeatherSolver solver = new WeatherSolver();

		get("/weather", (req, res) -> {
			String cityName = req.queryParams("city");
			if (cityName == null || cityName.isEmpty()) {
				return "都市名を指定してください。例: /weather?city=Tokyo";
			}
			try {
				String weatherData = solver.fetchWeatherData(cityName);
				return solver.parseWeatherData(weatherData);
			} catch (Exception e) {
				res.status(500);
				return "エラーが発生しました: " + e.getMessage();
			}
		});
	}
}
