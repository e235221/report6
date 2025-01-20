package jp.ac.uryukyu.ie.e235221;

// /**
// * 天気予報アプリのエントリーポイント。
// */
// public class Main {
// public static void main(String[] args) {
// try {
// WeatherSolver solver = new WeatherSolver();
// String cityName = "Tokyo"; // 都市名
// String weatherData = solver.fetchWeatherData(cityName);
// String summary = solver.parseWeatherData(weatherData);
// System.out.println(summary);
// } catch (Exception e) {
// System.err.println("エラーが発生しました: " + e.getMessage());
// }
// }
// }

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
