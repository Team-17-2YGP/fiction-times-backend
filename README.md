# fiction-times-backend

## Setting up the project for development

**Prerequisites**

- Java
- Maven
- MySQL (xampp)
- Apache Tomcat

**Configs**

- Set `JAVA_HOME`, `CATALINA_HOME`, and `MAVEN_HOME` environment variables

- Add java, and maven to `PATH` environment variable

**Steps**

1. Fork and clone the repository,

   ```
   git clone https://github.com/<your profile name>/fiction-times-backend
   ```

2. Create a database with name fictionTimes
3. Open the cloned repo, and find, duplicate, and rename the `database.properties.example` file to `database.properties`

4. Replace `${PORT}`, `${USERNAME}`, and `${PASSWORD}` with your MySQL database port number and credentials

## Running the project

**Intellij run configuration**

1. Click `Add Configuration...`

2. Select Add new configuration > Tomcat Server > Local
   ![image](https://user-images.githubusercontent.com/72651307/132094117-a56276a8-ba2d-4504-922e-72ef51d8d892.png)

3. Click configure and add Tomcat home path
   ![image](https://user-images.githubusercontent.com/72651307/132094216-996ccc59-c410-4d14-a39f-a5bc4ad52775.png)

4. Under deployment click on + icon and select Artifact
   ![image](https://user-images.githubusercontent.com/72651307/132094277-737fc8cd-0019-474e-b3f7-22812770026e.png)

5. Select the exploded artifact
   ![image](https://user-images.githubusercontent.com/72651307/132094335-0d6b1b10-486c-4fac-bc23-06f6b38f1a1d.png)

6. Click Apply and Ok

7. Select the play button to build and deploy the application

**Build project using maven**

```
mvn install
```
