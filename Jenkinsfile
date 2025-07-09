pipeline {
    agent any

//     tools {
//         maven 'Maven 3'   // 🔧 Name must match the one set in Jenkins → Global Tool Config
//         jdk 'JDK 11'      // 🔧 Optional: define JDK if your Jenkins requires it
//     }

    environment {
        REPORT_DIR = 'TestReport/Report_Folder'
        REPORT_FILE = 'ExtentSuite_Folder.html'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/bijitbiswas/AutomationProject-Selenium-TestNG', branch: 'master'
            }
        }

        stage('Build & Run Tests') {
            steps {
                sh 'mvn clean test'
            }
        }

//         stage('Publish TestNG Results') {
//             steps {
//                 junit 'test-output/testng-results.xml'
//             }
//         }

        stage('Publish Extent HTML Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: "${env.REPORT_DIR}",
                    reportFiles: "${env.REPORT_FILE}",
                    reportName: 'Extent HTML Report'
                ])
            }
        }
    }

    post {
        always {
            echo 'Cleaning up workspace...'
            cleanWs()
        }

        success {
            echo '✅ Build and test execution completed successfully!'
        }

        failure {
            echo '❌ Build or tests failed. Check console output and reports.'
        }
    }
}