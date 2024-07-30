pipeline {
    agent any

    environment {
        // Define environment variables if needed
         TOMCAT_USER = 'admin'
         TOMCAT_PASS = 'password'
    }


    stages {
        stage('Build') {
            steps {
                script {
					  def branchName = env.BRANCH_NAME ?: 'unknown'
                      bat "echo Building branch: ${branchName}"
                    switch (branchName) {
                        case 'dev':
                            bat 'gradlew clean build -PactiveProfile=dev -x test'
                            break
                        case 'qa':
                            bat 'gradlew clean build -PactiveProfile=qa -x test'
                            break
                        case 'master':
                           bat 'gradlew clean build -PactiveProfile=prod -x test'
                            break
                        default:
                            error "Unknown environment: ${env.BRANCH_NAME}"
                    }
                    
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
                    def tomcatUrl
                    def tomcatUser = 'admin'  // Replace with your Tomcat user
                    def tomcatPass = 'admin'  // Replace with your Tomcat password
                    def warFile = 'build/libs/jenkins_app.war'  // Adjust path to your WAR file
                 
                    switch (env.BRANCH_NAME) {
                        case 'dev':
                             bat 'echo deploy to dev'
                             tomcatUrl = 'http://localhost:9090/manager/text/deploy?path=/jenkins_app'
                            break
                        case 'qa':
                            tomcatUrl = 'http://localhost:9090/manager/text/deploy?path=/jenkins_app'
                            break
                        case 'master':
                            bat 'echo deploy to master'
                            tomcatUrl = 'http://localhost:9090/manager/text/deploy?path=/jenkins_app'
                            input message: 'Approve deployment to Production?', ok: 'Deploy'
                            break
                        default:
                            error "Unknown environment: ${env.BRANCH_NAME}"
                    }

                    // Deploy to Tomcat
                    bat "curl -u ${tomcatUser}:${tomcatPass} -T ${warFile} ${tomcatUrl}"
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
