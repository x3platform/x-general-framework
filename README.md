执行
./gradlew build

输出目录 
build/libs/


mvn release:perform
-DconnectionUrl=scm:git:git@github.com:x3platform/x-general-framework.git 
-Dscm.tag=exam-reactor-3.3.0
