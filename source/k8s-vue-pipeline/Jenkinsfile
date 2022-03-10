pipeline {
    agent {
        kubernetes {
            cloud 'study-kubernetes'
            slaveConnectTimeout 1200
            workspaceVolume hostPathWorkspaceVolume(hostPath: "/opt/workspace", readOnly: false)
            yaml '''
apiVersion: v1
kind: Pod
spec:
  containers:
    - args: [ \'$(JENKINS_SECRET)\', \'$(JENKINS_NAME)\' ]
      image: 'jenkins/inbound-agent:alpine'
      name: jnlp
      imagePullPolicy: IfNotPresent
      volumeMounts:
        - mountPath: "/etc/localtime"
          name: "localtime"
          readOnly: false
    - command:
        - "cat"
      env:
        - name: "LANGUAGE"
          value: "en_US:en"
        - name: "LC_ALL"
          value: "en_US.UTF-8"
        - name: "LANG"
          value: "en_US.UTF-8"
      image: "node:lts-alpine3.15"
      imagePullPolicy: "IfNotPresent"
      name: "build"
      tty: true
      volumeMounts:
        - mountPath: "/etc/localtime"
          name: "localtime"
    - command:
        - "cat"
      env:
        - name: "LANGUAGE"
          value: "en_US:en"
        - name: "LC_ALL"
          value: "en_US.UTF-8"
        - name: "LANG"
          value: "en_US.UTF-8"
      image: "registry.cn-beijing.aliyuncs.com/citools/kubectl:self-1.17"
      imagePullPolicy: "IfNotPresent"
      name: "kubectl"
      tty: true
      volumeMounts:
        - mountPath: "/etc/localtime"
          name: "localtime"
          readOnly: false
    - command:
        - "cat"
      env:
        - name: "LANGUAGE"
          value: "en_US:en"
        - name: "LC_ALL"
          value: "en_US.UTF-8"
        - name: "LANG"
          value: "en_US.UTF-8"
      image: "docker:19.03.15-git"
      imagePullPolicy: "IfNotPresent"
      name: "docker"
      tty: true
      volumeMounts:
        - mountPath: "/etc/localtime"
          name: "localtime"
          readOnly: false
        - mountPath: "/var/run/docker.sock"
          name: "dockersock"
          readOnly: false
  restartPolicy: "Never"
  nodeSelector:
    build: "true"
  securityContext: { }
  volumes:
    - hostPath:
        path: "/var/run/docker.sock"
      name: "dockersock"
    - hostPath:
        path: "/usr/share/zoneinfo/Asia/Shanghai"
      name: "localtime"
'''
        }
    }
    stages {
        stage('Pulling Code') {
            parallel {
                stage('Pulling Code by Jenkins') {
                    when {
                        expression {
                            env.gitlabBranch == null
                        }

                    }
                    steps {
                        git(changelog: true, poll: true, url: "${REPOSITORY_URL}", branch: "${BRANCH}", credentialsId: 'gitlab-key')
                        script {
                            COMMIT_ID = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
                            TAG = BUILD_TAG + '-' + COMMIT_ID
                            println "Current branch is ${BRANCH}, Commit ID is ${COMMIT_ID}, Image TAG is ${TAG}"

                        }

                    }
                }

                stage('Pulling Code by trigger') {
                    when {
                        expression {
                            env.gitlabBranch != null
                        }

                    }
                    steps {
                        git(url: "${REPOSITORY_URL}", branch: env.gitlabBranch, changelog: true, poll: true, credentialsId: 'gitlab-key')
                        script {
                            COMMIT_ID = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
                            TAG = BUILD_TAG + '-' + COMMIT_ID
                            println "Current branch is ${env.gitlabBranch}, Commit ID is ${COMMIT_ID}, Image TAG is ${TAG}"
                        }

                    }
                }

            }
        }

        stage('Building') {
            steps {
                container(name: 'build') {
                    sh """
                        npm cache verify
                        npm install --registry=https://registry.npm.taobao.org
                        npm install --chromedriver_cdnurl=http://cdn.npm.taobao.org/dist/chromedriver
                        npm run build
                    """
                }
            }
        }

        stage('Docker build for creating image') {
            environment {
                HARBOR_USER = credentials('HARBOR_ACCOUNT')
            }
            steps {
                container(name: 'docker') {
                    sh """
                        echo ${HARBOR_USER_USR} ${HARBOR_USER_PSW} ${TAG}
                        docker build -t ${HARBOR_ADDRESS}/${REGISTRY_DIR}/${IMAGE_NAME}:${TAG} .
                        docker login -u ${HARBOR_USER_USR} -p ${HARBOR_USER_PSW} ${HARBOR_ADDRESS}
                        docker push ${HARBOR_ADDRESS}/${REGISTRY_DIR}/${IMAGE_NAME}:${TAG}
                    """
                }
            }
        }

        stage('Deploying to K8s') {
            environment {
                MY_KUBECONFIG = credentials('study-k8s-kubeconfig')
            }
            steps {
                container(name: 'kubectl') {
                    sh """
                        /usr/local/bin/kubectl --kubeconfig ${MY_KUBECONFIG} set image deploy -l app=${IMAGE_NAME} ${IMAGE_NAME}=${HARBOR_ADDRESS}/${REGISTRY_DIR}/${IMAGE_NAME}:${TAG} -n $NAMESPACE
                    """
                }
            }
        }

    }
    environment {
        REPOSITORY_URL = "git@192.168.20.12:kubernetes/foodie-shop-frontend.git"
        COMMIT_ID = ""
        HARBOR_ADDRESS = "192.168.20.11"
        REGISTRY_DIR = "kubernetes"
        IMAGE_NAME = "foodie-shop-frontend"
        NAMESPACE = "kubernetes"
        TAG = ""
    }
    parameters {
        gitParameter(branch: '', branchFilter: 'origin/(.*)', defaultValue: '', description: 'Branch for build and deploy', name: 'BRANCH', quickFilterEnabled: false, selectedValue: 'NONE', sortMode: 'NONE', tagFilter: '*', type: 'PT_BRANCH')
    }
}