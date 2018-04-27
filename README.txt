**NOTES FOR CODING EXERCISE BY JEFF DONOVAN FOR BARCLAY BANK
1) The exercise will consist of a project with a pom file and code separted into packages- domain, service, etc. I will be
   delivering a service level class that can be called/driven with corresponding Junit tests. Are you expecting the service to be
   integrated into Spring or SpringBoot with a RESTFUL inferface/controller? I am not sure where to stop here.
   
2) Rule engine design uses Strategy pattern with rules linked to each other. Note if rule engine becomes more complex consider using
   a Rule Engine like Drools etc. instead of home grown solution. There are two ways to implement the Strategy pattern I choose the
   "linked" based implementation.

3) The solution to the business problem was divided into four rules that will be processed in the given order:
   ValidationRule - validation of data with strict "stop processing" if any data problem is found
								
   FilterSurveyDataRule - filter survey data points based on requirement:
	                       Prices less than 50% of average price are treated as promotion and not considered.
						   Prices more than 50% of average price are treated as data errors and not considered.
	                       
   SelectFrequentlyOccuringPriceRule - select recommened price based on frequency.
						   The retail company uses a Pricing engine which recommends most frequently occurring price. 
						   If multiple prices occur frequently, the least amongst them is chosen.
						   
   SupplyAndDemandAdjustmentRule - adjust price based on given supply and demand parameters
						   Products are classified based on parameters like Supply, Demand. Possible values are Low (L), High (H)
						   If Supply is High and Demand is High, Product is sold at same price as chosen price.
						   If Supply is Low and Demand is Low, Product is sold at 10 % more than chosen price.
						   If Supply is Low and Demand is High, Product is sold at 5 % more than chosen price.
						   If Supply is High and Demand is Low, Product is sold at 5 % less than chosen price.
						   					   
4) Rule Engine uses BigDecimal for money values with a scale set at 2. Should I consider using Joda Money or another more "sofisitcated"
   way of representing money data.

5) Rule Engine can be run by individual unit tests or the test harness which adds all of the rules and runs them- see engine.service.PricingEngineImplTestHarness

6) Rule engine uses lambda expressions so java 1.8 is needed to compile and run

7) If I had more time I would...
	1) Add more meaningful logging
	2) Add additional unit tests beyond what is provided to find edge cases
	3) Add unit tests for classes in the domain package
	4) Add more meaningful exceptions and more thought to exception handling
	5) Add java docs for all classes and at the minimum public methods