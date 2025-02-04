dependencies {
  implementation(project(":api:rest"))

  implementation(project(":database:system"))
  implementation(project(":events"))
  implementation(project(":modem"))
  implementation(project(":modem:message"))

  implementation(project(":api:customer:proto"))
  implementation(project(":api:customer:conversion"))
  implementation(project(":api:customer:conversion:json"))

  testImplementation(project(":api:rest:integration-test-base"))
}
