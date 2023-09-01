**What are the benefits of using a RESTful API**
	→ The benefits of REST is that it specifies a set of rules for how applications can communicate and share data with each other. This makes communication between different applications easier. In reference to the car project, using RESTful API makes the communication with the database much easier compared to last semester. 
**What is JSON, and why does JSON fit so well with REST?**
	→ JSON is a way of displaying data or objects in an easy to read text-based way. This makes it easier and faster to get and recieve the data from one client to another. It fits well with REST, because they both focus on making the communication between application more efficient. We can imagine JSON being a well written letter and REST being a clear roadmap for the letter. This is why they go well together. 
**How you have designed simple CRUD endpoints using spring boot and DTOs to separate api from data  -> Focus on your use of DTO's**
	→ To design CRUD endpoints, i have used the mapping annotations in the controller classes. The contoller classes uses the service classes to perform the respective tasks. I then have response and request DTO classes, for handling the data transfering. I reference to the project, the user could want to create anaccount. They fill out a form with the reqired information and send the data to the server. The controller endpoint for this CRUD validates and parse the data via the request dto class, and then the service class takes this information and perform the action requested - in this case saving the member. 
**What is the advantage of using using DTOs to separate api from data structure when designing rest endpoints**
	→ The advantage of using DTOs is that you can contol what data is visible to the different roles. A member should not see the max discount the seller can give on a car, and no one, even the employee should be able to see the password of a user. 
**Explain shortly the concept mocking in relation to software testing**
	→ Mocking in relation to software testing is simulating the different components and objects, that your code depends on. This is used to isolate a part of the code that you want to test -> that could be a class or method. So you test the components individually, to make sure they work as they should by themselves, without other services like the datababse.  
**How did you mock database access in your tests, using an in-memory database and/or mockito → Refer to your code**
	→ To mock database access in my tests, I used the in-memory database H2, using the @DataJpaTest annotation. 
	@DataJpaTest//Transactional->Rollback->InMemoryDB
	classCarServiceH2Test{

I then set up the test data in the setUp() method annotated with @BeforeEach, meaning that before each test, the test data is set. 

	Carc1,c2;//
	
	@BeforeEach
	voidsetUp(){
	c1=carRepository.save(newCar("Ford","Focus",100,5));
	c2=carRepository.save(newCar("Toyota","Aygo",150,1));
	carService=newCarService(carRepository);//SetupcarServicewiththemock(H2)DB
	}
**Explain the concept Build Server and the role Github Actions play here**
	→ A build server is a server that is set up to listen to push requests on the main branch of a github repository. When something is pushed to the main branch of the repository, the build server tries to build the application, if it can build the application it will deploy it.
**Explain maven, relevant parts in maven, and how maven is used in our CI setup.**
	→ Maven is a framework. Some of the important parts of Maven is the pom.xml file, which is where the dependencies are specified. Maven will automatically download the dependencies specified in the pom.xml file. Maven is used in the workflow process script. Maven is used to build the project. 
**Explain where maven is used by your GitHub Actions Script(s)**
	→ Maven is used in the workflows .yml file:

	build:
	    runs-on: ubuntu-latest
	
	    steps:
	      - uses: actions/checkout@v2
	
	      - name: Set up Java version
	        uses: actions/setup-java@v1
	        with:
	          java-version: '17'
	
	      - name: Build with Maven
	        run: mvn clean install

**Understand and chose cloud service models (IaaS, PaaS, SaaS, DBaaS)for your projects -> Just explain what you have used for this handin**
I have used PaaS for hosting my web application and DBaaS for managing my database.
