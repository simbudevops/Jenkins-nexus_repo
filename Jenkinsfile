pipeline {
    agent any

    tools {
        jdk 'java21'
        maven 'maven3'
    }

    environment {
        NEXUS_URL = 'http://localhost:8081'
    }

    stages {
        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Deploy to Nexus') {
            steps {
                configFileProvider([configFile(fileId: 'maven-settings', variable: 'MAVEN_SETTINGS')]) {
                    sh 'mvn -s $MAVEN_SETTINGS deploy -DskipTests'
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/*.war', fingerprint: true, allowEmptyArchive: true
        }
    }
}
