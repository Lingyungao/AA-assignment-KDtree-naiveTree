package nearestNeigh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class is required to be implemented. Kd-tree implementation.
 *
 * @author Jeffrey, Youhan
 */
public class KDTreeNN implements NearestNeigh {
	protected Node eduRoot;
	protected Node hosRoot;
	protected Node resRoot;
	
	List<Point> eduPointsList = new ArrayList<Point>();
	List<Point> hosPointsList = new ArrayList<Point>();
	List<Point> resPointsList = new ArrayList<Point>();
	public KDTreeNN() {
		eduRoot = null;
		hosRoot = null;
		resRoot = null;
	}

	@Override
	public void buildIndex(List<Point> points) {
		String axis = "lon";
		for(int i = 0; i < points.size(); ++i){
			if(points.get(i).cat.equals("education"))
				eduPointsList.add(points.get(i));
			if(points.get(i).cat.equals("hospital"))
				hosPointsList.add(points.get(i));
			if(points.get(i).cat.equals("restaurant"))
				resPointsList.add(points.get(i));
			
		}
		eduRoot = buildTree(eduPointsList,axis);
		hosRoot = buildTree(hosPointsList,axis);
		resRoot = buildTree(resPointsList, axis);
		System.out.println(eduRoot.data);
		System.out.println(hosRoot.data);
		System.out.println(resRoot.data);
		
	}

	Node buildTree(List<Point> points, String axis) {
		List<Point> sortedPoints = sortPoints(points, axis);
		if(axis.equals("lon")){
			axis = "lat";
		}
		else {
			axis = "lon";
		}
		Node n = new Node();
		Node right = new Node();
		Node left = new Node();
		int median = findMedian(sortedPoints);
		n.data = sortedPoints.get(median);
		n.left = null;
		n.right = null;
		if (median > 0) {
			left = buildTree(sortedPoints.subList(0, median),axis);
		}
		if(median+1 < points.size()){
			right = buildTree(sortedPoints.subList(median+1, points.size()),axis);
		}
		n.setLeft(left);
		n.setRight(right);

		return n;

	}

	@Override
	public List<Point> search(Point searchTerm, int k) {
		return new ArrayList<Point>();
	}

	@Override
	public boolean addPoint(Point point) {
		return false;
	}

	@Override
	public boolean deletePoint(Point point) {
		return false;
	}

	@Override
	public boolean isPointIn(Point point) {
		return false;
	}

	public List<Point> sortPoints(List<Point> points, String axis) {
		Point p = new Point();
		List<Point> sortedList = points;
		if (axis.equals("lon")) {
			for (int i = 1; i < sortedList.size(); ++i) {
				p = sortedList.get(i);
				int j = i;
				while (j > 0 && sortedList.get(j - 1).lon > p.lon) {
					sortedList.set(j, sortedList.get(j - 1));
					j--;

				}
				sortedList.set(j, p);
			}
			return sortedList;
		} else {
			for (int i = 1; i < sortedList.size(); ++i) {
				p = sortedList.get(i);
				int j = i;
				while (j > 0 && sortedList.get(j - 1).lat > p.lat) {
					sortedList.set(j, sortedList.get(j - 1));
					j--;

				}
				sortedList.set(j, p);
			}
			return sortedList;

		}
	}

	public int findMedian(List<Point> sortedList) {
		int median = 0;
		if(sortedList.size() % 2 == 0){
			median = sortedList.size() / 2;
			median++;
		}
		median = sortedList.size() / 2;

		return median;
	}

}
