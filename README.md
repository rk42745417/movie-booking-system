There are two separate projects, `gym-booking-app` and `movie-booking-app`. They are two java projects in the corresponding directories.

## Building
The build script `mvnw` can be found in each directories.
```bash
./mvnw clean package
```
Upon built, a `.jar` file will be at `targets`, it is an executable for java. See Execution below for more info.

## Execution
You have to set up the MySQL DB. Then, pass the password as the environment variable to the app.
```bash
export DB_PASSWORD=your_production_password

# Run the JAR file, activating the 'prod' profile
java -jar your-application-name.jar --spring.profiles.active=prod
```
