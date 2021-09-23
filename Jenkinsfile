pipeline {
    agent any

    environment {
        SHA = sh(returnStdout: true, script: 'git rev-parse HEAD')
        DOCKER_USERNAME = credentials("DOCKER_USERNAME")
        DOCKER_PASSWORD = credentials("DOCKER_PASSWORD")
        SECRET_FILE = credentials("KUBERNETES_DEPLOY_ACCOUNT_FILE")
        ZONE = credentials("ZONE")
        PROJECT_NAME = credentials("PROJECT_NAME")
        CLUSTER_NAME = credentials("CLUSTER_NAME")
    }

    parameters {
        booleanParam(name: "TEST", defaultValue: false, description: "Execute Test")
        booleanParam(name: "GATEWAY", defaultValue: false, description: "Update Gateway")
        booleanParam(name: "CAREER", defaultValue: false, description: "Update Career")
        booleanParam(name: "DASHBOARD", defaultValue: false, description: "Update Dashboard")
        booleanParam(name: "OAUTH2", defaultValue: false, description: "Update OAuth2")
        booleanParam(name: "DEPLOY", defaultValue: false, description: "Deploy services")
        booleanParam(name: "DESTROY", defaultValue: false, description: "Destroy services")
    }

    stages {
        stage("test") {
            when { expression { params.TEST == true }}
            steps {
                sh '''
                    export DOCKER_HOST_IP=host.docker.internal
                    sh gradlew clean
                    sh integration-test.sh
                '''
            }
        }

        stage("build") {
            when { expression { params.GATEWAY == true || params.CAREER == true || params.DASHBOARD == true || params.OAUTH2 == true }}
            steps {
                sh 'sh gradlew clean; sh gradlew assemble'
            }
        }

        stage("docker-hub / gcp login") {
            steps {
                sh '''
                    echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                    gcloud auth activate-service-account --key-file $SECRET_FILE
                    gcloud config set project $PROJECT_NAME
                    gcloud config set compute/zone $ZONE
                    gcloud container clusters get-credentials $CLUSTER_NAME
                '''
            }
        }

        stage("gateway") {
            when { expression { params.GATEWAY == true }}
            steps {
                sh '''
                    docker build -t hoya0220/gateway-service:latest -t hoya0220/gateway-service:$SHA -f ./api-gateway/Dockerfile ./api-gateway
                    docker push hoya0220/gateway-service:latest
                    docker push hoya0220/gateway-service:$SHA
                '''
            }
        }

        stage("career") {
            when { expression { params.CAREER == true }}
            steps {
                sh '''
                    docker build -t hoya0220/career-service:latest -t hoya0220/career-service:$SHA -f ./career-service/Dockerfile ./career-service
                    docker push hoya0220/career-service:latest
                    docker push hoya0220/career-service:$SHA
                '''
            }
        }

        stage("dashboard") {
            when { expression { params.DASHBOARD == true }}
            steps {
                sh '''
                    docker build -t hoya0220/dashboard-service:latest -t hoya0220/dashboard-service:$SHA -f ./dashboard-service/Dockerfile ./dashboard-service
                    docker push hoya0220/dashboard-service:latest
                    docker push hoya0220/dashboard-service:$SHA
                '''
            }
        }

        stage("oauth2") {
            when { expression { params.OAUTH2 == true }}
            steps {
                sh '''
                    docker build -t hoya0220/oauth2-service:latest -t hoya0220/oauth2-service:$SHA -f ./oauth2-service/Dockerfile ./oauth2-service
                    docker push hoya0220/oauth2-service:latest
                    docker push hoya0220/oauth2-service:$SHA
                '''
            }
        }

        stage("deploy") {
            when { expression { params.DEPLOY == true }}
            steps {
                sh 'kubectl apply -f ./deployment/k8s'
            }
        }

        stage("update services") {
            steps {
                script {
                    if(params.GATEWAY){
                        sh 'kubectl set image deployments/gateway-deployment gateway=hoya0220/gateway-service:$SHA'
                    }

                    if(params.CAREER){
                        sh 'kubectl set image deployments/career-deployment career=hoya0220/career-service:$SHA'
                    }

                    if(params.DASHBOARD){
                        sh 'kubectl set image deployments/dashboard-deployment dashboard=hoya0220/dashboard-service:$SHA'
                    }

                    if(params.OAUTH2){
                        sh 'kubectl set image deployments/oauth2-deployment oauth2=hoya0220/oauth2-service:$SHA'
                    }
                }
            }
        }

        stage("destroy") {
            when { expression { params.DESTROY == true }}
            steps {
                sh 'kubectl delete -f ./deployment/k8s'
            }
        }
    }

    post {
        success {
            echo "test success"
        }

        failure {
            echo "test failure"
        }
    }
}