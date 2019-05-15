sbt-jasperreports
========

sbt-jasperreports is a plugin for SBT to perform JasperReports tasks like compile jrmx project files to jasper compiled files.

## How to use

Make sure your SBT Version in project/build.properties:
```
sbt.version = 0.13.18
```
or
```
sbt.version = 1.2.8
```

Add the plugin in project/plugins.sbt:
```scala
addSbtPlugin("com.jeduardocosta" % "sbt-jasperreports" % "0.0.1")
```

It's necessary to configure the input folder with jrmx files and the output folder, where the jasper files will be written. Both folder need to part of resources folder in your project.

```
jasperReportsInputFolder := "/jasperreports/jrmx-files/"
jasperReportsOutputFolder := "/jasperreports/jasper-files/"
```

## License
```
This software is licensed under the Apache 2 license, quoted below.

Copyright 2013-2016 Stephen Samuel and contributors

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
- 
