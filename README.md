# Quarkus - Tika
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/github/all-contributors/quarkiverse/quarkus-tika?color=ee8449&style=for-the-badge)](#contributors)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

[![Version](https://img.shields.io/maven-central/v/io.quarkiverse.tika/quarkus-tika?logo=apache-maven&style=for-the-badge)](https://search.maven.org/artifact/io.quarkiverse.tika/quarkus-tika)
![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/quarkiverse/quarkus-tika/build.yml?style=for-the-badge)

## Introduction

This Quarkus extension allows you to parse documents using the [Apache Tika](https://tika.apache.org) library.

It is a replacement for the `quarkus-tika` extension originally part of the Quarkus core distribution.

## Documentation

The documentation for this extension can be found [here](https://quarkiverse.github.io/quarkiverse-docs/quarkus-tika/dev/index.html).

## Docker

When building native images in Docker using the standard Quarkus Docker configuration files some additional features need to be
installed to support Apache POI.  Specifically font information is not included in [Red Hat's ubi-minimal images](https://developers.redhat.com/products/rhel/ubi).  To install it
simply add these lines to your `DockerFile.native` file:

```shell
FROM registry.access.redhat.com/ubi8/ubi-minimal:8.9

######################### Set up environment for POI #############################
RUN microdnf update && microdnf install freetype fontconfig && microdnf clean all
######################### Set up environment for POI #############################

WORKDIR /work/
RUN chown 1001 /work \
    && chmod "g+rwX" /work \
    && chown 1001:root /work
# Shared objects to be dynamically loaded at runtime as needed,
COPY --chown=1001:root target/*.properties target/*.so /work/
COPY --chown=1001:root target/*-runner /work/application
# Permissions fix for Windows
RUN chmod "ugo+x" /work/application
EXPOSE 8080
USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
```

> [!CAUTION]
> Make sure `.dockerignore` does not exclude `.so` files!

## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/sberyozkin"><img src="https://avatars.githubusercontent.com/u/467639?v=4?s=100" width="100px;" alt="sberyozkin"/><br /><sub><b>sberyozkin</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-tika/commits?author=sberyozkin" title="Code">💻</a> <a href="#maintenance-sberyozkin" title="Maintenance">🚧</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://jamesnetherton.github.io/"><img src="https://avatars.githubusercontent.com/u/4721408?v=4?s=100" width="100px;" alt="James Netherton"/><br /><sub><b>James Netherton</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-tika/commits?author=jamesnetherton" title="Code">💻</a></td>
      <td align="center" valign="top" width="14.28%"><a href="http://gastaldi.wordpress.com"><img src="https://avatars.githubusercontent.com/u/54133?v=4?s=100" width="100px;" alt="George Gastaldi"/><br /><sub><b>George Gastaldi</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-tika/commits?author=gastaldi" title="Code">💻</a> <a href="#maintenance-gastaldi" title="Maintenance">🚧</a></td>
      <td align="center" valign="top" width="14.28%"><a href="http://tomscode.com"><img src="https://avatars.githubusercontent.com/u/896029?v=4?s=100" width="100px;" alt="Tomaž"/><br /><sub><b>Tomaž</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-tika/commits?author=tpodg" title="Code">💻</a> <a href="#maintenance-tpodg" title="Maintenance">🚧</a></td>
      <td align="center" valign="top" width="14.28%"><a href="http://melloware.com"><img src="https://avatars.githubusercontent.com/u/4399574?v=4?s=100" width="100px;" alt="Melloware"/><br /><sub><b>Melloware</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-tika/commits?author=melloware" title="Documentation">📖</a> <a href="https://github.com/quarkiverse/quarkus-tika/commits?author=melloware" title="Code">💻</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://zhfeng.github.io/"><img src="https://avatars.githubusercontent.com/u/1246139?v=4?s=100" width="100px;" alt="Zheng Feng"/><br /><sub><b>Zheng Feng</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-tika/commits?author=zhfeng" title="Code">💻</a> <a href="#maintenance-zhfeng" title="Maintenance">🚧</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/gefloh"><img src="https://avatars.githubusercontent.com/u/124285337?v=4?s=100" width="100px;" alt="Gerhard Flothow"/><br /><sub><b>Gerhard Flothow</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-tika/commits?author=gefloh" title="Documentation">📖</a></td>
    </tr>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://thejavaguy.org/"><img src="https://avatars.githubusercontent.com/u/11942401?v=4?s=100" width="100px;" alt="Ivan Milosavljević"/><br /><sub><b>Ivan Milosavljević</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-tika/commits?author=TheJavaGuy" title="Documentation">📖</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://foivos.zakkak.net"><img src="https://avatars.githubusercontent.com/u/1435395?v=4?s=100" width="100px;" alt="Foivos"/><br /><sub><b>Foivos</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkus-tika/commits?author=zakkak" title="Code">💻</a></td>
    </tr>
  </tbody>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!
