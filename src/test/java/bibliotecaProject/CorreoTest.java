package bibliotecaProject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.curso.service.EmailService;

class CorreoTest {
	
 @Autowired
 private EmailService emailService;
 
	@Test
	void test() {
		try {
			emailService.send("capgeminibiblioteca@gmail.com", "capgeminibiblioteca@gmail.com", "capgeminibiblioteca@gmail.com", "capgeminibiblioteca@gmail.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
