workflow "Build and Test" {
  resolves = ["SonarQube Analysis"]
  on = "push"
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
  secrets = [
    "SONAR_LOGIN",
    "GITHUB_TOKEN",
  ]
  args = "sonar:sonar -Dsonar.login=$SONAR_LOGIN"
}
