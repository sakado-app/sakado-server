# ![Sakado icon](https://github.com/sakado-app/sakado/raw/master/resources/android/icon/drawable-mdpi-icon.png) / Sakado application server

## Building

```bash
$ ./gradlew distZip
```

Look for build/distributions/sakado-server-[version].zip then

## Usage

```
$ unzip sakado-server-[version].zip
$ cd sakado-server-[version]
$ bin/sakado-server
```

Push notifications are disabled if Firebase Cloud Messaging is not configured in config/fcm.json