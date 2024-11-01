pipeline {
    agent any

    stages {
        stage('checking maven version') {
            steps {
                bat 'mvn -v'
            }
        }
        stage('Repo Cloning') {
            steps {
                git credentialsId: 'github-credentials',
               url: 'https://github.com/BrahmaNandaReddySadhu/CucumberProjects.git'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn clean test'
            }
        }
        stage('report generation'){
        steps {
        cucumber buildStatus: 'UNCHANGED', customCssFiles: '', customJsFiles: '', failedFeaturesNumber: -1, failedScenariosNumber: -1, failedStepsNumber: -1, fileIncludePattern: '**/*.json', jsonReportDirectory: 'target/cucumber-json-report/', pendingStepsNumber: -1, reportTitle: 'SADHU Cucumber Report', skippedStepsNumber: -1, sortingMethod: 'ALPHABETICAL', undefinedStepsNumber: -1
           }
        }
    }
}