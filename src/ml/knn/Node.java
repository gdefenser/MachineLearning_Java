package ml.knn;

public class Node {
	public static String CATE_RED = "red";
	public static String CATE_BLUE = "blue";
	public static String CATE_GREEN = "green";

	private String category;
	private double x;
	private double y;
	private double distance;

	public Node(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Node(double x, double y, String category) {
		this(x, y);
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return getX() + "	" + getY() + "	" + getCategory();
	}
}
