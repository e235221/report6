package jp.ac.uryukyu.ie.e235221;

/**
 * 天気予報アプリのエントリーポイント。
 */
public class Main {
    public static void main(String[] args) {
        try {
            WeatherSolver solver = new WeatherSolver();
            String cityName = "Tokyo"; // テスト用都市名
            String weatherData = solver.fetchWeatherData(cityName);
            String summary = solver.parseWeatherData(weatherData);
            System.out.println(summary);
        } catch (Exception e) {
            System.err.println("エラーが発生しました: " + e.getMessage());
        }
    }
}
