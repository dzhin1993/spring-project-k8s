pipeline {

    agent any

    stages {
      stage('Test') {
        steps {
          sh './gradlew test'
        }
      }

      stage('Build') {
        steps {
          sh './gradlew clean build -x test'
        }
      }


      stage('Deploy') {
        when {
          expression {
            currentBuild.result == null || currentBuild.result == 'SUCCESS'
          }
        }
        steps {
          sh 'echo deploy'
        }
      }
    }
}