pipeline {
    agent any

    environment {
        // Define environment variables if needed
         TOMCAT_USER = 'admin'
         TOMCAT_PASS = 'password'
    }

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['dev', 'qa', 'master'], description: 'Select the environment to deploy to.')
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the source code from the repository
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    // Build the application
                    bat 'gradlew clean build'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run tests
                    bat 'gradlew test'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    def environment = params.ENVIRONMENT
                    def tomcatUrl
                    def tomcatUser = 'admin'  // Replace with your Tomcat user
                    def tomcatPass = 'password'  // Replace with your Tomcat password
                    def warFile = 'target/myapp.war'  // Adjust path to your WAR file
                    
                    switch (environment) {
                        case 'dev':
                            //tomcatUrl = 'http://dev-server-url:8080/manager/text/deploy?path=/myapp&update=true'
                             bat 'echo in dev'
                            break
                        case 'qa':
                           // tomcatUrl = 'http://qa-server-url:8080/manager/text/deploy?path=/myapp&update=true'
                            break
                        case 'master':
                            bat 'echo in master'
                           // tomcatUrl = 'http://prod-server-url:8080/manager/text/deploy?path=/myapp&update=true'
                            input message: 'Approve deployment to Production?', ok: 'Deploy'
                            break
                        default:
                            error "Unknown environment: ${environment}"
                    }

                    // Deploy to Tomcat
                   // sh "curl -u ${tomcatUser}:${tomcatPass} -T ${warFile} ${tomcatUrl}"
                }
            }
        }
    }

    post {
        success {
            bat 'echo Pipeline succeeded'
        }

        failure {
            bat 'echo Pipeline failed'
        }

        always {
            // Cleanup actions, if needed
            bat 'echo Pipeline finished'
        }
    }
}
