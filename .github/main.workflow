workflow "New workflow" {
  on = "push"
  resolves = ["SonarQube Analysis"]
}

action "Maven Test" {
  uses = "LucaFeger/action-maven-cli"
  args = "clean test"
}

action "Maven Install" {
  uses = "LucaFeger/action-maven-cli"
  args = "install -DskipTests"
  needs = ["Maven Test"]
}

action "SonarQube Analysis" {
  uses = "LucaFeger/action-maven-cli"
  needs = ["Maven Install"]
  secrets = ["SONAR_LOGIN"]
  args = "sonar:sonar -Dsonar.login=$SONAR_LOGIN"
}
