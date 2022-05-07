package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class FriendshipGraphTest {

	@Test
	public void VertexTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
	}
	
	@Test
	public void EdgeTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
	}
	
	@Test
	public void DistanceTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		Person dick = new Person("Dick");
		Person xmeow = new Person("XMeow");
		Person elma = new Person("Elma");
		
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addVertex(dick);
		graph.addVertex(xmeow);
		graph.addVertex(elma);
		
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		graph.addEdge(dick, xmeow);
		graph.addEdge(xmeow, dick);
		graph.addEdge(ben, xmeow);
		graph.addEdge(xmeow, ben);
		
		
		assertEquals(1, graph.getDistance(rachel, ross));
		//should return 1
		assertEquals(1, graph.getDistance(xmeow, dick));
		//should return 1
		assertEquals(4, graph.getDistance(dick, rachel));
		//should return 4
		assertEquals(2, graph.getDistance(rachel,ben));
		//should return 2
		assertEquals(3, graph.getDistance(rachel, xmeow));
		//should return 3
		assertEquals(0, graph.getDistance(rachel,rachel));
		//should return 0
		assertEquals(-1, graph.getDistance(rachel,kramer));
		//should return -1
		assertEquals(-1, graph.getDistance(xmeow,kramer));
		//should return -1
	}

}
