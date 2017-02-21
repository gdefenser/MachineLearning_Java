package ml.knn;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Matrix {
	// 矩阵轴
	public static double AXIS_X = 100d;
	public static double AXIS_Y = 100d;
	public static String DECIMAL_FORMAT = "%.1f";
	public static String SET_FILE_PATH = "bin\\ml\\knn\\knn_training.txt";

	private int nodeCount = 0;
	Map<Double, Map<Double, Node>> mapMatrix;

	public Matrix() {
		mapMatrix = new HashMap<Double, Map<Double, Node>>();
		readFromSet();
	}

	public boolean isNodeExists(double x, double y) {
		Map<Double, Node> mapNode = mapMatrix.get(x);
		if (null == mapNode) {
			return false;
		}

		if (!mapNode.containsKey(y)) {
			return false;
		}
		return true;
	}

	/**
	 * 从数据集读取值
	 */
	public void readFromSet() {
		File fileSet = new File(SET_FILE_PATH);
		System.out.println(fileSet.getAbsolutePath());
		nodeCount = 0;
		try {
			List<String> listSet = FileUtils.readLines(fileSet, "UTF-8");
			if (null != listSet && !listSet.isEmpty()) {
				for (String setNode : listSet) {
					try {
						String[] elms = setNode.split("\t");
						double x = Double.parseDouble(elms[0]);
						double y = Double.parseDouble(elms[1]);
						String category = elms[2];
						Node node = new Node(x, y, category);

						addNodeToMatrix(node);
					} catch (Exception ex) {
						continue;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加节点至矩阵
	 */
	public void addNodeToMatrix(Node node) {
		// 大过矩阵坐标最大值则跳过
		// 暂时不考虑负坐标值
		if (node.getX() <= AXIS_X && node.getY() <= AXIS_Y
				&& !isNodeExists(node.getX(), node.getY())) {
			Map<Double, Node> mapNode = mapMatrix.get(node.getX());
			if (null == mapNode) {
				mapNode = new HashMap<Double, Node>();
			}
			mapNode.put(node.getY(), node);
			mapMatrix.put(node.getX(), mapNode);
			nodeCount++;
		}
	}

	public void processRandomNode(int nodeCount) {
		Random random = new Random();
		for (int i = 1; i <= nodeCount; i++) {
			// 保留3位小数
			double x = Double.parseDouble(String.format(DECIMAL_FORMAT,
					random.nextDouble() * AXIS_X));
			double y = Double.parseDouble(String.format(DECIMAL_FORMAT,
					random.nextDouble() * AXIS_Y));
			int ranCate = random.nextInt(3) % (3 - 1 + 1) + 1;
			String category = "";
			if (1 == ranCate) {
				category = Node.CATE_RED;
			} else if (2 == ranCate) {
				category = Node.CATE_BLUE;
			} else if (3 == ranCate) {
				category = Node.CATE_GREEN;
			}
			Node node = new Node(x, y, category);
			addNodeToMatrix(node);
		}
	}

	/**
	 * 保存到数据集
	 */
	public void saveToSet() {
		File fileSet = new File(SET_FILE_PATH);
		try {
			Iterator itMatrix = mapMatrix.values().iterator();
			String value = "";
			while (itMatrix.hasNext()) {
				Map<Double, Node> mapNode = (Map<Double, Node>) itMatrix.next();
				Iterator itNode = mapNode.values().iterator();
				while (itNode.hasNext()) {
					Node node = (Node) itNode.next();
					if (null != node) {
						value += node.toString();
						value += "\r\n";
					}
				}
			}
			if (!StringUtils.isEmpty(value)) {
				FileUtils.writeStringToFile(fileSet, value, "UTF-8");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<Double, Map<Double, Node>> getMapMatrix() {
		return mapMatrix;
	}

	public int getNodeCount() {
		return nodeCount;
	}
}
