FROM openjdk:17-alpine
COPY build/libs/*.jar app.jar

# ARG로 환경 변수 설정
ARG JASYPT_KEY
ENV JASYPT_KEY=$JASYPT_KEY

# 실행 중에 환경 변수 값을 출력
CMD echo "JASYPT_KEY value is: $JASYPT_KEY"

ENTRYPOINT ["java", "-jar", "app.jar", "--jasypt.encryptor.password=${JASYPT_KEY}"]