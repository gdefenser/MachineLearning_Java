package ml.knn;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

public class Knn {
	public void setCategoryByKnn(Node nodeNew, Matrix matrix,
			String expectCategory) {
		// 计算新增矩阵点与矩阵中其他点的距离
		PriorityQueue<Node> quene = getPriorityQueue(nodeNew, matrix);

		// 取出前k个最小距离的节点,并计算这k个节点各类型出现的概率
		double k = (int) Math.sqrt(matrix.getNodeCount());
		double redCount = 0d;
		double blueCount = 0d;
		double greenCount = 0d;

		Node[] allNodes = quene.toArray(new Node[0]);
		for (int i = 0; i < k; i++) {
			String category = allNodes[i].getCategory();
			if (Node.CATE_RED.equals(category)) {
				redCount++;
			} else if (Node.CATE_BLUE.equals(category)) {
				blueCount++;
			} else if (Node.CATE_GREEN.equals(category)) {
				greenCount++;
			}
		}
		double redRate = redCount / k;
		double blueRate = blueCount / k;
		double greenRate = greenCount / k;
		// 取概率最大概率的类型设置新节点的类型
		// 如概率相同,则取第一个
		if (redRate > blueRate) {
			if (redRate > greenRate) {
				nodeNew.setCategory(Node.CATE_RED);
			} else if (redRate == greenRate) {
				nodeNew.setCategory(Node.CATE_RED);
			} else {
				nodeNew.setCategory(Node.CATE_GREEN);
			}
		} else if (redRate == blueRate) {
			nodeNew.setCategory(Node.CATE_RED);
		} else {
			if (blueRate > greenRate) {
				nodeNew.setCategory(Node.CATE_BLUE);
			} else if (blueRate == greenRate) {
				nodeNew.setCategory(Node.CATE_BLUE);
			} else {
				nodeNew.setCategory(Node.CATE_GREEN);
			}
		}

		System.out.println("正确类型：" + expectCategory + "	实际类型："
				+ nodeNew.getCategory());
	}

	private PriorityQueue<Node> getPriorityQueue(Node nodeNew, Matrix matrix) {
		// 比较器,按距离从小到大排序
		Comparator<Node> comparator = new Comparator<Node>() {
			public int compare(Node node1, Node node2) {
				if (node1.getDistance() <= node2.getDistance())
					return -1;
				else
					return 1;
			}
		};
		// 保存计算的距离
		Map<Double, Node> mapDistance = new HashMap<Double, Node>();
		PriorityQueue<Node> quene = new PriorityQueue<Node>(
				matrix.getNodeCount(), comparator);

		Iterator itMatrix = matrix.getMapMatrix().values().iterator();
		while (itMatrix.hasNext()) {
			Map<Double, Node> mapNode = (Map<Double, Node>) itMatrix.next();
			Iterator itNode = mapNode.values().iterator();
			while (itNode.hasNext()) {
				Node nodeInMatrix = (Node) itNode.next();
				// 欧式公式
				double distance = (nodeNew.getX() - nodeInMatrix.getX())
						* (nodeNew.getY() - nodeInMatrix.getY());
				nodeInMatrix.setDistance(distance);
				// 保存距离至队列
				quene.add(nodeInMatrix);
			}
		}
		return quene;
	}

}
