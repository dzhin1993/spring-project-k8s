pipeline {

    agent any

    environment {
            DOCKER_IMAGE = 'ilyazhynko/spring-project-k8s:latest'
    }

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

      stage('Build docker image') {
        steps {
          script {
            docker.build(DOCKER_IMAGE, '-f Dockerfile .')
          }
        }
      }

      stage('Push Docker Image') {
        steps {
          script {
            docker.withRegistry('https://index.docker.io/v1/', 'DOCKER_CREDENTIALS_ID') {
              docker.image(DOCKER_IMAGE).push()
            }
          }
        }
      }

      stage('Deploy') {
        when {
          expression {
            currentBuild.result == null || currentBuild.result == 'SUCCESS'
          }
        }
        steps {
            withKubeConfig([credentialsId: 'KUBECONFIG_CREDENTIALS_ID']) {
              sh """
               kubectl apply -f k8s/secret.yaml
               kubectl apply -f k8s/configmap.yaml
               kubectl apply -f k8s/mysql-deployment.yaml
               kubectl apply -f k8s/app-deployment.yaml
              """
            }
        }
      }
    }

    post {
      success {
        cleanWs()
        echo 'The build has been finished'
      }

      failure {
        echo 'The build has been failed'
      }
    }
}