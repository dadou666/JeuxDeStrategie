package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class Test {

	@org.junit.jupiter.api.Test
	void test() {
		List<Integer> ls = new ArrayList<Integer>();
		ls.add(-5);
		ls.add(10);
		ls.add(5);
		ls.add(7);
		ls.add(-1);
		System.out.println(ls);
		ls.removeIf((i)-> {
			return i < 0;
			
		});
		System.out.println(ls);

		
	}

}
