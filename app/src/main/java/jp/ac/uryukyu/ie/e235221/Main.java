package jp.ac.uryukyu.ie.e235221;

import static spark.Spark.*;

public class Main {
	public static void main(String[] args) {
		// WeatherSolverインスタンスを作成
		WeatherSolver solver = new WeatherSolver();

		// 静的ファイルの場所を設定（HTMLファイルなどを保存する場所）
		staticFiles.location("/public");

		// 天気情報を取得するエンドポイント
		get("/weather", (req, res) -> {
			// 都市名をクエリパラメータから取得
			String cityName = req.queryParams("city");
			if (cityName == null || cityName.isEmpty()) {
				// 都市名が指定されていない場合
				return "<h1>都市名を指定してください。例: /weather?city=Tokyo</h1>";
			}
			try {
				// 天気情報を取得
				String weatherData = solver.fetchWeatherData(cityName);
				// 取得した天気情報を解析
				String weather = solver.parseWeatherData(weatherData);
				// HTMLで表示
				return "<h1>" + cityName + "の天気情報</h1>" +
						"<p>" + weather + "</p>";
			} catch (Exception e) {
				// エラーが発生した場合
				res.status(500);
				return "<h1>エラーが発生しました: " + e.getMessage() + "</h1>";
			}
		});
	}
}
