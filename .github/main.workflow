workflow "Test, Build, Sonar" {
  on = "push"
  resolves = ["SonarQube Analysis"]
}

action "Maven Test" {
  uses = "xlui/action-maven-cli/jdk8@master"
  args = "clean test"
}

action "Maven Install" {
  uses = "xlui/action-maven-cli/jdk8@master"
  args = "install -DskipTests"
  needs = ["Maven Test"]
}

action "SonarQube Analysis" {
  uses = "xlui/action-maven-cli/jdk8@master"
  needs = ["Maven Install"]
  secrets = ["SONAR_LOGIN"]
  args = "sonar:sonar -Dsonar.login=$SONAR_LOGIN"
}
