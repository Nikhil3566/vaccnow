package org.nagarro.vaccine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VaccineApplication.class)
public class VaccineApplicationTests {

	@Test
	public void main() {
		VaccineApplication.main(new String[] {});
	}
}
