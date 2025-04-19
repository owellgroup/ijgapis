# Use OpenJDK 21 as base image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Copy source code
COPY src ./src



# Grant execute permission to mvnw
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package



# Run the application
CMD ["java", "-jar", "target/ijgapis-0.0.1-SNAPSHOT.jar"]
