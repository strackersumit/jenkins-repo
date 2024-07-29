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
                    // Build the application
                    bat 'gradlew clean build -x test'
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
