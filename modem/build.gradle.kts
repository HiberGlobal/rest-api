dependencies {
  implementation(project(":api:rest"))

  implementation(project(":database:system"))
  implementation(project(":modem"))

  implementation(project(":api:customer:proto"))
  implementation(project(":api:customer:modem"))
  implementation(project(":api:customer:conversion:json"))

  testImplementation(project(":api:rest:integration-test-base"))
}
