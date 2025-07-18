#!groovy


def listBROWSER_NAMES = ["Firefox", "Chrome", "Edge", "Safari"]
def listSUITE_NAMES = ["SampleSuite"]

def writeConfigFile(browserName, workspace) {
    echo "DEBUG: Creating config file"
    def data = """BrowserName=${browserName}
    ApplicationURL=https://www.saucedemo.com/
    IsJenkinsRun=true
    WaitTime=10"""
    writeFile(file: "${workspace}/config/config.properties", text: data)
    echo "DEBUG: Config file is created"
}

pipeline {
    agent any

    environment {
        REPORT_DIR = 'TestReport/Report_Folder'
    }

    parameters {
        choice(
            name: 'BROWSER_NAME',
            choices: listBROWSER_NAMES,
            description: 'Required * Select BROWSER_NAME to run test on'
        )
        choice(
            name: 'SUITE',
            choices: listSUITE_NAMES,
            description: 'Required * Select SUITE containing the tests'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/bijitbiswas/AutomationProject-Selenium-TestNG', branch: 'master'
            }
        }

        stage('Generate configuration file') {
            steps{
                script{
                    writeConfigFile(params.BROWSER_NAME, env.WORKSPACE)
                }
            }
        }

        stage('Build & Run Tests') {
            steps {
                sh 'mvn clean test -Dsurefire.suiteXmlFiles=WebTestSuites/${SUITE}.xml'
            }
        }

        stage('Publish Extent HTML Report') {
            steps {
                script {
                    def reportPath = "${env.REPORT_DIR}/${params.SUITE}.html"
                    if (!fileExists(reportPath)) {
                        error "❌ Report not found at: ${reportPath}"
                    }
                }

                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: "${env.REPORT_DIR}",
                    reportFiles: "${params.SUITE}.html",
                    reportName: 'Extent HTML Report'
                ])
            }
        }
    }

    post {
        always {
            echo 'DEBUG: Cleaning up workspace...'
            cleanWs()
        }

        success {
            echo 'DEBUG: ✅ Build and test execution completed successfully!'
        }

        failure {
            echo 'DEBUG: ❌ Build or tests failed. Check console output and reports.'
        }
    }
}