# ![Sakado icon](https://github.com/sakado-app/sakado/raw/master/resources/android/icon/drawable-mdpi-icon.png) / Sakado application server

## Building

```bash
$ ./gradlew distZip
```

Look for build/distributions/sakado-server-[version].zip then

## Usage

Requires [sakado-data-server](https://github.com/sakado-app/sakado-data-server) to be launched on the port defined in config/pronote.json (config files are created at first run).
Won't run without it.

Push notifications are disabled if Firebase Cloud Messaging is not configured in config/fcm.json