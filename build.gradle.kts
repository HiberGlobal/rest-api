dependencies {
  implementation(project(":api:rest"))

  implementation(project(":api:rest:customer:file"))
  implementation(project(":api:rest:customer:modem"))
  implementation(project(":api:rest:customer:modem:message"))
  implementation(project(":api:rest:customer:asset"))
  implementation(project(":api:rest:customer:values"))

  testImplementation(project(":api:rest:integration-test-base"))
}
