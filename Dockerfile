# Use OpenJDK 21 with Maven wrapper support
FROM eclipse-temurin:21-jdk-jammy

# Set working directory inside the container
WORKDIR /app

# Copy Maven wrapper files and pom.xml
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Grant execute permission to mvnw script
RUN chmod +x mvnw

# Download dependencies (optional optimization)
RUN ./mvnw dependency:go-offline -B

# Copy the full source code
COPY src ./src

# Build the application and skip tests
RUN ./mvnw clean package -DskipTests

# Expose the port your Spring Boot app runs on


# Run the application
CMD ["java", "-jar", "target/ijgapis-0.0.1-SNAPSHOT.jar"]
