#!groovy


def listBROWSER_NAMES = ["Firefox", "Chrome", "Edge", "Safari"]
def listSUITE_NAMES = ["SampleSuite", "SampleRetrySuite"]

def writeConfigFile(browserName, headless, workspace) {
    echo "DEBUG: Creating config file"
    def data = """BrowserName=${browserName}
    ApplicationURL=https://www.saucedemo.com/
    IsJenkinsRun=true
    Headless=${headless}
    WaitTime=10"""
    sh "mkdir -p ${workspace}/src/test/resources"
    writeFile(file: "${workspace}/src/test/resources/config.properties", text: data)
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
        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Run browser in headless mode (recommended for CI). Not supported on Safari.'
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
                    writeConfigFile(params.BROWSER_NAME, params.HEADLESS, env.WORKSPACE)
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
                    def reportPath = "${env.REPORT_DIR}/Selenium_Automation_Report.html"
                    if (!fileExists(reportPath)) {
                        error "❌ Report not found at: ${reportPath}"
                    }
                }

                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: "${env.REPORT_DIR}",
                    reportFiles: "Selenium_Automation_Report.html",
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