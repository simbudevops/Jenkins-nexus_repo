# Nexus Project

Maven web application built by Jenkins and published to a local Nexus 3 repository.

## Stack
- Java 21 (OpenJDK 21.0.12)
- Maven (WAR packaging)
- Jenkins 2.568.3
- Nexus 3 at http://localhost:8081

## Files
| File | Purpose |
|------|---------|
| `pom.xml` | Maven build + `distributionManagement` pointing to Nexus |
| `Jenkinsfile` | Declarative pipeline: checkout -> compile -> test -> package -> deploy |
| `src/main/java/com/simbu/nexus/HelloServlet.java` | Sample servlet at `/hello` |
| `src/main/webapp/index.jsp` | Landing page |
| `src/main/webapp/WEB-INF/web.xml` | Servlet descriptor |
| `src/test/java/.../HelloServletTest.java` | JUnit 5 test |
| `.gitignore` | Ignores build output |

`settings.xml` is NOT in this repo. It holds Nexus credentials and lives in
Jenkins -> Manage Jenkins -> Managed files (fileId: `maven-settings`).

## Jenkins setup
1. Plugins: Config File Provider, Pipeline Maven Integration, Eclipse Temurin installer.
2. Manage Jenkins -> Tools: JDK named `jdk21`, Maven named `maven3`.
3. Manage Jenkins -> Managed files -> Add -> Global Maven settings.xml
   - ID: `maven-settings`
   - Paste the contents of `settings.xml`
4. Create a Pipeline job -> Pipeline script from SCM -> this GitLab repo.

## Versioning
- `1.0.0-SNAPSHOT` -> goes to `maven-snapshots`
- `1.0.0` (no `-SNAPSHOT`) -> goes to `maven-releases`

If a release deploy fails with 400, set the `maven-releases` repo
Deployment policy to `Allow redeploy` in Nexus.

## Local build
```bash
mvn -s settings.xml clean deploy
```
