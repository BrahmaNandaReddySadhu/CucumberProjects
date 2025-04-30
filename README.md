 Cucumberproject 

 clone the project
https://github.com/BrahmaNandaReddySadhu/CucumberProjects

 to run test cases use the 
mvn clean test

 report will be generated at the location target/test-out path


if the reports are not generated then run this comand on the 
System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "sandbox allow-scripts; default-src *; style-src * 'unsafe-inline'; img-src *; script-src * 'unsafe-inline' 'unsafe-eval';")
Jenkins.instance.save()
