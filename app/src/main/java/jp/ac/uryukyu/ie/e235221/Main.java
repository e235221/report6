package jp.ac.uryukyu.ie.e235221;

import static spark.Spark.*;

public class Main {
	public static void main(String[] args) {
		WeatherSolver solver = new WeatherSolver();

		// 静的ファイル：HTMLファイルなどを保存する場所の設定
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
				// 天気情報をHTML形式に整形
				String weatherHtml = solver.parseWeatherData(weatherData);

				// --- ここから修正 ---
				// 取得した天気情報を見やすく表示するため，
				// HTML全体を組み立てて返すように修正
				String resultHtml = "<!DOCTYPE html>"
						+ "<html lang='ja'>"
						+ "<head>"
						+ "<meta charset='UTF-8'>"
						+ "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
						+ "<title>" + cityName + "の天気</title>"
						+ "<link rel='stylesheet' href='/css/style.css'>" // CSSファイルの読み込み
						+ "</head>"
						+ "<body>"
						+ "<h1>" + cityName + "の天気情報</h1>"
						+ weatherHtml // parseWeatherDataで組み立てた天気情報ブロック
						+ "</body>"
						+ "</html>";
				// --- ここまで修正 ---

				return resultHtml;
			} catch (Exception e) {
				res.status(500);
				return "<h1>エラーが発生しました: " + e.getMessage() + "</h1>";
			}
		});
	}

}
