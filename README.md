## x-general-framework ##

设置如下环境变量
```
# set X3PLATFORM ENVIRONMENT
## App Path
X3_HOME=[YOUR_APP_HOME]
## MySQL Config
X3_MYSQL_HOST=[YOUR_MYSQL_HOST]
X3_MySQL_PORT=[YOUR_MYSQL_PORT]
X3_MySQL_USERNAME=[YOUR_MYSQL_USERNAME]
X3_MySQL_PASSWORD=[YOUR_MYSQL_PASSWORD]
X3_MySQL_DATABASE=[YOUR_MYSQL_DATABASE]
## Redis Config
X3_REDIS_HOST=[YOUR_REDIS_HOST]
X3_REDIS_PASSWORD=[YOUR_REDIS_PASSWORD]
export X3_HOME X3_MYSQL_HOST X3_MYSQL_PORT X3_MYSQL_USERNAME X3_MYSQL_PASSWORD X3_MYSQL_DATABASE X3_REDIS_HOST X3_REDIS_PASSWORD
```

如何编译此项目?
```
mvn install
```

环境要求 Java 1.8
