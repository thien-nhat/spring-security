# Sử dụng image có sẵn chứa Maven và OpenJDK
FROM maven:3.8.6-openjdk-18-slim AS build

# Đặt thư mục làm việc
WORKDIR /app
cl
# Sao chép toàn bộ mã nguồn vào container
COPY . .

# Build ứng dụng
RUN mvn clean package

# Sử dụng OpenJDK để chạy ứng dụng
FROM openjdk:18.0-slim

# Mở cổng 8080
EXPOSE 8080

# Sao chép file .jar vào container
COPY --from=build /app/target/*.jar app.jar

# Khởi động ứng dụng
#ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]
ENTRYPOINT ["java", "-jar", "/app.jar"]