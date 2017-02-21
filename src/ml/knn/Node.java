package ml.knn;

public class Node {
	public static String CATE_RED = "red";
	public static String CATE_BLUE = "blue";
	public static String CATE_GREEN = "green";

	private String category;
	private double x;
	private double y;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
