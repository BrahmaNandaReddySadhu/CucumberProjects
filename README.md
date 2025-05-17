 Cucumberproject 

 clone the project
https://github.com/BrahmaNandaReddySadhu/CucumberProjects

 to run test cases use the 
mvn clean test

 report will be generated at the location target/test-out path


if the reports are not generated then run this comand on the 
System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "sandbox allow-scripts; default-src *; style-src * 'unsafe-inline'; img-src *; script-src * 'unsafe-inline' 'unsafe-eval';")
Jenkins.instance.save()




if you want to integrate with the cicd then create a pipeline job 
scripted pipeline




node {
    stage("Clean work space"){
        deleteDir()
    }
    stage("checkout code from git hub"){
        git url: 'https://github.com/BrahmaNandaReddySadhu/CucumberProjects.git',
        branch: 'master' 
    }
    stage("Initiate test execution"){
        try{
            sh 'mvn clean test'
        }catch(err){
            echo "initiate test run failed. proceeding to rerun if scenarioes are failed"
              echo "Error details: ${err.getMessage()}"
        }
    }
    stage("Re run failed scenarioes"){
        def file = 'target/failedrerun.txt'
        if( fileExists(file) && readFile(file).trim()){
        echo 'failed scenarioes are available , Triggering rerun scenarioes...'
         sh 'mvn test -Dtest=com.sadhu.testRunner.RerunTestRunner'
        }else{
            echo 'No failed scenarioes are available.'
        }
    }
    stage("Archieve Reports "){
            archiveArtifacts artifacts: 'target/test-output/SparkReport/**', fingerprint: true
    }
    
    
}
