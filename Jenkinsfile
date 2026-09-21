pipeline {
    agent any

    tools {
        jdk 'java21'
        maven 'maven3'
    }

    environment {
        NEXUS_URL = 'http://localhost:8081'
        // Must match the ID shown in Manage Jenkins -> Managed files
        SETTINGS_FILE_ID = 'maven-settings'
    }

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages {
        stage('Compile') {
            steps {
                configFileProvider([configFile(fileId: "${SETTINGS_FILE_ID}", variable: 'MAVEN_SETTINGS')]) {
                    sh 'mvn -s $MAVEN_SETTINGS clean compile'
                }
            }
        }

        stage('Test') {
            steps {
                configFileProvider([configFile(fileId: "${SETTINGS_FILE_ID}", variable: 'MAVEN_SETTINGS')]) {
                    sh 'mvn -s $MAVEN_SETTINGS test'
                }
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                configFileProvider([configFile(fileId: "${SETTINGS_FILE_ID}", variable: 'MAVEN_SETTINGS')]) {
                    sh 'mvn -s $MAVEN_SETTINGS package -DskipTests'
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                configFileProvider([configFile(fileId: "${SETTINGS_FILE_ID}", variable: 'MAVEN_SETTINGS')]) {
                    sh 'mvn -s $MAVEN_SETTINGS deploy -DskipTests'
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/*.war', fingerprint: true, allowEmptyArchive: true
        }
        success {
            echo 'Build and deploy to Nexus succeeded.'
        }
        failure {
            echo 'Build failed. Check the console output above.'
        }
    }
}
