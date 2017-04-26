package dependencyInjectionExamples;

public class dependencyInjection {
	
	@Autowired
	GoodMorningService service;

}

@Component
class GoodMorningService{
	
	public String sayHi(){
		return "Good Morning";
	}

}

