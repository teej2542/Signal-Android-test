pipeline {
    options {
        disableConcurrentBuilds()
    }
    agent none
    environment {
        ANDROID_SDK_ROOT='/home/ubuntu/sdk'
    }
    stages {

        stage('Build') {
            agent { label 'master'}
            steps {
                script {
                    deleteDir()
                    git 'git@github.com:teej2542/Signal-Android-test.git'

                    try {
                        sh "./gradlew testInternalProdFlipperUnitTest -x lintchecks:test --continue"
                    } catch (ignored) {
                        junit '**/build/test-results/**/*.xml'
                    } finally {
                        sh "./gradlew collectResults"
                    }
                }
            }
        }
    }
}

