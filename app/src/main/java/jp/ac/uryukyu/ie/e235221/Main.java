package jp.ac.uryukyu.ie.e235221;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

/**
 * Webアプリケーションのエントリポイント。
 * Spark Javaを用いてサーバを起動し，
 * /weather エンドポイントで天気情報(HTML)を返す。
 */
public class Main {
	/**
	 * メインメソッド。
	 * 
	 * @param args コマンドライン引数(未使用)
	 */
	public static void main(String[] args) {
		// 静的ファイルを src/main/resources/public から配信
		staticFiles.location("/public");

		// GET /weather
		get("/weather", (Request req, Response res) -> {
			String cityName = req.queryParams("city");
			if (cityName == null || cityName.isEmpty()) {
				return "<p>都市名が指定されていません。</p>";
			}
			try {
				WeatherSolver solver = new WeatherSolver();
				// JSONデータを取得してページ全体を生成
				String jsonData = solver.fetchWeatherData(cityName);
				String html = solver.generateWeatherPage(jsonData);
				return html;
			} catch (Exception e) {
				return "<p>天気情報の取得でエラーが発生しました: " + e.getMessage() + "</p>";
			}
		});
	}
}
