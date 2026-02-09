#!groovy
script {
  DATETIME_TAG = java.time.LocalDateTime.now()
}
node {
    def project_name = "prometheus-tel-listen"
    def mvn_home = "/usr/share/maven"
    def jdk_home = "/usr/lib/jvm/java-11-openjdk-amd64"
    def namespace = "dev"

    def image_version = "1.0.0"
    try {
        notifyBuild('STARTED')
        stage('Clone repository') {
            checkout scm
        }

        stage('Maven build') {
            sh "mvn clean package -Dmaven.test.skip=true"
        }

        stage('Build image') {
            app = docker.build("prometheus-tel-listen-1.0.0")
        }
        stage('Deploy'){
                sh "docker stop prometheus-tel-listen | true"
                sh "docker rm prometheus-tel-listen | true"
                sh "docker rmi \$(docker images -f dangling=true -q) --force"
                sh "docker run -d --name prometheus-tel-listen --network  docker-compose_database_app -p 6666:6666 prometheus-tel-listen-1.0.0"
        }

    } catch (e) {
        currentBuild.result = "FAILED"
        throw e
    } finally {
        notifyBuild(currentBuild.result)
    }
}
def notifyBuild(String buildStatus = 'STARTED') {
    buildStatus = buildStatus ?: 'SUCCESSFUL'

    def colorName = 'RED'
    def colorCode = '#FF0000'
    def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
    def summary = "${subject} (${env.BUILD_URL})"
    def details = """<p>STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""

    if (buildStatus == 'STARTED') {
        color = 'YELLOW'
        colorCode = '#FFFF00'
    } else if (buildStatus == 'SUCCESSFUL') {
        color = 'GREEN'
        colorCode = '#00FF00'
    } else {
        color = 'RED'
        colorCode = '#FF0000'
    }
}
