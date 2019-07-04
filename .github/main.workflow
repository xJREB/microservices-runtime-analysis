workflow "New workflow" {
  on = "push"
  resolves = ["SonarQube Analysis"]
}

action "Maven Test" {
  uses = "LucaFeger/action-maven-cli@765e218a50f02a12a7596dc9e7321fc385888a27"
  args = "clean test"
}

action "Maven Install" {
  uses = "LucaFeger/action-maven-cli@765e218a50f02a12a7596dc9e7321fc385888a27"
  args = "install -DskipTests"
  needs = ["Maven Test"]
}

action "SonarQube Analysis" {
  uses = "LucaFeger/action-maven-cli@765e218a50f02a12a7596dc9e7321fc385888a27"
  needs = ["Maven Install"]
  secrets = ["SONAR_LOGIN"]
  args = "sonar:sonar -Dsonar.projectKey=xJREB_microservices-runtime-analysis -Dsonar.organization=xjreb-github -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=$SONAR_LOGIN"
}
