package dependencyInjectionExamples;

import static org.junit.Assert.*;

import org.junit.Test;

public class dummyTest {

	GoodMorningService service = new GoodMorningService();
	
	@Test
	public void test() {
		System.out.println(service.sayHi());
	}

}
